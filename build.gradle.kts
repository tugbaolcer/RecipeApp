// Top-level build file where you can add configuration options common to all sub-projects/modules.
plugins {
    alias(libs.plugins.android.application) apply false
    alias(libs.plugins.kotlin.android) apply false
    alias(libs.plugins.hilt.android) apply false
    alias(libs.plugins.ksp) apply false
    alias(libs.plugins.google.gms.google.services) apply false

}

buildscript {
    repositories {
        google()
        maven {
            url = uri("https://maven.fabric.io/public")
        }
        mavenCentral()
    }
    dependencies {
        classpath (libs.android.gradle.plugin)
        classpath (libs.kotlin.gradle.plugin)
        classpath (libs.google.services)
        classpath(libs.firebase.crashlytics.gradle)
    }

}