# VPC
resource "aws_vpc" "main" {
  cidr_block = var.vpc-cidr
  enable_dns_support = true
  enable_dns_hostnames = true
}

#sub A
resource "aws_subnet" "public-a" {
  vpc_id = aws_vpc.main.id
  cidr_block = var.sub-a-cidr
  availability_zone = "${var.region}a"
  map_public_ip_on_launch = true
}

resource "aws_subnet" "private-b" {
  vpc_id = aws_vpc.main.id
  cidr_block = var.sub-b-cidr
  availability_zone = "${var.region}b"
  map_public_ip_on_launch = true
}

# resource "aws_subnet" "private-c" {
#   vpc_id = aws_vpc.main.id
#   cidr_block = var.sub-c-cidr
#   availability_zone = "${var.region}c"
#   map_public_ip_on_launch = true
# }

#Internet Gateway
resource "aws_internet_gateway" "this" {
  vpc_id = aws_vpc.main.id
}

#Rotas
resource "aws_route_table" "this" {
  vpc_id = aws_vpc.main.id

  route {
    cidr_block = "0.0.0.0/0"
    gateway_id = aws_internet_gateway.this.id
  }
}

#Associar subnets à route table
resource "aws_route_table_association" "sub-a" {
  subnet_id = aws_subnet.public-a.id
  route_table_id = aws_route_table.this.id
}

resource "aws_route_table_association" "sub-b" {
  subnet_id = aws_subnet.private-b.id
  route_table_id = aws_route_table.this.id
}

# resource "aws_route_table_association" "sub-c" {
#   subnet_id = aws_subnet.private-c.id
#   route_table_id = aws_route_table.this.id
# }


#Security Groups
resource "aws_security_group" "ecs-sg" {
  vpc_id = aws_vpc.main.id

  ingress {
    from_port = var.app-port
    to_port = var.app-port
    protocol = "tcp"
    cidr_blocks = ["0.0.0.0/0"]
  }

  egress {
    from_port = 0
    to_port = 0
    protocol = -1
    cidr_blocks = [ "0.0.0.0/0" ]
  }
}

resource "aws_security_group" "rds-sg" {
    vpc_id = aws_vpc.main.id

    ingress {
        from_port = 3306
        to_port = 3306
        protocol = "tcp"
        cidr_blocks = [ aws_vpc.main.cidr_block ]
    }

    egress {
        from_port = 0
        to_port = 0
        protocol = -1
        cidr_blocks = [ "0.0.0.0/0" ]
    }
  
}

resource "aws_security_group" "container-sg" {
    vpc_id = aws_vpc.main.id

    ingress {
        from_port = 0
        to_port = 0
        protocol = -1
        cidr_blocks = [aws_vpc.main.cidr_block]
    }

    egress {
        from_port = 0
        to_port = 0
        protocol = -1
        cidr_blocks = ["0.0.0.0/0"]
    }
  
}

resource "aws_iam_role" "ecs_role" {
  name = "ecsTaskExecutionRole"

  assume_role_policy = jsonencode({
    Version = "2012-10-17"
    Statement = [
      {
        Effect = "Allow"
        Principal = {
          Service = "ecs-tasks.amazonaws.com"
        }
        Action = "sts:AssumeRole"
      }
    ]
  })
}

resource "aws_iam_policy_attachment" "ecs_execution_policy" {
  name       = "ecsTaskExecutionPolicyAttachment"
  roles      = [aws_iam_role.ecs_role.name]
  policy_arn = "arn:aws:iam::aws:policy/service-role/AmazonECSTaskExecutionRolePolicy"
}

#Política que permite acessar container com AWS Session Manager
resource "aws_iam_policy_attachment" "ecs_execution_policy" {
  name       = "ecsTaskExecutionPolicyAttachment"
  roles      = [aws_iam_role.ecs_role.name]
  policy_arn = "arn:aws:iam::aws:policy/AmazonSSMManagedInstanceCore"
}


#Load Ballancing
resource "aws_alb" "this" {
  name = "gregorian-alb"
  internal = false
  load_balancer_type = "application"
  security_groups = [aws_security_group.ecs-sg.id]
  subnets = [ aws_subnet.public-a.id, aws_subnet.private-b.id ]
}

