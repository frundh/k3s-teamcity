import jetbrains.buildServer.configs.kotlin.v2018_2.*

object Drone_PoC : BuildType({
    name = "Hello Drone"
    params {...}
    vcs {...}

    steps {
        script {
            name = "Env"
            scriptContent = """
                cat >> .env <<EOL
                RELEASE_BRANCH=master
                DRONE_COMMIT_SHA=%build.vcs.number%
                DRONE_DOCKER_REGISTRY_USERNAME=%Docker.Registry.Username%
                DRONE_DOCKER_REGISTRY_PASSWORD=%Docker.Registry.Password%
                EOL
            """.trimIndent()
        }
        script {
            name = "Drone Exec"
            scriptContent = "docker run --rm -v /var/run/docker.sock:/var/run/docker.sock -v ${'$'}(pwd):${'$'}(pwd) -w ${'$'}(pwd) --env-file .env drone/cli exec --env-file=.env"
        }
    }
})
