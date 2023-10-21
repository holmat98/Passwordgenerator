plugins {
    id(Plugins.ANDROID_LIBRARY)
    id(Plugins.KOTLIN_ANDROID)
    id(Plugins.JACOCO)
}

android {
    namespace = "com.mateuszholik.domain"
    compileSdk = DefaultConfig.COMPILE_SDK

    defaultConfig {
        minSdk = DefaultConfig.MIN_SDK

        testInstrumentationRunner = DefaultConfig.TEST_INSTRUMENTATION_RUNNER
    }

    buildTypes {
        release {
            proguardFiles(
                getDefaultProguardFile(Proguard.FILE),
                Proguard.RULES
            )
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
    }
    kotlinOptions {
        jvmTarget = "17"
    }
}

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
