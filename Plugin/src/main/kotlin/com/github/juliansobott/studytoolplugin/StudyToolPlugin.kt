package com.github.juliansobott.studytoolplugin

import org.gradle.api.Action
import org.gradle.api.DefaultTask
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.api.file.FileCollection
import org.gradle.api.internal.file.copy.CopyAction
import org.gradle.api.tasks.TaskAction
import org.gradle.api.tasks.bundling.Zip
import java.io.File
import java.io.FileNotFoundException

/**
 * Tasks:
 * - publish plugin
 * - Check plugin
 * - test plugin installed
 * - test plugin not installed
 */

const val GROUP = "study tool"

const val TASK_PUBLISH = "publishPlugin"
const val TASK_BUILD_PUBLISH = "buildPublish"
const val TASK_CHECK = "checkPlugin"
const val TASK_TEST = "testPlugin"

class StudyToolPlugin : Plugin<Project> {
    override fun apply(project: Project) {
        // Extension
        project.extensions.create<StudyToolExtension>("StudyTool", StudyToolExtension::class.java)

        // Register tasks
        project.tasks.register<BuildPublishTask>(TASK_BUILD_PUBLISH, BuildPublishTask::class.java)
        project.tasks.register<PublishTask>(TASK_PUBLISH, PublishTask::class.java)
        project.tasks.register<CheckTask>(TASK_CHECK, CheckTask::class.java)
        project.tasks.register<TestPluginTask>(TASK_TEST, TestPluginTask::class.java)

        // Dependencies
        project.tasks.getByPath(TASK_BUILD_PUBLISH).dependsOn("clean", "jar", TASK_CHECK)
        project.tasks.getByPath(TASK_PUBLISH).dependsOn(TASK_BUILD_PUBLISH)
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

    }

    @TaskAction
    fun publishStudyPlugin() {
        println("Hello: Publishing!")
    }
}


open class BuildPublishTask : Zip() {

    var infoPath: String = ""

    init {
        group = GROUP

        archiveBaseName.set("publish")
        destinationDirectory.set(File("build/"))
        from(File("build/libs"))

        doFirst {
            check()
        }
    }

    private fun check() {
        val ext = project.extensions.getByType(StudyToolExtension::class.java)
        println(ext.infoPath)
        infoPath = ext.infoPath

        println("Info Path: $infoPath")

        from(File("build/libs"))
        val infoFiles = File(infoPath)
        into("info").from(infoFiles)
        if (!infoFiles.exists()) {
            throw FileNotFoundException("Info files not found: $infoPath")
        }
    }
}


open class StudyToolExtension {
    var infoPath: String = "info"
}