plugins {
    id(Plugins.ANDROID_LIBRARY)
    id(Plugins.KOTLIN_ANDROID)
    id(Google.KSP.PLUGIN)
    id(Plugins.JACOCO)
}

android {
    namespace = "com.mateuszholik.data"
    compileSdk = DefaultConfig.COMPILE_SDK

    defaultConfig {
        minSdk = DefaultConfig.MIN_SDK

        testInstrumentationRunner = DefaultConfig.TEST_INSTRUMENTATION_RUNNER

        javaCompileOptions {
            annotationProcessorOptions {
                arguments["room.schemaLocation"] = "$projectDir/schemas"
            }
        }
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
    implementation(project(":cryptography"))

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
    androidTestImplementation(Testing.JUnit.DEPENDENCY)
    androidTestCompileOnly(Testing.JUnit.API_DEPENDENCY)
    androidTestRuntimeOnly(Testing.JUnit.ENGINE)
    androidTestImplementation(Testing.JUnit.PARAMS)
    androidTestImplementation(Testing.AssertJ.DEPENDENCY)

    //room
    implementation(Androidx.Room.ROOM_KTX_DEPENDENCY)
    ksp(Androidx.Room.COMPILER_DEPENDENCY)
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

    // Jetpack security
    implementation(Androidx.Security.CRYPTO_DEPENDENCY)
}

tasks.withType(Test::class) {
    useJUnitPlatform()
}
