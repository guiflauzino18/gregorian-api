output "alb_dns" {
  value = aws_alb.this.dns_name
  description = "DNS do ALb"
}

output "rds_address" {
  value = aws_db_instance.mysql.address
  description = "RDS Address"
}

