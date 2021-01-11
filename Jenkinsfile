pipeline {
    agent { docker { image 'ocean' } }
    stages {
        stage('build') {
            steps {
                sh 'mvn --version'
            }
        }
    }
}
