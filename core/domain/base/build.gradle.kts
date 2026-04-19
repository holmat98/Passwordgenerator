plugins {
    id(Plugins.JAVA_LIBRARY)
    id(Plugins.KOTLIN_JVM)
}

configureJvm21()

dependencies {
    coroutines()
}
