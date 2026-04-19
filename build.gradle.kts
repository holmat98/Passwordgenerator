buildscript {
    repositories {
        google()
        mavenCentral()
    }

    dependencies {
        classpath(AndroidX.Navigation.SAFE_ARGS_DEPENDENCY)
        classpath(Google.PlayServices.OssLicences.PLUGIN)
        classpath(Google.PlayServices.DEPENDENCY)
        classpath(Google.PlayServices.Firebase.Crashlytics.GRADLE)
    }
}

plugins {
    id(AndroidX.Compose.COMPILER_PLUGIN) version AndroidX.Compose.KOTLIN_COMPILER_PLUGIN_VERSION apply false
    id(AndroidGitVersion.PLUGIN) version AndroidGitVersion.VERSION apply false
    id(Google.Hilt.PLUGIN) version Google.Hilt.VERSION apply false
    id(Google.KSP.PLUGIN) version Google.KSP.VERSION apply false
}

allprojects {
    repositories {
        google()
        mavenCentral()
    }
}
