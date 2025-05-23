// Top-level build file where you can add configuration options common to all sub-projects/modules.


buildscript {
    repositories {
        // other repositories...
        mavenCentral()
        google()
    }
    dependencies {
        // other plugins...
        classpath ("com.google.dagger:hilt-android-gradle-plugin:2.52")
        classpath   ("com.google.gms:google-services:4.4.0")
    }
}



plugins {
    alias(libs.plugins.android.application) apply false
    alias(libs.plugins.kotlin.android) apply false
    alias(libs.plugins.kotlin.compose) apply false
    id("com.google.gms.google-services") version "4.4.2" apply false
    id("com.google.dagger.hilt.android") version "2.44" apply false
    id("com.google.firebase.crashlytics") version "3.0.3" apply false
}