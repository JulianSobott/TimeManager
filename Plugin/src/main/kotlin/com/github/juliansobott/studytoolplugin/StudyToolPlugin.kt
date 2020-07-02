package com.github.juliansobott.studytoolplugin

import kotlinx.serialization.Serializable
import kotlinx.serialization.json.Json
import kotlinx.serialization.json.JsonConfiguration
import kotlinx.serialization.stringify
import org.gradle.api.DefaultTask
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.api.tasks.TaskAction
import org.gradle.api.tasks.bundling.Jar
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

const val META_FILE = "plugin_load.json"

class StudyToolPlugin : Plugin<Project> {
    override fun apply(project: Project) {
        // Plugin dependencies
        project.plugins.apply("java")


        // Extension
        val ext = project.extensions.create<StudyToolExtension>("StudyTool", StudyToolExtension::class.java)

        // Config

        // Register tasks
        project.tasks.register<BuildPublishTask>(TASK_BUILD_PUBLISH, BuildPublishTask::class.java)
        project.tasks.register<PublishTask>(TASK_PUBLISH, PublishTask::class.java)
        project.tasks.register<CheckTask>(TASK_CHECK, CheckTask::class.java)
        project.tasks.register<TestPluginTask>(TASK_TEST, TestPluginTask::class.java)

        // Edit tasks
        project.tasks.getByPath("jar").doFirst {
            if (it is Jar) {
                // create meta file
                val json = Json(JsonConfiguration.Stable)
                val data = json.stringify(LoadDataExtension.serializer(), ext.loadData)
                println(data)
                val path = "build/resources/$META_FILE"
                File(path).writeText(data)
                // add meta file
                it.from(path)
            } else {
                // Empty: Project is wrong configured
            }
        }

        // Task dependencies
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

    init {
        group = GROUP

        archiveBaseName.set("publish")
        destinationDirectory.set(File("build/"))

        // info files
        doFirst {
            val ext = project.extensions.getByType(StudyToolExtension::class.java)
            val infoFiles = File(ext.infoPath)
            if (!infoFiles.exists()) {
                throw FileNotFoundException("Info folder not found: ${ext.infoPath}")
            }
            into("info").from(infoFiles)
        }

        // jar file
        from(File("build/libs"))
    }
}


open class StudyToolExtension {
    var infoPath: String = "info"
    var loadData: LoadDataExtension = LoadDataExtension()
}

@Serializable
open class LoadDataExtension {
    var windowFxml: String = "main.fxml"
    var settingsFxml: String = "settings.fxml"
    var icon: String = "icon.png"
}