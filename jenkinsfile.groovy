//Desccriptive all in one for MBP concepts
pipeline{
    agent any
    stages{
        stage('when->branch|tag|buildtags'){
	    when{
	        branch 'main'
            }
	    steps{
	        echo "testing when"
	    }
	}
    }
}
