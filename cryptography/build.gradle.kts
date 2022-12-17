plugins {
    id("com.android.library")
    id("org.jetbrains.kotlin.android")
    kotlin("kapt")
}

apply(from="${project.rootDir}/jacoco.gradle")

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

    // koin
    implementation(Koin.DEPENDENCY)
    implementation(Koin.ViewModel.DEPENDENCY)
    implementation(Koin.Scope.DEPENDENCY)

    // mockk
    testImplementation(Mockk.DEPENDENCY)
    androidTestImplementation(Mockk.Android.DEPENDENCY)

    // Jetpack security
    implementation(Androidx.Security.CRYPTO_DEPENDENCY)
    implementation(Androidx.Security.APP_AUTHENTICATOR_DEPENDENCY)
    implementation(Androidx.Security.IDENTITY_CREDENTIAL_DEPENDENCY)
    androidTestImplementation(Androidx.Security.APP_AUTHENTICATOR_TESTING_DEPENDENCY)
}

tasks.withType(Test::class) {
    useJUnitPlatform()
}