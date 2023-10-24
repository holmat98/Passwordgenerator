import com.android.build.gradle.internal.dsl.BaseAppModuleExtension
import org.gradle.api.Action
import org.gradle.api.JavaVersion
import org.gradle.api.plugins.ExtensionAware
import org.jetbrains.kotlin.gradle.dsl.KotlinJvmOptions

fun BaseAppModuleExtension.configure(
    namespace: String = "com.mateuszholik.passwordgenerator",
    versionCode: Int,
    versionName: String,
) {
    this.namespace = namespace
    compileSdk = DefaultConfig.COMPILE_SDK

    buildFeatures {
        buildConfig = true
    }

    configureDefaultConfig(
        versionCode = versionCode,
        versionName = versionName
    )
    configureBuildFeatures()
    configureDefaultBuildTypes()
    configureJava()
}

private fun BaseAppModuleExtension.configureDefaultConfig(versionCode: Int, versionName: String) {
    defaultConfig {
        applicationId = DefaultConfig.APPLICATION_ID
        minSdk = DefaultConfig.MIN_SDK
        targetSdk = DefaultConfig.TARGET_SDK
        this.versionCode = versionCode
        this.versionName = versionName

        testInstrumentationRunner = DefaultConfig.TEST_INSTRUMENTATION_RUNNER
    }
}

private fun BaseAppModuleExtension.configureBuildFeatures() {
    buildFeatures {
        viewBinding = true
        dataBinding = true
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
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
    }
    kotlinOptions {
        jvmTarget = "17"
    }
}

private fun BaseAppModuleExtension.kotlinOptions(configure: Action<KotlinJvmOptions>) {
    (this as ExtensionAware).extensions.configure("kotlinOptions", configure)
}
