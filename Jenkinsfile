pipeline {
  agent {
    docker {
      image 'maven:3.3.3'
      args '-v /root/.m2:/root/.m2'
    }

  }
  stages {
    stage('Build') {
      steps {
        sh 'export MAVEN_OPTS="-Xmx512M -XX:MaxPermSize=512m"'
        sh 'java -version'
        sh 'mvn -B -DskipTests clean package'
      }
    }

    stage('Test') {
      steps {
        sh 'mvn test'
      }
    }

  }
}