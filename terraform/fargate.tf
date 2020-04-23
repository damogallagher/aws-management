module "ecs-fargate" {
    source              = cn-terraform/ecs-fargate/aws
    version             = 2.0.10
    name_preffix        = var.name_preffix
    profile             = var.aws_profile
    region              = var.aws_region
    vpc_id              = module.networking.vpc_id
    availability_zones  = module.networking.availability_zones
    public_subnets_ids  = module.networking.public_subnets_ids
    private_subnets_ids = module.networking.private_subnets_ids
    container_name               = ${var.name_preffix}-myContainer
    container_image              = ${ecr.ecr_image_name}:${ecr.ecr_image_tag}
    container_cpu                = 1024
    container_memory             = 8192
    container_memory_reservation = 2048
    essential                    = true
    container_port               = 9000
    environment = [
        {
            name  = env1
            value = value1
        }
    ]
}