resource "aws_alb_target_group" "api-target" {
  name = "api-target"
  port = var.app-port #api tambem irá escutar na porta 80. Por isso está sendo usado a mesma porta da app
  protocol = "HTTP"
  vpc_id = aws_vpc.main.id
  target_type = "ip" #para fargate essa opção é IP para EC2 é instance.
  health_check {
    path = "/api/check"
    interval = 30
    timeout = 5
    healthy_threshold = 2
    unhealthy_threshold = 2
  }
}

resource "aws_alb_target_group" "app-target" {
  name = "app-target"
  port = var.app-port
  protocol = "HTTP"
  vpc_id = aws_vpc.main.id
  target_type = "ip"
  health_check {
    path = "/login"
    interval = 30
    timeout = 5
    healthy_threshold = 2
    unhealthy_threshold = 2
  }
}

#Target group do Kibana
resource "aws_alb_target_group" "kibana-target" {
  name = "kibana-target"
  port = var.kibana-port
  protocol = "HTTP"
  vpc_id = aws_vpc.main.id
  target_type = "ip"
  health_check {
    path = "/"
    interval = 30
    timeout = 5
    healthy_threshold = 2
    unhealthy_threshold = 2 
  }
  
}

# Listener na porta 80 com regras que encaminha para o target group correspondente
resource "aws_alb_listener" "app-listener" {
  load_balancer_arn = aws_alb.this.arn
  port = var.app-port
  protocol = "HTTP"
  default_action {
    type = "fixed-response"
    fixed_response {
      content_type = "text/plain"
      status_code = "404"
      message_body = "Página não Encontrada!"
    }
  }
}

resource "aws_lb_listener_rule" "rule-api" {
    listener_arn = aws_alb_listener.app-listener.arn
    priority = 10
    condition {
      host_header {
        values = ["api.gregorian.com"]
      }
    }
    action {
      type = "forward"
      target_group_arn = aws_alb_target_group.api-target.arn
    }
  
}

resource "aws_alb_listener_rule" "rule-app" {
    listener_arn = aws_alb_listener.app-listener.arn
    priority = 20
    condition {
      host_header {
        values = [ "cliente.gregorian.com" ]
      }
    }
    action {
      type = "forward"
      target_group_arn = aws_alb_target_group.app-target.arn
    }
  
}

#Rule Kibana
resource "aws_alb_listener_rule" "kibana-rule" {
  listener_arn = aws_alb_listener.app-listener.arn
  priority = 30
  condition {
    host_header {
      values = [ "kibana.gregorian.com" ]
    }
  }
  action {
    type = "forward"
    target_group_arn = aws_alb_target_group.kibana-target.arn
  }
  
}


#Cluster ECS 
resource "aws_ecs_cluster" "gregorian-cluster" {
  name = "gregorian-cluster"
}

resource "aws_ecs_task_definition" "task-api" {
  family = "gregorian-api"
  requires_compatibilities = [ "FARGATE" ]
  network_mode = "awsvpc"
  cpu = 256
  memory = 512
  execution_role_arn = aws_iam_role.ecs_role.arn
  depends_on = [ aws_db_instance.mysql ]
  container_definitions = jsonencode([
    {
        name  = "gregorian-api"
        image = "guiflauzino18/gregorian-api:latest"
        cpu =    256
        memory = 512
        essential = true
        portMappings = [{containerPort = "${var.api-port}"}] #Aqui mapeia a porta do container. O target group recebe na porta 80 e aqui redireciona para a 8080
        environment = [
            {name = "MYSQL_IP", value = aws_db_instance.mysql.address},
            {name = "MYSQL_USERNAME", value = "${var.mysql-user}"},
            {name = "MYSQL_PASSWORD", value = "${var.mysql-password}"}
        ]
        logConfiguration = {
          logDriver = "awslogs"
          options = {
            awslogs-group = aws_cloudwatch_log_group.api_logs.name
            awslogs-region = var.region
            awslogs-stream-prefix = "ecs"
          }
        }
    }
  ])
  
}

resource "aws_ecs_task_definition" "task-app" {
  family = "gregorian-app"
  requires_compatibilities = [ "FARGATE" ]
  network_mode = "awsvpc"
  cpu = 256
  memory = 512
  execution_role_arn = aws_iam_role.ecs_role.arn
  depends_on = [ aws_alb_target_group.api-target]
  container_definitions = jsonencode([
    {
        name  = "gregorian-app"
        image = "guiflauzino18/gregorian-app:beta"
        cpu =    256
        memory = 512
        essential = true
        portMappings = [{containerPort = "${var.app-port}"}]
        environment = [
            {name = "API_URL", value = "${var.api-url}"}
        ]
                logConfiguration = {
          logDriver = "awslogs"
          options = {
            awslogs-group = aws_cloudwatch_log_group.frontend_logs.name
            awslogs-region = var.region
            awslogs-stream-prefix = "ecs"
          }
        }
    }
  ])
}

