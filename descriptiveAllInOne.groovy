//Descritptive pipeline example
//Visit documentation for additional options/syntax
pipeline{
    /*environment{
        //this is declared as a string
        i=0
        cred1=credentials('gmailCredentials')
    }
    
    options{
        //timestamps from options can randomly cause failures, so use it in steps as shown in seconds stage
        timestamps()
        skipDefaultCheckout()
        //parallelsAlwaysFailFast()
        buildDiscarder(logRotator(numToKeepStr:'25'))
        disableConcurrentBuilds()
        skipStagesAfterUnstable()
        overrideIndexTriggers(true) // applicable for MBP. true sets the specfic branch to trigger a build when scanned
        //checkoutToSubdirectory('foo') //Perform the automatic source control checkout in a subdirectory of the workspace
        //newContainerPerStage  //used along with docker or dockerfile top level agent
    }
    
    //must be configured in global tools configuration first
    tools{
        maven 'maven-3.6.3'
    }
    
    parameters{
        string(name:'string',defaultValue:"default",description:'description')
        booleanParam(name:'boolean',defaultValue:true,description:'description')
        choice(name:'choice',choices:['1','2','3'],description:'description')
        text(name:'text',defaultValue:'default',description:'description')
        file(name:'file',description:'description')
        password(name:'password',description:'description')  //password is not masked by default when printed
        credentials(name:'credentials',description:'description',required:true)  //required here does not work
    }*/
    
    agent{
        node{
            label 'master'
            customWorkspace 'workspace/DescriptiveAllInOne'
        }
    }
    
    //requires atleast one build for the changes to reflect in the pipeline 
    triggers{
        // cron('MINUTE HOUR DAYOFTHEMONTH MONTH WEEK(0-7)')
        //cron('TZ=Europe/London\nH(30-59)/2 * * * *') //every two minutes during the last 30 mins of the hour
        //pollSCM('* * * * *') //every minute
        upstream(upstreamProjects:'hello-world,syntax-example',threshold: hudson.model.Result.SUCCESS)
    }
    
    stages{
        stage('test'){
            steps{
                echo 'test stage'
            }
        }
        
        /*stage('checkout from git to test pollSCM'){
            steps{
                checkout([$class: 'GitSCM',
                branches: [[name:'origin/dev']],
                userRemoteConfigs: [[url:'git@github.com:saba10/pipelineAsCodeUdemyTrainingSampleRepo.git']]])
            }
        }
        
        stage('pipeline->stages->stage->steps->retry|timeout|script|error'){
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
                //skipDefaultCheckout()  
                // skipDefaultCheckout from stage level works for agent set at stage only or when global agent is set to none
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
                    sh "printenv"  //prints all env variables that are available to shell
                }
            }
        }
        
        stage('when->environment|equals|expression|not|allOf|anyOf'){
            when{
                not{
                    anyOf{
                        environment name: 'i', value: '1'
                        equals actual: i, expected: '1' 
                        expression{
                            i == '1'
                        }
                    }
                }
                
                //triggeredBy cause: "UserIdCause", detail: "sabm"     //other tiggeredbBy causes are 'SCMTrigger' 'TimerTrigger' 'UpstreamCause'
                //beforeAgent true                                     //when condition executes before entering the agent set in stage level. default value is false.
                //There are other when conditions - 'beforeInput' and 'beforeOptions'
                
            }
            steps{
                echo "testing when condition"
            }
        }
        
        //refer documentation for the rules for nested stage
        stage('parallel example'){
            failFast true
            parallel{
                stage('stage 1'){
                    steps{
                        sleep 5
                        echo "test stage 1"
                    }
                }
                stage('stage 2'){
                    steps{
                        sleep 5
                        echo "test stage 2"
                    }
                }
                //here parallel execution is for outer stages, the nested stage 'nested 1' 'nested 2' are sequential 
                stage('nested'){
                    stages{
                        stage('nested1'){
                            steps{
                                sleep 5
                                echo "test nested 1"
                            }
                        }
                        stage('nested 2'){
                            steps{
                                sleep 5
                                echo "test nested 2"
                            }
                        }
                    }
                }
            }
        }
        
        stage('input example'){
            input{
                message 'provide input'
                ok 'done'
                submitter 'admin'
                submitterParameter 'submitterName'
                parameters{
                    string(name:'string',defaultValue:'default',description:'description')
                    booleanParam(name:'boolean',defaultValue: true, description:'description')
                    choice(name:'choice',choices:['1','2','3'],description:'description')
                    text(name:'text',defaultValue:'default',description:'description')
                    file(name:'file',description:'description')
                    password(name:'password',description:'description')  //password is not masked by default when printed
                    credentials(name:'credentials',description:'description',required:true) //required here does not work
                }
            }
            steps{
                echo "string is ${string}"
                echo "boolean is ${boolean}"
                echo "choice is ${choice}"
                echo "text is ${text}"
                echo "file is ${file}"
                echo "password is ${password}"
                echo "credentials is ${credentials}"
                echo "submitter is ${submitterName}"
            }
        }
        stage('post'){
            steps{
                script{
                    currentBuild.result='Unstable'
                }
            }
        }*/
    }
    //post conditions are always called in below order regardless of their positions
    //Execution ORDER - always,changed,fixed,regression,aborted,failure,success,unstable,unsuccessful,cleanup
    //changed(true when status has changed),fixed(true when status is success and previous run was failure or unstable),regression(true when status changed and previous run was a success), cleanup(always true and is executed at last)
    post{
        cleanup{
            echo "cleanup is called"
        }
        regression{
            echo "regression is called"
        }
        always{
            echo "always is called"
        }
    }
}
