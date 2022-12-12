plugins {
    id("com.android.library")
    id("org.jetbrains.kotlin.android")
    kotlin("kapt")
}

android {
    compileSdk = Dependencies.DefaultConfig.COMPILE_SDK

    defaultConfig {
        minSdk = Dependencies.DefaultConfig.MIN_SDK
        targetSdk = Dependencies.DefaultConfig.TARGET_SDK

        testInstrumentationRunner = Dependencies.DefaultConfig.TEST_INSTRUMENTATION_RUNNER
    }

    buildTypes {
        release {
            isMinifyEnabled = true
            proguardFiles(
                getDefaultProguardFile(Dependencies.Proguard.FILE),
                Dependencies.Proguard.RULES
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

    // Core ktx
    implementation(Dependencies.Androidx.Core.DEPENDENCY)

    // Appcompat
    implementation(Dependencies.Androidx.AppCompat.DEPENDENCY)

    // Tests
    testImplementation(Dependencies.Testing.JUnit.DEPENDENCY)
    testCompileOnly(Dependencies.Testing.JUnit.API_DEPENDENCY)
    testRuntimeOnly(Dependencies.Testing.JUnit.ENGINE)
    testImplementation(Dependencies.Testing.JUnit.PARAMS)
    androidTestImplementation(Dependencies.Testing.Espresso.DEPENDENCY)
    testImplementation(Dependencies.Testing.AssertJ.DEPENDENCY)
    testImplementation(Dependencies.Androidx.Core.Testing.DEPENDENCY)

    //room
    implementation(Dependencies.Androidx.Room.ROOM_KTX_DEPENDENCY)
    kapt(Dependencies.Androidx.Room.COMPILER_DEPENDENCY)
    implementation(Dependencies.Androidx.Room.ROOM_KTX_DEPENDENCY)
    testImplementation(Dependencies.Androidx.Room.TEST_DEPENDENCY)

    // RxJava
    implementation(Dependencies.RxJava.DEPENDENCY)
    implementation(Dependencies.RxJava.Android.DEPENDENCY)
    implementation(Dependencies.Androidx.Room.RX_JAVA_DEPENDENCY)

    // koin
    implementation(Dependencies.Koin.DEPENDENCY)
    implementation(Dependencies.Koin.ViewModel.DEPENDENCY)
    implementation(Dependencies.Koin.Scope.DEPENDENCY)

    // mockk
    testImplementation(Dependencies.Mockk.DEPENDENCY)
    androidTestImplementation(Dependencies.Mockk.Android.DEPENDENCY)
}

tasks.withType<Test> {
    useJUnitPlatform()
}
