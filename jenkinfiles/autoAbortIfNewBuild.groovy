import hudson.model.Result
import jenkins.model.CauseOfInterruption

/**
* List of script need to be approved by admin
* method hudson.model.Executor interrupt hudson.model.Result jenkins.model.CauseOfInterruption[]
* method hudson.model.Run getExecutor
* method org.jenkinsci.plugins.workflow.support.steps.build.RunWrapper getRawBuild
* new jenkins.model.CauseOfInterruption$UserInterruption java.lang.String
*/
def abortPreviousRunningBuilds() {
    if (currentBuild.previousBuildInProgress) {
        def exec = currentBuild.previousBuildInProgress.getRawBuild().getExecutor()
        if (exec) {
            exec.interrupt(Result.ABORTED, new CauseOfInterruption.UserInterruption("interrupted by build #${currentBuild.getId()}"))
        }
    }
}

node('slave-1') {
    try {
        
        stage("Checking") {
            abortPreviousRunningBuilds()
        }
        
        stage("prepare") {
            echo "Sleep 10 seconds"
            sh "sleep 10"
        }
        
        stage("build") {
            echo "Sleep 30 seconds"
            sh "sleep 30"
        }
        
        stage("post") {
            echo "Sleep 10 seconds"
            sh "sleep 10"
        }
    } catch (e) {
        echo "Error catched"
        echo e.toString()
    } finally {
        echo "Final steps, Always run"
    }
}
