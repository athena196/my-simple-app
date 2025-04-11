pipeline {
    agent any

    environment {
        IMAGE_TAG = "${BUILD_NUMBER}"
    }

    stages {
        stage('Checkout Code') {
            steps {
                git branch: 'ci/cd-gcp', url: 'https://github.com/athena196/my-simple-app.git'
            }
        }

        stage('Build Backend Image') {
            steps {
                dir('my-simple-app-backend') {
                    script {
                        def backendImage = "athena196/my-simple-app-backend:${IMAGE_TAG}"
                        sh "docker build -t ${backendImage} ."
                        env.BACKEND_IMAGE = backendImage
                    }
                }
            }
        }

        stage('Build Frontend Image') {
            steps {
                dir('my-simple-app-frontend') {
                    script {
                        def frontendImage = "athena196/my-simple-app-frontend:${IMAGE_TAG}"
                        sh "docker build -t ${frontendImage} ."
                        env.FRONTEND_IMAGE = frontendImage
                    }
                }
            }
        }

        stage('Push Images to Docker Hub') {
            steps {
                withCredentials([usernamePassword(
                    credentialsId: 'dockerhub-creds',
                    usernameVariable: 'DOCKER_USER',
                    passwordVariable: 'DOCKER_PASS'
                )]) {
                    sh """
                        echo "$DOCKER_PASS" | docker login -u "$DOCKER_USER" --password-stdin
                        docker push $BACKEND_IMAGE
                        docker push $FRONTEND_IMAGE
                    """
                }
            }
        }
    }
}
