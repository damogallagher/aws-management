pipeline {
    agent { 
        label 'docker' 
        docker { image 'java' } 
        }
    stages {
        stage('build') {
            steps {
                sh 'java --version'
            }
        }
    }
}