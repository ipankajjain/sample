#!C:\cygwin64\bin\bash
pipelineJob("${SEED_PROJECT}-${SEED_BRANCH}-pipeline") {
            description "Building the ${BRANCH} pipeline."
            parameters {
                        stringParam('GITHUB_PROJECT', "ipankajjain/${SEED_PROJECT}", 'The Github project name including the workspace.')
                        stringParam('BUILD_BRANCH', "${BRANCH}", 'The source branch of the project used for this build.')
                        stringParam('MAVEN_GOAL', "package -Dmaven.test.skip=true", 'The maven goal to be executed.')
                        stringParam('PIPELINE_CREDENTIALS_ID', '${ADMIN_KEY}', 'The ssh API Key used for deployment.')
                        }

    // because stash notifier will not work
    triggers {
        scm('')
    }
    
    logRotator {
        numToKeep(15)
        artifactNumToKeep(1)
    }    

    definition {
        cpsScm {
            scm {
                git('git@github.com:/ipankajjain/pipelines.git', 'origin/master') { node ->
                                                           node / 'userRemoteConfigs' / 'hudson.plugins.git.UserRemoteConfig' {
                                                                       credentialsId('PIPELINE_CREDENTIALS_ID')
                                                                       url('git@github.com:/ipankajjain/pipelines.git')
                                                           }
                                                           node / 'extensions' {
                                                           }
                                       }
            }
            scriptPath("JENKINS_JAVA_PROJECT")
        }
    }
}
 
