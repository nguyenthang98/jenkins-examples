node('slave-1') {
    try {

        stage("prepare") {
            echo "Sleep 2 seconds"
            sh "sleep 2"
        }

        stage("build") {
            echo "Sleep 10 seconds"
            sh "sleep 10"
        }

        stage("post") {
            echo "Sleep 2 seconds"
            sh "sleep 2"
        }
    } catch (e) {
        echo "Error catched"
        echo e.toString()
    } finally {
        echo "Final steps, Always run"
        
        def build = currentBuild
        def index = 5
        def durationAvg = build.duration
        sh "rm test.html"
        while (index > 0) {
            if (build) {
                def buildStartTime = new Date(build.startTimeInMillis).format("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'")
                sh "echo '<div>Build <strong>${build.displayName}</strong> (${build.currentResult}): Start: ${buildStartTime}, Duration: ${build.durationString}ms</div>' >> test.html"
                durationAvg = (durationAvg + build.duration) / 2
                build = build.previousBuild
                --index
            } else {
                break
            }
        }
        sh "echo '<br/><div>Duration Avg: ${durationAvg} ms</div>' >> test.html"
        archiveArtifacts artifacts: 'test.html', followSymlinks: false
    }
}
