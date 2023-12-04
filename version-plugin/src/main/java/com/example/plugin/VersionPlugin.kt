package com.example.plugin

import org.gradle.api.Project

/**
 * 虽然这个类是对Plugin接口的一个空实现，但是一定要有，否则后面的构建会报错
 */
class VersionPlugin : org.gradle.api.Plugin<Project>{
    override fun apply(p0: Project) {
    }
}