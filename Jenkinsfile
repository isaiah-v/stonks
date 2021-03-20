pipeline {
    agent none

    environment {
        PROJECT_NAME = 'stonks'
        DOCKER_REGISTRY = credentials('HOST_DOCKER_REGISTRY')
        RUNDECK_JOB_ID = '71dddb62-597c-4f03-b166-bf1590fb76f4'
        RUNDECK_TOKEN = credentials('RUNDECK_TOKEN')
    }

    stages {
        stage('Build amd64') {
            agent { 
                label 'amd64'
            }
            steps {
                buildArtifacts('amd64');
                deployArtifacts('amd64');
                cleanArtifacts('amd64');
            }
        }
        stage('Build arm64') {
            agent { 
                label 'aarch64'
            }
            steps {
                buildArtifacts('arm64');
                deployArtifacts('arm64');
                cleanArtifacts('arm64');
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
        stage('Rundeck Deploy') {
            agent {
                label 'amd64'
            }
            steps {
                rundeckDeploy();
            }
        }
    }
}

def buildArtifacts(arch) {
    echo 'Building...'

    sh "echo ${getVersion()} > src/main/resources/version"

    sh 'chmod +x ./gradlew'
    sh './gradlew build'
    
    sh "docker build --tag \044DOCKER_REGISTRY/$PROJECT_NAME:${arch}-latest ."
}

def deployArtifacts(arch) {
    echo 'Deploying...'
    sh "docker push \044DOCKER_REGISTRY/$PROJECT_NAME:${arch}-latest"
}

def cleanArtifacts(arch) {
    echo 'Cleaning...'
    sh "docker rmi \044DOCKER_REGISTRY/$PROJECT_NAME:${arch}-latest"
}

def rundeckDeploy() {
    echo 'Deploy Application...'
    sh "curl --request POST https://ci.ivcode.org/rundeck/api/36/job/${RUNDECK_JOB_ID}/executions?authtoken=\044RUNDECK_TOKEN"
}

def getVersion() {
    return "${env.TAG_NAME ? env.TAG_NAME.substring(1) : 'SNAPSHOT.' + env.BUILD_NUMBER}";
}
