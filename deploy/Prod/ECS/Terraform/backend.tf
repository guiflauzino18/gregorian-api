terraform {
  backend "s3" {
    bucket = "s3-gregorian"
    region = "us-east-1"
    key = "terraform/gregorian-api/ecs"
  }
}