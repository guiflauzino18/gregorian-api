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

#Copia Docker Compose para s3
resource "aws_s3_object" "docker-compose" {
  bucket = "s3.gregorian"
  key = "terraform/gregorian-api/staging/docker-compose.yml"
  source = "../Build/docker-compose.yml"
  acl = "private"
}

#Par de Chaves
resource "aws_key_pair" "this" {
  key_name = var.key_name
  public_key = var.public_key
}

#EC2
resource "aws_instance" "gregorian-api" {
  ami = "ami-0cb91c7de36eed2cb"
  instance_type = "t2.micro"
  key_name = var.key_name
  vpc_security_group_ids = [aws_security_group.SGForEC2.id]
  availability_zone = var.sub_a_az
  subnet_id = aws_subnet.subnet_a.id
  depends_on = [ aws_s3_object.docker-compose ]
  user_data = base64decode(
<<-EOF
#!/bin/bash

#Instala Docker
# Add Docker's official GPG key:
sudo apt-get update
sudo apt-get install -y ca-certificates curl
sudo install -m 0755 -d /etc/apt/keyrings
sudo curl -fsSL https://download.docker.com/linux/ubuntu/gpg -o /etc/apt/keyrings/docker.asc
sudo chmod a+r /etc/apt/keyrings/docker.asc

# Add the repository to Apt sources:
echo \
  "deb [arch=$(dpkg --print-architecture) signed-by=/etc/apt/keyrings/docker.asc] https://download.docker.com/linux/ubuntu \
  $(. /etc/os-release && echo "$VERSION_CODENAME") stable" | \
  sudo tee /etc/apt/sources.list.d/docker.list > /dev/null
sudo apt-get update

sudo apt-get install -y docker-ce docker-ce-cli containerd.io docker-buildx-plugin docker-compose-plugin

#Instala Mysql Client
sudo apt install -y mysql-client-core-8.0

#instala aws cli
cd /tmp
apt install -y unzip
curl "https://awscli.amazonaws.com/awscli-exe-linux-x86_64.zip" -o "awscliv2.zip"
unzip awscliv2.zip
sudo ./aws/install

#Configura Docker Compose
mkdir /gregorian
cd /gregorian
aws s3 cp s3://s3.gregorian/terraform/gregorian-api/staging/docker-compose.yml .
docker compose pull
docker compose up -d
EOF
  )
tags = var.tags
}