# Docker commands

## Build locally
docker build -t damogallagher/awsmanagement .
 
## Run locally
docker run -p 8080:8080 --rm damogallagher/awsmanagement

#Deploy to ECR commands
Retrieve an authentication token and authenticate your Docker client to your registry.
Use the AWS CLI:
```
aws ecr get-login-password --region us-east-1 | docker login --username AWS --password-stdin 858398790708.dkr.ecr.us-east-1.amazonaws.com/damiengallagher
```
Build your Docker image using the following command. 

``` 
docker build -t awsmanagement . 
```

After the build completes, tag your image so you can push the image to this repository:

```
docker tag awsmanagement:latest 858398790708.dkr.ecr.us-east-1.amazonaws.com/awsmanagement:latest
```

Run the following command to push this image to your newly created AWS repository:

```
docker push 858398790708.dkr.ecr.us-east-1.amazonaws.com/awsmanagement:latest
```

# Terraform commands
When in the terraform directory - you can run the following commands 

Init the project (should only need to be done if cloning a new repo)
``` 
terraform init
```

See what changes terraform will make in your environment (i)
```
terraform plan
```

Apply changes to your environment