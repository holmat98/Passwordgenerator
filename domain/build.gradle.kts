plugins {
    id(Plugins.ANDROID_LIBRARY)
    id(Plugins.KOTLIN_ANDROID)
    kotlin(Plugins.KAPT)
}

apply(from="../jacoco.gradle")

android {
    compileSdk = DefaultConfig.COMPILE_SDK

    defaultConfig {
        minSdk = DefaultConfig.MIN_SDK
        targetSdk = DefaultConfig.TARGET_SDK

        testInstrumentationRunner = DefaultConfig.TEST_INSTRUMENTATION_RUNNER
    }

    buildTypes {
        release {
            isMinifyEnabled = true
            proguardFiles(
                getDefaultProguardFile(Proguard.FILE),
                Proguard.RULES
            )
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }
    kotlinOptions {
        jvmTarget = "1.8"
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

    //room
    implementation(Androidx.Room.ROOM_KTX_DEPENDENCY)
    kapt(Androidx.Room.COMPILER_DEPENDENCY)
    implementation(Androidx.Room.ROOM_KTX_DEPENDENCY)
    testImplementation(Androidx.Room.TEST_DEPENDENCY)

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
