// Top-level build file where you can add configuration options common to all sub-projects/modules.
buildscript {
    repositories {
        google()
        mavenCentral()
    }

    dependencies {
        classpath(Dependencies.Gradle.DEPENDENCY)
        classpath(Dependencies.Jetbrains.KotlinGradlePlugin.DEPENDENCY)
        classpath(Dependencies.Androidx.Navigation.SAFE_ARGS_DEPENDENCY)
    }
}

tasks {
    register("clean", Delete::class) {
        delete(rootProject.buildDir)
    }
}