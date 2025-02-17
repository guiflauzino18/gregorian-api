variable "tags" {
  type = map(string)
}

variable "region" {
  type = string
}

variable "vpc_cidr" {
  type = string
}

#Subnet A
variable "sub_a_cidr" {
  type = string
}

variable "sub_a_az" {
  type = string
}

#Subenet B
variable "sub_b_cidr" {
  type = string
}

variable "sub_b_az" {
  type = string
}

#Subnet C
variable "sub_c_cidr" {
  type = string
}

variable "sub_c_az" {
  type = string
}

#EC2
variable "key_name" {
  type = string
}

variable "public_key" {
  type = string
}

variable "user_data" {
  type = string
}



