pipeline {
	agent {node 'master'}
	options { skipDefaultCheckout() }
	
	environment {
		SSH_CONFIG_NAME = 'AnsibleHost'
		path_to_file = 'build/libs'
		file_name = 'customerinfosrv-0.0.1-SNAPSHOT.jar'
		remote_dir_path = '/home/ec2-user/app/'
	}
	
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
		stage ('sample sftp move') {
			steps {
				script {
				  sshPublisher(
				   continueOnError: false, failOnError: true,
				   publishers: [
				    sshPublisherDesc(
				     configName: "${SSH_CONFIG_NAME}",
				     verbose: true,
				     transfers: [
				      sshTransfer(
				       sourceFiles: "${path_to_file}/${file_name}, ${path_to_file}/${file_name}",
				       removePrefix: "",
				       remoteDirectory: "${remote_dir_path}",
				       //execCommand: "run commands after copy?"
				      )
				     ])
				   ])
				 }
			}
		}
		stage('Move to Repo') {
			steps {
				withCredentials(bindings: [sshUserPrivateKey(credentialsId: 'JenkinsSSH', keyFileVariable: 'keyfile')]) {
					    echo keyfile
				
						echo 'copying package file to Ansible control host'
						sh 'scp -i $keyfile build/libs/customerinfosrv-0.0.1-SNAPSHOT.jar ec2-user@172.31.37.245:/home/ec2-user/app/'			
						
						echo 'copying systemd service file'
						sh 'scp -i $keyfile service/customerinfosrv.service ec2-user@172.31.37.245:/home/ec2-user/app/'	
						
						echo 'copying ansible files'
						sh 'scp -i $keyfile ansible/* ec2-user@172.31.37.245:/home/ec2-user/ansible/'
					
				}
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