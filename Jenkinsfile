pipeline {
	agent {node 'master'}
	stages {
		stage('Checkout') {
			steps {
				git credentialsId: 'GITID', url: 'https://github.com/adhisivan2020/customerinfosrv.git'
				echo 'code checkout completed'
			}
		}
		stage('Build') {
			steps {
				script {
					sh 'chmod +x gradlew'
					sh './gradlew clean build'
					echo 'build completed'
				}
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