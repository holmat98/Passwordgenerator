import com.android.build.gradle.internal.dsl.BaseAppModuleExtension
import org.gradle.api.JavaVersion

fun BaseAppModuleExtension.configure(
    namespace: String,
    versionCode: Int,
    versionName: String,
) {
    this.namespace = namespace
    compileSdk = DefaultConfig.COMPILE_SDK

    configureDefaultConfig(
        namespace = namespace,
        versionCode = versionCode,
        versionName = versionName
    )
    configureComposeFeatures()
    configureDefaultBuildTypes()
    configureJava()
    configurePackaging()
}

private fun BaseAppModuleExtension.configureDefaultConfig(
    namespace: String,
    versionCode: Int,
    versionName: String,
) {
    defaultConfig {
        applicationId = namespace
        minSdk = DefaultConfig.MIN_SDK
        targetSdk = DefaultConfig.TARGET_SDK
        this.versionCode = versionCode
        this.versionName = versionName

        vectorDrawables {
            useSupportLibrary = true
        }

        testInstrumentationRunner = DefaultConfig.TEST_INSTRUMENTATION_RUNNER
    }
}

private fun BaseAppModuleExtension.configureComposeFeatures() {
    buildFeatures {
        buildConfig = true
        compose = true
    }
}

private fun BaseAppModuleExtension.configurePackaging() {
    packaging {
        resources {
            excludes += "/META-INF/{AL2.0,LGPL2.1}"
        }
    }
}

private fun BaseAppModuleExtension.configureDefaultBuildTypes() {
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
}

private fun BaseAppModuleExtension.configureJava() {
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_21
        targetCompatibility = JavaVersion.VERSION_21
    }
}
