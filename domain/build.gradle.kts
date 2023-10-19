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

    // Core ktx
    implementation(Androidx.Core.DEPENDENCY)

    // Appcompat
    implementation(Androidx.AppCompat.DEPENDENCY)

    // Tests
    testImplementation(Testing.JUnit.DEPENDENCY)
    testCompileOnly(Testing.JUnit.API_DEPENDENCY)
    testRuntimeOnly(Testing.JUnit.ENGINE)
    testImplementation(Testing.JUnit.PARAMS)
    testImplementation(Testing.AssertJ.DEPENDENCY)
    testImplementation(Androidx.Core.Testing.DEPENDENCY)

    // RxJava
    implementation(RxJava.DEPENDENCY)
    implementation(RxJava.Android.DEPENDENCY)
    implementation(Androidx.Room.RX_JAVA_DEPENDENCY)

    // koin
    implementation(Koin.DEPENDENCY)

    // mockk
    testImplementation(Mockk.DEPENDENCY)
    androidTestImplementation(Mockk.Android.DEPENDENCY)
}

tasks.withType<Test> {
    useJUnitPlatform()
}
