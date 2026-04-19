plugins {
    id(Plugins.ANDROID_LIBRARY)
    id(Plugins.ANDROID_KOTLIN)
    id(Google.KSP.PLUGIN)
    id(Plugins.JACOCO)
}

android.configure(namespace = "com.mateuszholik.data")

ksp {
    arg("room.schemaLocation", "$projectDir/schemas")
}

dependencies {

    // Modules
    implementation(project(":cryptography"))

    // Dependencies
    common()
    unitTesting()
    androidTesting()
    room()
    rxJava()
    koin()
    securityCrypto()
}

tasks.withType(Test::class) {
    useJUnitPlatform()
}
