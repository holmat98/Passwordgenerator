buildscript {
    repositories {
        google()
        mavenCentral()
    }

    dependencies {
        classpath(GradlePlugins.ANDROID_GRADLE_PLUGIN)
        classpath(GradlePlugins.KOTLIN_GRADLE_PLUGIN)
        classpath(Androidx.Navigation.SAFE_ARGS_DEPENDENCY)
        classpath(Google.PlayServices.OssLicences.PLUGIN)
        classpath(Google.PlayServices.DEPENDENCY)
        classpath(Google.PlayServices.Firebase.Crashlytics.GRADLE)
    }
}

plugins {
    id(AndroidGitVersion.PLUGIN) version AndroidGitVersion.VERSION apply false
    id(Google.KSP.PLUGIN) version Google.KSP.version apply false
}

allprojects {
    repositories {
        google()
        mavenCentral()
    }
}
