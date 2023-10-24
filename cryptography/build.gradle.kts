plugins {
    id(Plugins.ANDROID_LIBRARY)
    id(Plugins.KOTLIN_ANDROID)
    id(Plugins.JACOCO)
}

android.configure(namespace = "com.mateuszholik.cryptography")

dependencies {

    // Dependencies
    common()
    unitTesting()
    koin()
    cryptography()
}

tasks.withType(Test::class) {
    useJUnitPlatform()
}
