#EC2
resource "aws_instance" "gregorian-api" {
  ami = "ami-0c55b159cbfafe1f0"
  instance_type = "t2.micro"
  key_name = var.key_name
  vpc_security_group_ids = [aws_security_group.SGForEC2.id]
  user_data = <<-EOF
#!/bin/bash
sudo yum update
sudo yum install -y docker
sudo systemctl enable --now docker
sudo usermod -aG docker ec2-user
docker run -d --name gregorian-api -p 8080:8080 guiflauzino18/gregorian-api:alfa
EOF

tags = var.tags
}

#VPC
resource "aws_vpc" "this" {
  cidr_block = var.vpc_cidr
  enable_dns_hostnames = true
  enable_dns_support = true
  tags = var.tags
}

#SUBNETS
resource "aws_subnet" "subnet_a" {
  vpc_id = aws_vpc.this.id
  cidr_block = var.sub_a_cidr
  availability_zone = var.sub_a_az
  map_public_ip_on_launch = true
}

#INTERNET GATEWAY
resource "aws_internet_gateway" "this" {
  vpc_id = aws_vpc.this.id
  tags = var.tags
}

#ROUTE TABLES
resource "aws_route_table" "this" {
  vpc_id = aws_vpc.this.id
  tags = var.tags

  #Rota local
  route {
    cidr_block = var.vpc_cidr
    gateway_id = "local"
  }

  #Rota Default
  route {
    cidr_block = "0.0.0.0/0"
    gateway_id = aws_internet_gateway.this.id
  }
}


#Associar subnets à route table
resource "aws_route_table_association" "rt_suba_a" {
  subnet_id = aws_subnet.subnet_a.id
  route_table_id = aws_route_table.this.id
}


#SECURITY GROUPS
resource "aws_security_group" "SGForEC2" {
  name = "for-instances"
  description = "Security Groups para Instancias EC2"
  vpc_id = aws_vpc.this.id
  tags = var.tags
}

#Políticas do SGForEC2
#Permite entrada da rede da VPC
resource "aws_vpc_security_group_ingress_rule" "alowAllFromVPCCidr" {
  security_group_id = aws_security_group.SGForEC2.id
  cidr_ipv4 = var.vpc_cidr
  ip_protocol = -1
}

# Permite entrada a porta 8080
resource "aws_vpc_security_group_ingress_rule" "Allow8080" {
  security_group_id = aws_security_group.SGForEC2.id
  ip_protocol = "tcp"
  from_port = 8080
  to_port = 8080
  cidr_ipv4 = "0.0.0.0/0"
  
}

#Permite SSH
resource "aws_vpc_security_group_ingress_rule" "AllowSSH" {
  security_group_id = aws_security_group.SGForEC2.id
  from_port = 22
  to_port = 22
  ip_protocol = "tcp"
  cidr_ipv4 = "0.0.0.0/0"
}

#Permite todo tráfego de saída
resource "aws_vpc_security_group_egress_rule" "AllowAll" {
  security_group_id = aws_security_group.SGForEC2.id
  cidr_ipv4 = "0.0.0.0/0"
  ip_protocol = -1

}

#Par de Chaves
resource "aws_key_pair" "this" {
  key_name = var.key_name
  public_key = var.public_key
}

