package com.github.juliansobott.studytoolplugin

import org.gradle.api.DefaultTask
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.api.tasks.TaskAction

/**
 * Tasks:
 * - publish plugin
 * - Check plugin
 * - test plugin installed
 * - test plugin not installed
 */

const val GROUP = "study tool"

const val TASK_PUBLISH = "publish"
const val TASK_CHECK = "check"
const val TASK_TEST = "test"

class StudyToolPlugin : Plugin<Project> {
    override fun apply(project: Project) {
        project.task("helloLaunch") {
            it.doLast {
                println("Hello from Launch")
            }
        }
        // Register tasks
        project.tasks.register<PublishTask>(TASK_PUBLISH, PublishTask::class.java)
        project.tasks.register<CheckTask>(TASK_CHECK, CheckTask::class.java)
        project.tasks.register<TestPluginTask>(TASK_TEST, TestPluginTask::class.java)
    }
}

open class CheckTask : DefaultTask() {

    init {
        group = GROUP
    }
    @TaskAction
    fun checkPlugin() {
        println("Hello: Checking!")
    }
}

open class TestPluginTask : DefaultTask() {

    init {
        group = GROUP
    }

    @TaskAction
    fun testPlugin() {
        println("Hello: testing!")
    }
}

open class PublishTask : DefaultTask() {

    init {
        group = GROUP
        dependsOn(TASK_CHECK)
    }

    @TaskAction
    fun publishStudyPlugin() {
        println("Hello: Publishing!")
    }
}
