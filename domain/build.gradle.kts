plugins {
    id(Plugins.ANDROID_LIBRARY)
    id(Plugins.KOTLIN_ANDROID)
    id(Plugins.JACOCO)
}

android.configure(namespace = "com.mateuszholik.domain")

dependencies {

    // Modules
    implementation(project(":data"))
    implementation(project(":cryptography"))
    implementation(project(":passwordvalidation"))

    // Dependencies
    common()
    unitTesting()
    rxJava()
    koin()
}

tasks.withType<Test> {
    useJUnitPlatform()
}
