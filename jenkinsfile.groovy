//Descritptive pipeline example
//Visit documentation for additional options/syntax
pipeline{
    environment{
        //this is declared as a string
        i=0
        cred1=credentials('gmailCredentials')
    }

    options{
        //timestamps from options can randomly cause failures, so use it in steps as shown in seconds stage
        timestamps()
        skipDefaultCheckout()
    }

    //must be configured in global tools configuration first
    tools{
        maven 'maven-3.6.3'
    }

    agent{
        node{
            label 'master'
            customWorkspace 'workspace/DescriptiveAllInOne'
        }
    }

    stages{
        /*stage('pipeline->stages->stage->steps->retry|timeout|script|error'){
            steps{
                retry(2){
                    //retry does not work if timeout exceeds
                    timeout(time:2, unit:'MINUTES'){
                        script{
                            if(i=='0'){
                                i++
                                error "creating error to check retry"
                            }
                            else{
                                echo "success now as i=${i}"
                            }
                        }
                    }
                }
            }

        }

        stage('pipeline->environment|options|tools|agent'){
            options{
                retry(2)
                timeout(time:2, unit:'MINUTES')
            }
            tools{
                jdk 'jdk8'
            }
            steps{
                timestamps{
                    //java initialized by stage->tools and maven from pipeline->tools
                    sh 'java -version'
                    sh 'mvn -v'
                    echo "cred1 is in username:password format ${cred1}"
                    echo "use cred1_USR to access username ${cred1_USR}"
                    echo "use cred1_PSW too access password ${cred1_PSW}"
                    sh "printenv"  //prints all env variables
                }
            }
        }*/

        stage('sample'){
            when{
                allOf{ //allOf or anyOf
                    equals expected: '0', actual: i
                    not{
                        environment name: 'i', value: '1'
                    }
                    expression{
                        i == '0'
                    }
                }
            }
            steps{
                echo 'testing when'
            }
        }
    }
}
