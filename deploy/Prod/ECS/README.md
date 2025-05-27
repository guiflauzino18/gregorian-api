# Deploy utilizando o serviço de ECS

- Criar Rede VPC em pelo menos 2 AZ

- Criar Security Group
  * para o ECS permitindo tráfego na porta 80 ou 443 de qualquer origem
  * Para o RDS permitindo tráfego da porta 3306 com origem o cidr da VPC
  * Para os Services permitindo todo tráfego da rede da VPC

- Criar ALB
Application Loading Ballancing que irá rotear o tráfego de entrada para os containers
  * resource "aws_alb"

- Criar os target group da API e APP
Target groups são os destinos para onde o load ballancing irá rotear o tráfego
  * resource "aws_alb_target_group" "api-target"
  * resource "aws_alb_target_group" "app-target"

- Criar o Listener
Listener ficará ouvindo as requisições de entrada na porta 80 ou 443
  * resource "aws_alb_listener"

- Criar as regras do listener baseado na url
Roteia o tráfego para o target group baseado nas regras como cliente.gregorian.com para o target group target-app
  * resource "aws_lb_listener_rule" "rule-api" 
  * resource "aws_lb_listener_rule" "rule-app"

- Criar Cluster ECS
  * resource "aws_ecs_cluster" "gregorian-cluster"

- Criar as Tasks Definitions
As Tasks Definitions contém as informações do container, como imagem, nome e recursos
  * resource "aws_ecs_task_definition" "task-api"
  * resource "aws_ecs_task_definition" "task-app"

- Criar os Services
Os services expõem as tasks definitions para os target group e permitem o acesso ao container vindo do load ballancing
  * resource "aws_ecs_service" "service-api"
  * resource "aws_ecs_service" "service-app"

- Criar o RDS
  * resource "aws_db_instance" "mysql"

- Criar o grupo de subnets para o RDS
  * resource "aws_db_subnet_group" "db-subnet-group"


# Iniciar Deploy

- Iniciar Terraform
  * terraform init

- Validar arquivo do terraform
  * terraform validate

- Planejar
  * terraform plan -var="mysql-password" -var="mysql-user=gregorian" -out=plan.out

- Aplicar
  * terraform apply plan.out

# Acessar shel do container
- Usando AWS Session Manager. A política AmazonSSMManagedInstanceCore adicionada à Role do ECS permite acessar o container via Session Manager. 
  * Instalar plugin do Session Manager na máquina
    `curl "https://s3.amazonaws.com/session-manager-downloads/plugin/latest/ubuntu_64bit/session-manager-plugin.deb" -o "session-manager-plugin.deb"`
    `sudo dpkg -i session-manager-plugin.deb`

  * Listar tarefas em execução e pegar o taskArn
    `aws ecs list-tasks --cluster gregorian-cluster`
    
  * Acessar Container
    `aws ecs execute-command --cluster gregorian-cluster --task <TASK_ARN> --container <NOME_DO_CONTAINER> --command "/bin/bash" --interactive`

  * Se container tiver /bin/bash tentar /bin/sh