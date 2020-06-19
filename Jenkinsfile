pipeline {
	agent {node 'master'}
	options { skipDefaultCheckout() }
	
	environment {
		SSH_CONFIG_NAME = 'AnsibleHost'
		path_to_file = 'build/libs'
		file_name = 'customerinfosrv-0.0.1-SNAPSHOT.jar'
		remote_dir_path = '/app'
	}
	
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
		stage ('Publish Build') {
			steps {
				script {
					echo 'copying package file to Ansible control host'
				
				  sshPublisher(
				   continueOnError: false, failOnError: true,
				   publishers: [
				    sshPublisherDesc(
				     configName: "${SSH_CONFIG_NAME}",
				     verbose: true,
				     transfers: [
				      sshTransfer(
				       sourceFiles: "${path_to_file}/${file_name}",
				       removePrefix: "${path_to_file}",
				       remoteDirectory: "${remote_dir_path}",
				       //execCommand: "run commands after copy?"
				      ),
					  sshTransfer(
				       sourceFiles: "service/*",
				       removePrefix: "service",
				       remoteDirectory: "${remote_dir_path}"
				      ),
					  sshTransfer(
				       sourceFiles: "ansible/*",
				       removePrefix: "ansible",
				       remoteDirectory: "/ansible"
				      ),
					  sshTransfer(
				       sourceFiles: "ansible/playbooks/*",
				       removePrefix: "ansible/playbooks",
				       remoteDirectory: "/ansible/playbooks"
				      ),				      				      
				     ])
				   ])
				 }
			}
		}
		stage('App Deploy') {
			agent {node 'ansiblenode'}
			steps {
				script {
					echo 'running deployment'
					dir ('/home/ec2-user/ansible/') {
						sh 'pwd'
						sh 'ansible-playbook playbooks/installrestsrv.yml'
						sh 'ansible-playbook playbooks/nrpe-deploy.yml'
					}
				}
			}		
		}
	}
}