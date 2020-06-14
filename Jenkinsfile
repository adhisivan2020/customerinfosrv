pipeline {
	agent {node 'master'}
	options { skipDefaultCheckout() }
	stages {
		stage('Checkout') {
			steps {
				/*git credentialsId: 'GITID', url: 'https://github.com/adhisivan2020/customerinfosrv.git'*/
				echo 'code checkout completed'
			}
		}
		stage('Build') {
			steps {
				script {
					/*sh 'chmod +x gradlew'
					sh './gradlew clean build'*/
					echo 'build completed'
				}
			}
		}
		stage('Test') {
			steps {
				echo 'running test'
			}
		}
		stage('Move to Repo') {
			steps {
				echo 'nothing happening here'
			/*script {
				echo 'copying package file to Ansible control host'
				sh 'scp -i /home/ec2-user/.ssh/id_rsa build/libs/customerinfosrv-0.0.1-SNAPSHOT.jar ec2-user@172.31.37.245:/home/ec2-user/app/'			
				
				echo 'copying systemd service file'
				sh 'scp -i /home/ec2-user/.ssh/id_rsa service/customerinfosrv.service ec2-user@172.31.37.245:/home/ec2-user/app/'	
				
				echo 'copying ansible files'
				sh 'scp -i /home/ec2-user/.ssh/id_rsa ansible/* ec2-user@172.31.37.245:/home/ec2-user/ansible/'
													
			}*/
			}
		}
		stage('Deploy') {
			agent {node 'ansiblenode'}
			steps {
				script {
					echo 'running deployment'
					dir ('/home/ec2-user/ansible/') {
						sh 'pwd'
						sh 'ansible-playbook playbooks/installrestsrv.yml'
					}
				}
			}		
		}
	}
}