resource "aws_ecs_service" "service-api" {
    name = "gregorian-api"
    cluster = aws_ecs_cluster.gregorian-cluster.id
    task_definition = aws_ecs_task_definition.task-api.arn
    launch_type = "FARGATE"
    desired_count = 2
    network_configuration {
      subnets = [aws_subnet.public-a.id, aws_subnet.private-b.id]
      security_groups = [aws_security_group.container-sg.id]
      assign_public_ip = true
    }
    load_balancer {
      target_group_arn = aws_alb_target_group.api-target.arn
      container_name = "gregorian-api"
      container_port = var.api-port
    }
  
}


resource "aws_ecs_service" "service-app" {
    name = "greogrian-app"
    cluster = aws_ecs_cluster.gregorian-cluster.id
    task_definition = aws_ecs_task_definition.task-app.arn
    
    launch_type = "FARGATE"
    desired_count = 1
    network_configuration {
      subnets = [aws_subnet.public-a.id, aws_subnet.private-b.id]
      security_groups = [aws_security_group.container-sg.id]
      assign_public_ip = true
    }
    load_balancer {
      target_group_arn = aws_alb_target_group.app-target.arn
      container_name = "gregorian-app"
      container_port = var.app-port
    }
  
}

#RDS MYSQL
resource "aws_db_instance" "mysql" {
  engine = "mysql"
  engine_version = "8.0"
  instance_class = "db.t3.micro"
  allocated_storage = 20
  identifier = "gregorian-db"
  db_subnet_group_name = aws_db_subnet_group.db-subnet-group.name
  username = var.mysql-user
  password = var.mysql-password
  db_name = var.mysql-database
  publicly_accessible = false
  vpc_security_group_ids = [ aws_security_group.rds-sg.id ]
  skip_final_snapshot = true

}

resource "aws_db_subnet_group" "db-subnet-group" {
    name = "gregorian-db-subnet"
    subnet_ids = [aws_subnet.private-b.id, aws_subnet.public-a.id]
  
}


##############################################################################
#Elastic Search
resource "aws_ecs_task_definition" "elasticsearch" {
  family                   = "elasticsearch"
  network_mode             = "awsvpc"
  requires_compatibilities = ["FARGATE"]
  cpu                      = "1024"  # 1 vCPU
  memory                   = "2048"  # 2 GB RAM
  execution_role_arn       = aws_iam_role.ecs_role.arn
  container_definitions    = jsonencode([
    {
      name      = "elasticsearch"
      image     = "docker.elastic.co/elasticsearch/elasticsearch:7.10.1"
      essential = true
      portMappings = [
        {
          containerPort = 9200
          hostPort      = 9200
        }
      ]
      environment = [
        {
          name  = "discovery.type"
          value = "single-node"
        }
      ]
      # logConfiguration = {
      #   logDriver = "awslogs"
      #   options = {
      #     awslogs-group         = "/ecs/elasticsearch"
      #     awslogs-region        = var.region
      #     awslogs-stream-prefix = "ecs"
      #   }
      # }
    }
  ])
}

resource "aws_ecs_service" "elasticsearch" {
  name            = "elasticsearch"
  cluster         = aws_ecs_cluster.gregorian-cluster.id
  task_definition = aws_ecs_task_definition.elasticsearch.arn
  desired_count   = 1
  launch_type     = "FARGATE"
  network_configuration {
    subnets         = [aws_subnet.public-a.id, aws_subnet.private-b.id]
    security_groups = [aws_security_group.elasticsearch.id]
    assign_public_ip = true
  }
}

#LogStash
resource "aws_ecs_task_definition" "logstash" {
  family                   = "logstash"
  network_mode             = "awsvpc"
  requires_compatibilities = ["FARGATE"]
  cpu                      = "512"  # 0.5 vCPU
  memory                   = "1024" # 1 GB RAM
  execution_role_arn       = aws_iam_role.ecs_role.arn
  container_definitions    = jsonencode([
    {
      name      = "logstash"
      image     = "docker.elastic.co/logstash/logstash:7.10.1"
      essential = true
      portMappings = [
        {
          containerPort = 5044
          hostPort      = 5044
        }
      ]
      # logConfiguration = {
      #   logDriver = "awslogs"
      #   options = {
      #     awslogs-group         = "/ecs/logstash"
      #     awslogs-region        = var.region
      #     awslogs-stream-prefix = "ecs"
      #   }
      # }
    }
  ])
}

