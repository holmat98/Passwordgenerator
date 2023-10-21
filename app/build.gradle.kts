plugins {
    id(Plugins.ANDROID_APPLICATION)
    id(Plugins.KOTLIN_ANDROID)
    kotlin(Plugins.KAPT)
    id(Plugins.SAFE_ARGS)
    id(Plugins.OSS_LICENSES)
    id(Plugins.GOOGLE_SERVICES)
    id(Plugins.FIREBASE_CRASHLYTICS)
    id(AndroidGitVersion.PLUGIN)
    id(Plugins.KOTLIN_PARCELIZE)
    id(Plugins.JACOCO)
}

androidGitVersion {
    format = "%tag%%-commit%%-dirty%"
    parts = 4
    multiplier = 100
}

android {
    namespace = "com.mateuszholik.passwordgenerator"
    compileSdk = DefaultConfig.COMPILE_SDK

    buildFeatures {
        buildConfig = true
    }

    defaultConfig {
        applicationId = DefaultConfig.APPLICATION_ID
        minSdk = DefaultConfig.MIN_SDK
        targetSdk = DefaultConfig.TARGET_SDK
        versionCode = androidGitVersion.code()
        versionName = androidGitVersion.name()

        testInstrumentationRunner = DefaultConfig.TEST_INSTRUMENTATION_RUNNER
    }

    buildFeatures {
        viewBinding = true
        dataBinding = true
    }

    buildTypes {
        debug {
            applicationIdSuffix = ".debug"
        }
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
    implementation(project(":domain"))
    implementation(project(":passwordvalidation"))

    // Dependencies
    common()
    ui()
    fragmentNavigation()
    viewModel()
    unitTesting()
    rxJava()
    koin(true)
    logging()
    workManager()
    biometricManager()
    leakCanary()
    ossLicenses()
    crashlytics()
    autofill()
}

tasks.withType(Test::class) {
    useJUnitPlatform()
}
