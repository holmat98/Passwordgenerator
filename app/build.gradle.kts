plugins {
    id("com.android.application")
    id("kotlin-android")
    kotlin("kapt")
    id("androidx.navigation.safeargs")
    id("com.google.android.gms.oss-licenses-plugin")
}

apply(from="../jacoco.gradle")

android {
    compileSdk = DefaultConfig.COMPILE_SDK

    defaultConfig {
        applicationId = DefaultConfig.APPLICATION_ID
        minSdk = DefaultConfig.MIN_SDK
        targetSdk = DefaultConfig.TARGET_SDK
        versionCode = DefaultConfig.VERSION_CODE
        versionName = DefaultConfig.VERSION_NAME

        testInstrumentationRunner = DefaultConfig.TEST_INSTRUMENTATION_RUNNER
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
    implementation(project(":domain"))
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
    androidTestImplementation(Testing.Espresso.DEPENDENCY)
    testImplementation(Testing.AssertJ.DEPENDENCY)
    testImplementation(Androidx.Core.Testing.DEPENDENCY)

    // Navigation
    implementation(Androidx.Navigation.FRAGMENT_NAVIGATION_DEPENDENCY)
    implementation(Androidx.Navigation.UI_KTX_NAVIGATION_DEPENDENCY)

    // Material design
    implementation(Google.MaterialDesign.DEPENDENCY)

    // Constraint layout
    implementation(Androidx.ConstraintLayout.DEPENDENCY)

    //room
    implementation(Androidx.Room.ROOM_KTX_DEPENDENCY)
    kapt(Androidx.Room.COMPILER_DEPENDENCY)
    implementation(Androidx.Room.ROOM_KTX_DEPENDENCY)
    testImplementation(Androidx.Room.TEST_DEPENDENCY)

    // ViewModel
    implementation(Androidx.Lifecycle.ViewModel.DEPENDENCY)

    // Jetpack security
    implementation(Androidx.Security.CRYPTO_DEPENDENCY)
    implementation(Androidx.Security.APP_AUTHENTICATOR_DEPENDENCY)
    implementation(Androidx.Security.IDENTITY_CREDENTIAL_DEPENDENCY)
    androidTestImplementation(Androidx.Security.APP_AUTHENTICATOR_TESTING_DEPENDENCY)

    // RxJava
    implementation(RxJava.DEPENDENCY)
    implementation(RxJava.Android.DEPENDENCY)
    implementation(Androidx.Room.RX_JAVA_DEPENDENCY)

    // koin
    implementation(Koin.DEPENDENCY)
    implementation(Koin.ViewModel.DEPENDENCY)
    implementation(Koin.Scope.DEPENDENCY)

    // mockk
    testImplementation(Mockk.DEPENDENCY)
    androidTestImplementation(Mockk.Android.DEPENDENCY)

    // viewpager
    implementation(Androidx.ViewPager.DEPENDENCY)

    // lottie
    implementation(Lottie.DEPENDENCY)

    // gson converter
    implementation(Retrofit.Converter.DEPENDENCY)

    // timber
    implementation(Timber.DEPENDENCY)

    // worker manager
    implementation(Androidx.WorkManager.DEPENDENCY)
    implementation(Androidx.WorkManager.Rx.DEPENDENCY)

    // biometric manager
    implementation(Androidx.Biometric.DEPENDENCY)

    // leak canary
    debugImplementation(SquareUp.LeakCanary.DEPENDENCY)

    // splash screen
    implementation(Androidx.SplashScreen.DEPENDENCY)

    // oss licenses
    implementation(Google.PlayServices.OssLicences.DEPENDENCY)
}

tasks.withType(Test::class) {
    useJUnitPlatform()
}