resource "aws_ecs_service" "logstash" {
  name            = "logstash"
  cluster         = aws_ecs_cluster.gregorian-cluster.id
  task_definition = aws_ecs_task_definition.logstash.arn
  desired_count   = 1
  launch_type     = "FARGATE"
  network_configuration {
    subnets         = [aws_subnet.public-a.id, aws_subnet.private-b.id]
    security_groups = [aws_security_group.logstash.id]
    assign_public_ip = true
  }
}


#KIBANA
resource "aws_ecs_task_definition" "kibana" {
  family                   = "kibana"
  network_mode             = "awsvpc"
  requires_compatibilities = ["FARGATE"]
  cpu                      = "512"  # 0.5 vCPU
  memory                   = "1024" # 1 GB RAM
  execution_role_arn       = aws_iam_role.ecs_role.arn
  container_definitions    = jsonencode([
    {
      name      = "kibana"
      image     = "docker.elastic.co/kibana/kibana:7.10.1"
      essential = true
      portMappings = [
        {
          containerPort = 5601
          hostPort      = 5601
        }
      ]
      environment = [
        {
          name  = "ELASTICSEARCH_HOSTS"
          value = "http://elasticsearch:9200"
        }
      ]
      # logConfiguration = {
      #   logDriver = "awslogs"
      #   options = {
      #     awslogs-group         = "/ecs/kibana"
      #     awslogs-region        = var.region
      #     awslogs-stream-prefix = "ecs"
      #   }
      # }
    }
  ])
}

resource "aws_ecs_service" "kibana" {
  name            = "kibana"
  cluster         = aws_ecs_cluster.gregorian-cluster.id
  task_definition = aws_ecs_task_definition.kibana.arn
  desired_count   = 1
  launch_type     = "FARGATE"
  network_configuration {
    subnets         = [aws_subnet.public-a.id, aws_subnet.private-b.id]
    security_groups = [aws_security_group.kibana.id]
    assign_public_ip = true
  }
  load_balancer {
    target_group_arn = aws_alb_target_group.kibana-target.arn
    container_name   = "kibana"
    container_port   = 5601
  }
}

#Security Group
resource "aws_security_group" "elasticsearch" {
  name        = "elasticsearch-sg"
  vpc_id      = aws_vpc.main.id
  description = "Allow traffic to Elasticsearch"

  ingress {
    from_port   = 9200
    to_port     = 9200
    protocol    = "tcp"
    cidr_blocks = [aws_vpc.main.cidr_block]
  }

  egress {
    from_port   = 0
    to_port     = 0
    protocol    = "-1"
    cidr_blocks = ["0.0.0.0/0"]
  }
}

resource "aws_security_group" "logstash" {
  name        = "logstash-sg"
  vpc_id      = aws_vpc.main.id
  description = "Allow traffic to Logstash"

  ingress {
    from_port  = 5044
    to_port = 5044
    protocol = "tcp"
    cidr_blocks = [ aws_vpc.main.cidr_block ]

  }

  egress {
    from_port   = 0
    to_port     = 0
    protocol    = "-1"
    cidr_blocks = ["0.0.0.0/0"]
  }

}

resource "aws_security_group" "kibana" {
  name        = "kibana-sg"
  vpc_id      = aws_vpc.main.id
  description = "Allow traffic to Kibana"

  ingress {
    from_port  = 5601
    to_port = 5601
    protocol = "tcp"
    cidr_blocks = [ aws_vpc.main.cidr_block ]

  }

  egress {
    from_port   = 0
    to_port     = 0
    protocol    = "-1"
    cidr_blocks = ["0.0.0.0/0"]
  }

}

###################################################
# CloudWatch
resource "aws_cloudwatch_log_group" "api_logs" {
  name              = "/ecs/gregorian-api"
  retention_in_days = 7
}

resource "aws_cloudwatch_log_group" "frontend_logs" {
  name              = "/ecs/gregorian-app"
  retention_in_days = 7
}

resource "aws_cloudwatch_log_group" "mysql_logs" {
  name              = "/ecs/gregorian-mysql"
  retention_in_days = 7
}
