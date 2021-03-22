pipeline {
    agent { 
        label 'amd64'
    }

    environment {
        PROJECT_NAME = 'stonks'
        DOCKER_REGISTRY = credentials('HOST_DOCKER_REGISTRY')
        RUNDECK_JOB_ID = '69a6482f-c285-4e14-a915-03f5b4e665f4'
        RUNDECK_TOKEN = credentials('RUNDECK_TOKEN')
    }

    stages {
        stage('Build') {
            steps {
                doBuild();
            }
        }
        stage('Deploy') {
            steps {
                doDeploy();
            }
        }
        stage('Clean') {
            steps {
                doClean();
            }
        }
        stage('Run') {
            steps {
                doRun();
            }
        }
    }
}

def doBuild() {
    echo 'Building...'

    sh "echo ${getVersion()} > src/main/resources/version"

    sh 'chmod +x ./gradlew'
    sh './gradlew build'
    
    sh "docker build --platform amd64 --tag \044DOCKER_REGISTRY/$PROJECT_NAME:amd64-latest ."
    sh "docker build --platform arm64 --tag \044DOCKER_REGISTRY/$PROJECT_NAME:arm64-latest ."
}

def doDeploy() {
    echo 'Deploying...'
    
    sh "docker push \044DOCKER_REGISTRY/$PROJECT_NAME:amd64-latest"
    sh "docker push \044DOCKER_REGISTRY/$PROJECT_NAME:arm64-latest"
    
    sh "docker manifest create --insecure --amend \044DOCKER_REGISTRY/$PROJECT_NAME:latest \044DOCKER_REGISTRY/$PROJECT_NAME:amd64-latest \044DOCKER_REGISTRY/$PROJECT_NAME:arm64-latest"
    sh "docker manifest push --insecure --purge \044DOCKER_REGISTRY/$PROJECT_NAME:latest"
}

def doClean() {
    echo 'Cleaning...'
    
    sh "docker rmi \044DOCKER_REGISTRY/$PROJECT_NAME:amd64-latest"
    sh "docker rmi \044DOCKER_REGISTRY/$PROJECT_NAME:arm64-latest"
}

def doRun() {
    echo 'Running...'

    echo 'Deploying Application...'
    sh "curl --request POST https://ci.ivcode.org/rundeck/api/36/job/${RUNDECK_JOB_ID}/executions?authtoken=\044RUNDECK_TOKEN"
}

def getVersion() {
    return "${env.TAG_NAME ? env.TAG_NAME.substring(1) : 'SNAPSHOT.' + env.BUILD_NUMBER}";
}
