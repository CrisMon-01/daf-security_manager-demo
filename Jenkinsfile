pipeline{
    agent any
     stages {
        stage('Build') {
         steps {
             script{
                 slackSend (message: "BUILD START: Job '${***REMOVED***.JOB_NAME} [${***REMOVED***.BUILD_NUMBER}]' CHECK THE RESULT ON: ***REMOVED***://***REMOVED******REMOVED***.***REMOVED***.it/blue/organizations/jenkins/CI-Security_Manager/activity")
             if(***REMOVED***.BRANCH_NAME=='testci'){
                sh '''
                sbt ';eval System.setProperty("STAGING", "true"); reload; clean; compile; docker:publish'      
                '''
                }
            }
         }
        }
        stage('Staging'){
            steps{
            script{
                if(***REMOVED***.BRANCH_NAME=='testci'){
                    //kubectl delete -f  ***REMOVED***_security_manager_test.yml
                    sh '''
                    cd kubernetes
                    sh config-map-test.sh 
                    kubectl --kubeconfig=../../../.kube/config.***REMOVED***-***REMOVED*** replace -f  ***REMOVED***_security_manager_test.yml --namespace=testci
                    '''
                    slackSend (color: '#00FF00', message: "SUCCESSFUL: Job '${***REMOVED***.JOB_NAME} [${***REMOVED***.BUILD_NUMBER}]' ***REMOVED***://***REMOVED******REMOVED***.***REMOVED***.it/blue/organizations/jenkins/CI-Security_Manager/activity")
            }
            }
        }
     }
     }

post { 
        failure { 
            slackSend (color: '#ff0000', message: "FAIL: Job '${***REMOVED***.JOB_NAME} [${***REMOVED***.BUILD_NUMBER}]' ***REMOVED***://***REMOVED******REMOVED***.***REMOVED***.it/blue/organizations/jenkins/CI-Security_Manager/activity")
        }
    }
}