pipeline {
    agent { 
        label 'amd64'
    }

    environment {
        PROJECT_NAME = 'stonks'
        DOCKER_REGISTRY = credentials('HOST_DOCKER_REGISTRY')
        RUNDECK_JOB_ID = '71dddb62-597c-4f03-b166-bf1590fb76f4'
        RUNDECK_TOKEN = credentials('RUNDECK_TOKEN')
    }

    stages {
        stage('Build') {
            steps {
                build();
            }
        }
        stage('Deploy') {
            steps {
                deploy();
            }
        }
        stage('Clean') {
            steps {
                clean();
            }
        }
        stage('Run') {
            steps {
                run();
            }
        }
    }
}

def build() {
    echo 'Building...'

    sh "echo ${getVersion()} > src/main/resources/version"

    sh 'chmod +x ./gradlew'
    sh './gradlew build'
    
    sh "docker build --platform amd64 --tag \044DOCKER_REGISTRY/$PROJECT_NAME:amd64-latest ."
    sh "docker build --platform arm64 --tag \044DOCKER_REGISTRY/$PROJECT_NAME:arm64-latest ."
}

def deploy() {
    echo 'Deploying...'
    
    sh "docker push \044DOCKER_REGISTRY/$PROJECT_NAME:amd64-latest"
    sh "docker push \044DOCKER_REGISTRY/$PROJECT_NAME:arm64-latest"
    
    sh "docker manifest create --insecure --amend \044DOCKER_REGISTRY/$PROJECT_NAME:latest \044DOCKER_REGISTRY/$PROJECT_NAME:amd64-latest \044DOCKER_REGISTRY/$PROJECT_NAME:arm64-latest"
    sh "docker manifest push --insecure --purge \044DOCKER_REGISTRY/$PROJECT_NAME:latest"
}

def clean() {
    echo 'Cleaning...'
    
    sh "docker rmi \044DOCKER_REGISTRY/$PROJECT_NAME:amd64-latest"
    sh "docker rmi \044DOCKER_REGISTRY/$PROJECT_NAME:arm64-latest"
}

def run() {
    echo 'Running...'

    echo 'Deploying Application...'
    sh "curl --request POST https://ci.ivcode.org/rundeck/api/36/job/${RUNDECK_JOB_ID}/executions?authtoken=\044RUNDECK_TOKEN"
}

def getVersion() {
    return "${env.TAG_NAME ? env.TAG_NAME.substring(1) : 'SNAPSHOT.' + env.BUILD_NUMBER}";
}
