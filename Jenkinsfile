pipeline{
    agent any

    //git(
    //   url: 'git@github.com:damogallagher/aws-management.git',
    //   credentialsId: 'bitnami',
    //   branch: env.BRANCH_NAME
    //)
    options
    {
        buildDiscarder(logRotator(numToKeepStr: '3'))
    }

    environment 
    {
        VERSION = 'latest'
        PROJECT = 'aws_management'
        IMAGE = 'aws_management:latest'
        ECRURL = 'http://858398790708.dkr.ecr.us-east-1.amazonaws.com'
        ECRCRED = 'ecr:us-east-1:aws_management'
        JAVA_HOME = '/opt/bitnami/java'
    }
    stages
    {

       stage("Compilation and Analysis") {
           steps {
            parallel (
                'Compilation': {
                    sh "echo $JAVA_HOME "
                    sh "java -version"
                    sh "./mvnw clean install -DskipTests"
                }, 
                'Static Analysis': {
                    sh "./mvnw checkstyle:checkstyle"
                    sh "ls -latr "
                    sh "ls -latr target/"
                    /*step([$class: 'CheckStylePublisher',
                      canRunOnFailed: true,
                      defaultEncoding: '',
                      healthy: '100',
                      pattern: '/target/checkstyle-result.xml',
                      unHealthy: '90',
                      useStableBuildAsReference: true
                    ])*/
            }
            )
           }
        }

        stage("Tests") {
            steps {
                    script {
                        try {
                            sh "./mvnw test -DskipTests=false"
                            sh "ls -latr target/"
                            sh "ls -latr target/surefire-reports/"
                        } catch(err) {
                            step([$class: 'JUnitResultArchiver', testResults:
                            '**/target/surefire-reports/TEST-*Test*.xml'])
                            throw err
                        }
                    step([$class: 'JUnitResultArchiver', testResults:
                        '**/target/surefire-reports/TEST-*Test*.xml'])
                }
            }
        }
    
        stage('Build preparations')
        {
            steps
            {
                script 
                {
                    // calculate GIT lastest commit short-hash
                    gitCommitHash = sh(returnStdout: true, script: 'git rev-parse HEAD').trim()
                    shortCommitHash = gitCommitHash.take(7)
                    // calculate a sample version tag
                    VERSION = shortCommitHash
                    // set the build display name
                    currentBuild.displayName = "#${BUILD_ID}-${VERSION}"
                    IMAGE = "$PROJECT:$VERSION"
                }
            }
        }
        stage('Docker build')
        {
            steps
            {
                script
                {
                    // Build the docker image using a Dockerfile
                    docker.build("$IMAGE","examples/pipelines/TAP_docker_image_build_push_ecr")
                }
            }
        }
        stage('Docker push')
        {
            steps
            {
                script
                {
                    // login to ECR - for now it seems that that the ECR Jenkins plugin is not performing the login as expected. I hope it will in the future.
                    sh("eval \$(aws ecr get-login --no-include-email | sed 's|https://||')")
                    // Push the Docker image to ECR
                    docker.withRegistry(ECRURL, ECRCRED)
                    {
                        docker.image(IMAGE).push()
                    }
                }
            }
        }
    }
    
    post
    {
        always
        {
            // make sure that the Docker image is removed
            sh "docker rmi $IMAGE | true"
        }
    }
    

}
