pipeline {
    agent none

	environment {
        PROJECT_NAME = 'stonks'
        DOCKER_REGISTRY = credentials('HOST_DOCKER_REGISTRY')
  	}

    stages {
        stage('Build') {
            agent { 
                label 'amd64'
            }
            steps {
                build('amd64');
            }
        }
        stage('Deploy Docker') {
            agent { 
                label 'amd64'
            }
            when {
                tag "*"
            }
            steps {
                deploy('amd64');
            }
        }
        stage('Clean') {
            agent { 
                label 'amd64'
            }
            steps {
                clean('amd64');
            }
        }
        stage('Build aarch64') {
            agent { 
                label 'aarch64'
            }
            steps {
                build('arm64');
            }
        }
        stage('Deploy Docker arm64') {
            agent { 
                label 'aarch64'
            }
            when {
                tag "*"
            }
            steps {
                deploy('arm64');
            }
        }
        stage('Clean aarch64') {
            agent { 
                label 'aarch64'
            }
            steps {
                clean('arm64');
            }
        }
        stage('Deploy Docker Manifest') {
            agent { 
                label 'amd64'
            }
            when {
                tag "*"
            }
            steps {
                sh "docker manifest create --insecure --amend \044DOCKER_REGISTRY/$PROJECT_NAME:${getVersion()} \044DOCKER_REGISTRY/$PROJECT_NAME:amd64-${getVersion()} \044DOCKER_REGISTRY/$PROJECT_NAME:arm64-${getVersion()}"
                sh "docker manifest create --insecure --amend \044DOCKER_REGISTRY/$PROJECT_NAME:latest \044DOCKER_REGISTRY/$PROJECT_NAME:amd64-${getVersion()} \044DOCKER_REGISTRY/$PROJECT_NAME:arm64-${getVersion()}"
                
                sh "docker manifest push --insecure --purge \044DOCKER_REGISTRY/$PROJECT_NAME:${getVersion()}"
                sh "docker manifest push --insecure --purge \044DOCKER_REGISTRY/$PROJECT_NAME:latest"
            }
        }
    }
}

def build(arch) {
    echo 'Building...'

    sh "echo ${getVersion()} > src/main/resources/version"

    sh 'chmod +x ./gradlew'
    sh './gradlew build'
    
    sh "docker build --tag \044DOCKER_REGISTRY/$PROJECT_NAME:${arch}-${getVersion()} ."
}

def deploy(arch) {
    echo 'Deploying...'
    sh "docker push \044DOCKER_REGISTRY/$PROJECT_NAME:${arch}-${getVersion()}"
}

def clean(arch) {
    echo 'Cleaning...'
    sh "docker rmi \044DOCKER_REGISTRY/$PROJECT_NAME:${arch}-${getVersion()}"
}

def getVersion() {
    return "${env.TAG_NAME ? env.TAG_NAME.substring(1) : 'SNAPSHOT.' + env.BUILD_NUMBER}";
}