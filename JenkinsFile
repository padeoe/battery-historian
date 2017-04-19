node {
    stage('SCM') {
        git 'https://github.com/padeoe/battery-historian.git/'
    }
    stage('build') {
        def mvnHome = tool 'M3'
        bat "${mvnHome}/bin/mvn -B clean package"
    }
    stage('deploy') {
        bat "docker cp target/ROOT.war mytomcat:/usr/local/tomcat/webapps"
    }
    stage('results') {
        archiveArtifacts artifacts: '**/target/*.war', fingerprint: true
    }
}