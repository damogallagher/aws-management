pipeline {
    agent { 
        docker { image 'java' } 
        }
    stages {
        stage('build') {
            steps {
                sh "whoami"
                sh 'java --version'
            }
        }
    }
}

