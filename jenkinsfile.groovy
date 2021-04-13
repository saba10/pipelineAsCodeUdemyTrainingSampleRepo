//Desccriptive all in one for MBP concepts
pipeline{
    agent any
    stages{
        stage('when->branch|tag|buildtags'){
	    when{
	        //branch 'dev'
		//tag "release*"
		buildingTag()
            }
	    steps{
	        echo "testing when"
	    }
	}
    }
}
