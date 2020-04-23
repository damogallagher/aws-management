node {

jdk = tool name: 'JDK8'
  env.JAVA_HOME = "${jdk}"

  echo "jdk installation path is: ${jdk}"

    git(
       url: 'git@github.com:damogallagher/aws-management.git',
       credentialsId: 'bitnami',
       branch: env.BRANCH_NAME
    )
    sh "ls -latr"
       stage("Compilation and Analysis") {
            parallel 'Compilation': {
                sh "ls -latr"
                sh "./mvnw clean install -DskipTests"
            }, 'Static Analysis': {
                stage("Checkstyle") {
                    sh "./mvnw checkstyle:checkstyle"

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
                    } catch(err) {
                        step([$class: 'JUnitResultArchiver', testResults:
                          '**/target/surefire-reports/TEST-*UnitTest.xml'])
                        throw err
                    }
                   step([$class: 'JUnitResultArchiver', testResults:
                     '**/target/surefire-reports/TEST-*UnitTest.xml'])
                }
            }


        }
    
}