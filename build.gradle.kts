// Top-level build file where you can add configuration options common to all sub-projects/modules.
buildscript {
    repositories {
        google()
        mavenCentral()
    }

    dependencies {
        classpath(Gradle.DEPENDENCY)
        classpath(Jetbrains.KotlinGradlePlugin.DEPENDENCY)
        classpath(Androidx.Navigation.SAFE_ARGS_DEPENDENCY)
        classpath(Jacoco.DEPENDENCY)
    }
}

allprojects {
    repositories {
        google()
        mavenCentral()
    }
}

tasks {
    register("clean", Delete::class) {
        delete(rootProject.buildDir)
    }
}