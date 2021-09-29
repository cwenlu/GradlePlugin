package com.cwl.lib

import org.gradle.api.Plugin
import org.gradle.api.Project
//插件有语法错，或者其他错，都会报找不到插件，没法指示具体错误
class PluginTest implements Plugin<Project> {
    @Override
    void apply(Project project) {
        println("hello plugin")
        def task = project.task("HelloPluginTask",group: 'HelloPlugin') {
            println("HelloPluginTask")
            doLast {
                println("HelloPluginTask exected")
            }
        }

        project.afterEvaluate {
            def mergeDebugResources = project.tasks.findByName("mergeDebugResources")
            mergeDebugResources?.finalizedBy(task)
        }

        //创建并添加属性到扩展中
        def person = project.extensions.create('personInfo',Person.class)

        // ./gradlew printPersonInfo
        project.task('printPersonInfo',group: 'HelloPlugin'){
            doLast {
                println("${person.name}==${person.age}")
            }
        }
    }
}