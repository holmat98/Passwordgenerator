buildscript {
    repositories {
        google()
        mavenCentral()
    }

    dependencies {
        classpath(Gradle.DEPENDENCY)
        classpath(Jetbrains.KotlinGradlePlugin.DEPENDENCY)
        classpath(Androidx.Navigation.SAFE_ARGS_DEPENDENCY)
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