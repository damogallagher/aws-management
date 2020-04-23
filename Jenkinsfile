node {

    jdk = tool name: 'JDK8'
    env.JAVA_HOME = "${jdk}"
    echo "jdk installation path is: ${jdk}"

    git(
       url: 'git@github.com:damogallagher/aws-management.git',
       credentialsId: 'bitnami',
       branch: env.BRANCH_NAME
    )

       stage("Compilation and Analysis") {
            parallel 'Compilation': {
                sh "./mvnw clean install -DskipTests"
            }, 'Static Analysis': {
                stage("Checkstyle") {
                    sh "./mvnw checkstyle:checkstyle"
                    sh "ls -latr "
                    sh "ls -latr target/"
                    step([$class: 'CheckStylePublisher',
                      canRunOnFailed: true,
                      defaultEncoding: '',
                      healthy: '100',
                      pattern: '**/target/checkstyle-result.xml',
                      unHealthy: '90',
                      useStableBuildAsReference: true
                    ])
                }
            }
        }

        stage("Tests and Deployment") {
            parallel 'Unit tests': {
                stage("Runing unit tests") {
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
    
}