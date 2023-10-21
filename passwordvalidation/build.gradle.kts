plugins {
    id(Plugins.ANDROID_LIBRARY)
    id(Plugins.KOTLIN_ANDROID)
    id(Google.KSP.PLUGIN)
    id(Plugins.JACOCO)
}

android.configure(namespace = "com.mateuszholik.passwordvalidation")

dependencies {
    common()
    unitTesting()
    room()
    rxJava()
    koin()
}

tasks.withType(Test::class) {
    useJUnitPlatform()
}
