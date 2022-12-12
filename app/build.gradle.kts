plugins {
    id("com.android.application")
    id("kotlin-android")
    kotlin("kapt")
    id("androidx.navigation.safeargs")
}

android {
    compileSdk = Dependencies.DefaultConfig.COMPILE_SDK

    defaultConfig {
        applicationId = Dependencies.DefaultConfig.APPLICATION_ID
        minSdk = Dependencies.DefaultConfig.MIN_SDK
        targetSdk = Dependencies.DefaultConfig.TARGET_SDK
        versionCode = Dependencies.DefaultConfig.VERSION_CODE
        versionName = Dependencies.DefaultConfig.VERSION_NAME

        testInstrumentationRunner = Dependencies.DefaultConfig.TEST_INSTRUMENTATION_RUNNER
    }

    buildFeatures {
        viewBinding = true
        dataBinding = true
    }

    buildTypes {
        release {
            isMinifyEnabled = true
            isShrinkResources = true
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

    // Modules
    implementation(project(":data"))
    implementation(project(":domain"))
    implementation(project(":passwordvalidation"))

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

    // Navigation
    implementation(Dependencies.Androidx.Navigation.FRAGMENT_NAVIGATION_DEPENDENCY)
    implementation(Dependencies.Androidx.Navigation.UI_KTX_NAVIGATION_DEPENDENCY)

    // Material design
    implementation(Dependencies.Google.MaterialDesign.DEPENDENCY)

    // Constraint layout
    implementation(Dependencies.Androidx.ConstraintLayout.DEPENDENCY)

    //room
    implementation(Dependencies.Androidx.Room.ROOM_KTX_DEPENDENCY)
    kapt(Dependencies.Androidx.Room.COMPILER_DEPENDENCY)
    implementation(Dependencies.Androidx.Room.ROOM_KTX_DEPENDENCY)
    testImplementation(Dependencies.Androidx.Room.TEST_DEPENDENCY)

    // ViewModel
    implementation(Dependencies.Androidx.Lifecycle.ViewModel.DEPENDENCY)

    // Jetpack security
    implementation(Dependencies.Androidx.Security.CRYPTO_DEPENDENCY)
    implementation(Dependencies.Androidx.Security.APP_AUTHENTICATOR_DEPENDENCY)
    implementation(Dependencies.Androidx.Security.IDENTITY_CREDENTIAL_DEPENDENCY)
    androidTestImplementation(Dependencies.Androidx.Security.APP_AUTHENTICATOR_TESTING_DEPENDENCY)

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

    // viewpager
    implementation(Dependencies.Androidx.ViewPager.DEPENDENCY)

    // lottie
    implementation(Dependencies.Lottie.DEPENDENCY)

    // gson converter
    implementation(Dependencies.Retrofit.Converter.DEPENDENCY)

    // timber
    implementation(Dependencies.Timber.DEPENDENCY)

    // worker manager
    implementation(Dependencies.Androidx.WorkManager.DEPENDENCY)
    implementation(Dependencies.Androidx.WorkManager.Rx.DEPENDENCY)

    // biometric manager
    implementation(Dependencies.Androidx.Biometric.DEPENDENCY)

    // leak canary
    debugImplementation(Dependencies.SquareUp.LeakCanary.DEPENDENCY)
}

tasks.withType<Test> {
    useJUnitPlatform()
}
