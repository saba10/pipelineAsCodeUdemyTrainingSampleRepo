//Desccriptive all in one for MBP concepts
pipeline{
    agent any
    stages{
        stage('when->branch|tag|buildtags'){
	    when{
	        //branch 'dev'                                   
		//tag "release*"                     
		//buildingTag()                                        //true on all tags
		changelog '.*test*'                                    //true when commit message matches the expression provided 
		//changeRequest()                                      //true on all pull requests	
		//changeRequest title:'test-when*'                     //true based on the pull request parameters
		changeset pattern:'**/*.groovy', caseSensitive: true   //true when changes are made to one or more filenames matching the pattern
            }
	    steps{
	        echo "testing when"
	    }
	}
    }
}
