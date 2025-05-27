variable "region" {
  type = string
}

variable "vpc-cidr" {
  type = string
}

variable "sub-a-cidr" {
  type = string
}

variable "sub-b-cidr" {
  type = string
}

variable "sub-c-cidr" {
  type = string
}

variable "app-port" {
  type = number
}

variable "api-port" {
  type = number
}

variables "alb-port"{
  type = number
}

variable "app-url" {
  type = string
}

variable "api-url" {
  type = string
}

variable "mysql-user" {
  type = string
}

variable "mysql-password" {
  type = string
}

variable "mysql-database" {
  type = string
}

variable "elasticsearch-port" {
  type = number  
}

variable "logstash-port" {
  type = number  
}

variable "kibana-port" {
  type = number  
}