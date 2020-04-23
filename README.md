# Docker commands

## Build locally
docker build -t damogallagher/awsmanagement .
 
## Run locally
docker run -p 8080:8080 --rm damogallagher/awsmanagement

#ECR commands
Retrieve an authentication token and authenticate your Docker client to your registry.
Use the AWS CLI:
```
aws ecr get-login-password --region us-east-1 | docker login --username AWS --password-stdin 858398790708.dkr.ecr.us-east-1.amazonaws.com/damiengallagher
```
Build your Docker image using the following command. 

``` 
docker build -t damiengallagher . 
```

After the build completes, tag your image so you can push the image to this repository:

```
docker tag damiengallagher:latest 858398790708.dkr.ecr.us-east-1.amazonaws.com/damiengallagher:latest
```

Run the following command to push this image to your newly created AWS repository:

```
docker push 858398790708.dkr.ecr.us-east-1.amazonaws.com/damiengallagher:latest
```