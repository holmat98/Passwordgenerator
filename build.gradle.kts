buildscript {
    repositories {
        google()
        mavenCentral()
    }

    dependencies {
        classpath(GradlePlugins.ANDROID_GRADLE_PLUGIN)
        classpath(GradlePlugins.KOTLIN_GRADLE_PLUGIN)
        classpath(Androidx.Navigation.SAFE_ARGS_DEPENDENCY)
        classpath(GradlePlugins.OSS_LICENSES)
        classpath(Google.PlayServices.DEPENDENCY)
        classpath(Google.PlayServices.Firebase.Crashlytics.GRADLE)
    }
}

plugins {
    id(GradlePlugins.KOTLIN_JVM) version GradlePlugins.kotlinVersion apply false
    id(AndroidGitVersion.PLUGIN) version AndroidGitVersion.VERSION apply false
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
