package com.example.plugin

object Versions {
    val compileSdk = 33
    val minSdkVersion = 26
    val targetSdkVersion = 33
    val versionCode = 1
    val versionName = "1.0"

    val core_ktx = "1.9.0"
    val androidx_appcompat = "1.6.1"
    val material = "1.9.0"
    val constraintlayout = "2.1.4"
    val lifecycle = "2.6.2"
    val asm = "7.1"
    val auto_service = "1.1.1"
    val core_google_shortcuts = "1.1.0"
    val arouter = "1.5.2"
    val play_services_auth = "20.5.0"
    val firebase_bom = "31.0.2"
    val kotlinpoet = "1.10.2"
}

object Dependencies {
    val core_ktx = "androidx.core:core-ktx:${Versions.core_ktx}"
    val androidx_appcompat = "androidx.appcompat:appcompat:${Versions.androidx_appcompat}"
    val material = "com.google.android.material:material:${Versions.material}"
    val constraintlayout = "androidx.constraintlayout:constraintlayout:${Versions.constraintlayout}"
    val lifecycle_process = "androidx.lifecycle:lifecycle-process:${Versions.lifecycle}"
    val lifecycle_viewmodel_ktx = "androidx.lifecycle:lifecycle-viewmodel-ktx:${Versions.lifecycle}"
    val lifecycle_runtime_ktx = "androidx.lifecycle:lifecycle-runtime-ktx:${Versions.lifecycle}"
    val asm = "org.ow2.asm:asm:${Versions.asm}"
    val asm_commons = "org.ow2.asm:asm-commons:${Versions.asm}"
    val auto_service = "com.google.auto.service:auto-service:${Versions.auto_service}"
    val core_google_shortcuts = "androidx.core:core-google-shortcuts:${Versions.core_google_shortcuts}"
    val arouter_api = "com.alibaba:arouter-api:${Versions.arouter}"
    val arouter_compiler = "com.alibaba:arouter-compiler:${Versions.arouter}"
    val play_services_auth = "com.google.android.gms:play-services-auth:${Versions.play_services_auth}"
    val firebase_bom = "com.google.firebase:firebase-bom:${Versions.firebase_bom}"
    val kotlinpoet = "com.squareup:kotlinpoet:${Versions.kotlinpoet}"
}