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
		
		stage('Deploy to GKE') {
			steps {
				withCredentials([file(credentialsId: 'gcp-sa-key', variable: 'GCP_KEY')]) {
					sh """
						# Set gcloud config
						gcloud auth activate-service-account --key-file=$GCP_KEY
						gcloud config set project quantum-potion-454412-h6
						gcloud config set compute/region asia-southeast2
		
						# Get GKE credentials using region
						gcloud container clusters get-credentials standard-public-cluster-1 --region asia-southeast2
						
						# Replace image tags dynamically in YAML
                        sed -i 's|image: athena196/my-simple-app-backend:.*|image: athena196/my-simple-app-backend:${IMAGE_TAG}|' my-simple-app-backend/k8s/backend-deployment-service.yml
                        sed -i 's|image: athena196/my-simple-app-frontend:.*|image: athena196/my-simple-app-frontend:${IMAGE_TAG}|' my-simple-app-frontend/k8s/frontend-deployment-service-new.yml

                        # Optional: install ingress controller (only if not installed)
                        # kubectl apply -f https://raw.githubusercontent.com/kubernetes/ingress-nginx/controller-v1.10.0/deploy/static/provider/cloud/deploy.yaml
	
						# Apply Kubernetes manifests
						kubectl apply -f my-simple-app-backend/k8s/backend-deployment-service.yml
						kubectl apply -f my-simple-app-frontend/k8s/frontend-deployment-service-new.yml
						kubectl apply -f my-simple-app-ingress/k8s/ingress-new.yml
					"""
				}
			}
		}
    }
}
