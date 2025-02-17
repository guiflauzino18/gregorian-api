output "ec2_dns" {
  value = aws_instance.app.public_dns
}
