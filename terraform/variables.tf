variable "name_prefix" {
  description = "The prefix to be used for AWS resources"
  default     = "awsmanagement"
}
variable "aws_profile" {
  description = "The AWS profile to use"
  default     = "default"
}
variable "aws_region" {
  description = "The AWS region things are created in"
  default     = "us-east-1"
}

variable "ecr_image_name" {
  description = "The image to use in ecr"
  default     = "858398790708.dkr.ecr.us-east-1.amazonaws.com/damiengallagher"
}
variable "ecr_image_tag" {
  description = "The tag of the image to use in ecr"
  default     = "latest"
}

