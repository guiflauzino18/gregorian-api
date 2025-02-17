terraform {
  backend "s3" {
    bucket = "s3.gregorian"
    region = "us-east-2"
    key = "terraform/gregorian-api"
  }
}