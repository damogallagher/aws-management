# Set up CloudWatch group and log stream and retain logs for 30 days
resource "aws_cloudwatch_log_group" "awsmanagement_log_group" {
  name              = "/ecs/awsmanagement-service"
  retention_in_days = 30

  tags = {
    Name = "awsmanagement-log-group"
  }
}

resource "aws_cloudwatch_log_stream" "awsmanagement_log_stream" {
  name           = "awsmanagement-log-stream"
  log_group_name = aws_cloudwatch_log_group.awsmanagement_log_group.name
}