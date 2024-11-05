pipeline {
    environment {
        DOCKER_USERNAME = 'dammakrim' // replace with your actual Docker Hub username
    }

    agent any
    stages {
        stage('Stage 1') {
            steps {
                echo 'Printing message....'
                sh 'echo hello world'
            }
        }
    stage('git-clone') {
       steps {
        echo 'Cloning repository...'
        git(
            branch: 'RimDammak', 
            url: 'git@github.com:RimDammak/Devops_Project_Esprit.git', // Using HTTPS URL for PAT access
            credentialsId: 'PAT_Jenkins_' // Use your PAT credential ID here
        )
    }
}

        stage('Verify') {
            steps {
                echo 'Checking content....'
                sh 'find .'
            }
        } 
        stage('maven-clean') {
            steps {
                echo 'Maven Clean'
                sh 'mvn clean'
            }
        }   
        
        stage('maven-install') {
            steps {
                echo 'Maven Install'
                sh 'mvn install'
            }
        }

        stage('Run JUnit & Mockito Tests') {
            steps {
                echo 'Running Tests...'
                sh 'mvn test'
            }
        }

        stage('SonarQube Analysis') {
            steps {
                echo 'Performing SonarQube Analysis...'
                sh '''
                mvn clean verify sonar:sonar \
                -Dsonar.projectKey=Rim_Dammak_5ARCTIC8 \
                -Dsonar.projectName='Rim_Dammak_5ARCTIC8' \
                -Dsonar.host.url=http://192.168.50.4:9000 \
                -Dsonar.token=sqp_22b2b6a693cf4e7ea14195808ba4d42782e93a3c
                '''
            }
        }

        stage('Deploy to Nexus') {
            steps {
                echo 'Deploying artifacts to Nexus...'
                sh 'mvn deploy'
            }
        }
        
        stage('Cleaning Up') {
            steps { 
                echo 'Cleaning up...'
                sh 'mvn clean'
            }
        }

        stage('Build Docker Image') {
            steps {
                echo 'Building Docker image...'
                sh 'docker build -t dammakrim/springbootapplication:latest .'
            }
        }

        stage('Push Docker Image') {
            steps {
                echo 'Pushing Docker image to Docker Hub...'
                withCredentials([string(credentialsId: 'docker_password', variable: 'DOCKER_PASSWORD')]) {
                    sh '''
                    echo $DOCKER_PASSWORD | docker login -u $DOCKER_USERNAME --password-stdin
                    docker push dammakrim/springbootapplication:latest
                    '''
                }
            }
        }

        stage('Run Docker Compose') {
            steps {
                echo 'Running Docker Compose...'
                sh 'docker compose up -d' 
            }
        }    
    } // End of stages block

    // Correctly positioned post block
    post {
        success {
            echo 'Pipeline succeeded!'
            // Send success email notification
            mail to: 'rim.dammak@esprit.tn',
                 subject: 'Build Success',
                 body: 'The build was successful!'
        }
        failure {
            echo 'Pipeline failed.'
            mail to: 'rim.dammak@esprit.tn',
                 subject: 'Build Failure',
                 body: 'The build has failed. Please check the logs.'
        }
    } // End of post block
}
