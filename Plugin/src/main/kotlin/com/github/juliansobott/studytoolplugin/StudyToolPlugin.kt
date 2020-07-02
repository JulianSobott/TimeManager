package com.github.juliansobott.studytoolplugin

import org.gradle.api.Plugin
import org.gradle.api.Project

class StudyToolPlugin : Plugin<Project> {
    override fun apply(project: Project) {
        project.task("helloLaunch") {
            it.doLast {
                println("Hello from Launch")
            }
        }
    }
}