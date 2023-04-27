buildscript {
    repositories {
        google()
        mavenCentral()
    }

    dependencies {
        classpath(Gradle.DEPENDENCY)
        classpath(Jetbrains.KotlinGradlePlugin.DEPENDENCY)
        classpath(Androidx.Navigation.SAFE_ARGS_DEPENDENCY)
        classpath(GradlePlugins.OSS_LICENSES)
        classpath(Google.PlayServices.DEPENDENCY)
        classpath(Google.PlayServices.Firebase.Crashlytics.GRADLE)
    }
}

plugins {
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
