pipeline {
    agent none;

    environment {
        PROJECT_NAME = 'stonks'
        DOCKER_REGISTRY = credentials('HOST_DOCKER_REGISTRY')
        RUNDECK_JOB_ID = '69a6482f-c285-4e14-a915-03f5b4e665f4'
        RUNDECK_TOKEN = credentials('RUNDECK_TOKEN')
    }

    stages {
        stage('Build Arm64') {
            agent {
                label 'arm64'
            }
            steps {
                doBuild('arm64');
                doDeploy('arm64');
                doClean('arm64');
            }
        }
        stage('Build Amd64') {
            agent {
                label 'amd64'
            }
            steps {
                doBuild('amd64');
                doDeploy('amd64');
                doClean('amd64');
                doManifest();
            }
        }
        stage('Run') {
            agent {
                label 'amd64'
            }
            steps {
                doRun();
            }
        }
    }
}

def doBuild(arch) {
    echo 'Building...'

    sh "echo ${getVersion()} > src/main/resources/version"
    sh 'chmod +x ./gradlew'
    sh './gradlew build'
    
    sh "docker build --tag \044DOCKER_REGISTRY/$PROJECT_NAME:${arch}-latest ."
}

def doDeploy(arch) {
    echo 'Deploying...'
    
    sh "docker push \044DOCKER_REGISTRY/$PROJECT_NAME:${arch}-latest"
}

def doClean(arch) {
    echo 'Cleaning...'
    
    sh "docker rmi \044DOCKER_REGISTRY/$PROJECT_NAME:${arch}-latest"
}
def doManifest() {
    sh "docker manifest create --insecure --amend \044DOCKER_REGISTRY/$PROJECT_NAME:latest \044DOCKER_REGISTRY/$PROJECT_NAME:amd64-latest \044DOCKER_REGISTRY/$PROJECT_NAME:arm64-latest"
    sh "docker manifest push --insecure --purge \044DOCKER_REGISTRY/$PROJECT_NAME:latest"
}

def doRun() {
    echo 'Running...'

    echo 'Deploying Application...'
    sh "curl --request POST https://ci.ivcode.org/rundeck/api/36/job/${RUNDECK_JOB_ID}/executions?authtoken=\044RUNDECK_TOKEN"
}

def getVersion() {
    return "${env.TAG_NAME ? env.TAG_NAME.substring(1) : 'SNAPSHOT.' + env.BUILD_NUMBER}";
}
