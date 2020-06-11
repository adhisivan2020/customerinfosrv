pipeline {
	agent any
	stages {
		stage('Build') {
			steps {
				echo 'starting build'
				git credentialsId: 'GITID', url: 'https://github.com/adhisivan2020/customerinfosrv.git'
				sh '.gradlew clean build'
				echo 'build completed'
			}
		}
		stage('Test') {
			steps {
				echo 'running test'
			}
		}
		stage('Deploy') {
			steps {
				echo 'running deployment'
			}		
		}
	}
}