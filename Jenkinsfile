pipeline{
    agent any
     stages {
        stage('Build') {
         steps {
             script{
             if(***REMOVED***.BRANCH_NAME=='testci'){
                sh '''
                sbt " -DSTAGING=true; reload; clean; compile;  docker:publish"
                '''
                }
            }
         }
        }
        stage('Staging'){
            steps{
            script{
                if(***REMOVED***.BRANCH_NAME=='testci'){
                    sh '''
                    cd kubernetes
                    sh config-map-test.sh
                    kubectl delete -f  ***REMOVED***_security_manager_test.yml
                    kubectl create -f  ***REMOVED***_security_manager_test.yml --namespace=security-enhancements
                    '''
            }
            }
        }
     }
     }
}