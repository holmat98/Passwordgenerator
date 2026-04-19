plugins {
    id(Plugins.ANDROID_LIBRARY)
    id(Plugins.ANDROID_KOTLIN)
    id(Plugins.JACOCO)
}

android.configure(namespace = "com.mateuszholik.domain")

dependencies {

    // Modules
    implementation(project(":data"))
    implementation(project(":cryptography"))
    implementation(project(":passwordvalidation"))
    implementation(project(":core:domain:base"))

    // Dependencies
    common()
    unitTesting()
    rxJava()
    koin()
}

tasks.withType<Test> {
    useJUnitPlatform()
}
