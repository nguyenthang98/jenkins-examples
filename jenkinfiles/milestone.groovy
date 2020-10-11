node('slave-1') {
    try {
        def buildNumber = env.BUILD_NUMBER as int
        if (buildNumber > 1) milestone(buildNumber - 1)
        milestone(buildNumber)
        
        stage("prepare") {
            echo "Sleep for 20 seconds"
            sh "sleep 20"
        }
        stage('build') {
            echo "Sleep for 30 seconds"
            sh "sleep 30"
        }
        stage('post-build') {
            echo "Sleep for 4 seconds"
            sh "sleep 4"
        }
    } catch (e) {
        echo "Failed build"
    } finally {
        echo "Finally steps. Contains error"
    }
}
