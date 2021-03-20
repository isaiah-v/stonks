pipeline {
    agent none

	environment {
            PROJECT_NAME = 'stonks'
            DOCKER_REGISTRY = credentials('HOST_DOCKER_REGISTRY')
  	}

    stages {
        stage('Build amd64') {
            agent { 
                label 'amd64'
            }
            steps {
                build('amd64');
		deploy('amd64');
		clean('amd64');
            }
        }
        stage('Build arm64') {
            agent { 
                label 'aarch64'
            }
            steps {
                build('arm64');
		deploy('arm64');
		clean('arm64');
            }
        }
        stage('Update Manifest') {
            agent {
                label 'amd64'
            }
            steps {
                sh "docker manifest create --insecure --amend \044DOCKER_REGISTRY/$PROJECT_NAME:latest \044DOCKER_REGISTRY/$PROJECT_NAME:amd64-latest \044DOCKER_REGISTRY/$PROJECT_NAME:arm64-latest"
                sh "docker manifest push --insecure --purge \044DOCKER_REGISTRY/$PROJECT_NAME:latest"
            }
        }
	state('Deploy') {
            agent {
                label 'amd64'
            }
            steps {
                sh "echo TODO"
            }
	}
    }
}

def build(arch) {
    echo 'Building...'

    sh "echo ${getVersion()} > src/main/resources/version"

    sh 'chmod +x ./gradlew'
    sh './gradlew build'
    
    sh "docker build --tag \044DOCKER_REGISTRY/$PROJECT_NAME:${arch}-latest ."
}

def deploy(arch) {
    echo 'Deploying...'
    sh "docker push \044DOCKER_REGISTRY/$PROJECT_NAME:${arch}-latest"
}

def clean(arch) {
    echo 'Cleaning...'
    sh "docker rmi \044DOCKER_REGISTRY/$PROJECT_NAME:${arch}-latest"
}

def getVersion() {
    return "${env.TAG_NAME ? env.TAG_NAME.substring(1) : 'SNAPSHOT.' + env.BUILD_NUMBER}";
}
