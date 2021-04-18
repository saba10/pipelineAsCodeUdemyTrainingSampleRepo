//Desccriptive all in one for MBP concepts
pipeline{
    agent any
    stages{
        stage('when->branch|tag|buildtags'){
	    when{
	        //branch 'dev'                                   
		//tag "release*"                     
		//buildingTag()                                    //true on all tags
		changelog '.*test*'                               //true when commit message matches the expression provided 
		//changeRequest()                                //true on all pull requests	
		changeRequest title:'test-when*'
            }
	    steps{
	        echo "testing when"
	    }
	}
    }
}
