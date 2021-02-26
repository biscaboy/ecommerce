pipeline {
  agent any

  stages {
    stage('Build') {
      steps {
        sh 'export MAVEN_OPTS="-Xmx512M -XX:MaxPermSize=512m" && mvn -B -DskipTests clean package'
      }
    }

    stage('Test') {
      steps {
        sh 'mvn test'
      }
    }
  }
}