import com.android.build.api.dsl.LibraryExtension
import org.gradle.api.JavaVersion

fun LibraryExtension.configure(
    namespace: String,
    isUsingCompose: Boolean = false
) {
    this.namespace = namespace
    compileSdk = DefaultConfig.COMPILE_SDK

    configureDefaultConfig()
    configureDefaultBuildTypes()
    configureJava()
    if (isUsingCompose) {
        configureComposeFeatures()
    }
    configureBuildConfigFeatures()
}

private fun LibraryExtension.configureDefaultConfig() {
    defaultConfig {
        minSdk = DefaultConfig.MIN_SDK

        vectorDrawables {
            useSupportLibrary = true
        }

        testInstrumentationRunner = DefaultConfig.TEST_INSTRUMENTATION_RUNNER
        consumerProguardFiles(DefaultConfig.CONSUMER_RULES_FILE)
    }
}

private fun LibraryExtension.configureComposeFeatures() {
    buildFeatures {
        compose = true
    }
}

private fun LibraryExtension.configureBuildConfigFeatures() {
    buildFeatures {
        buildConfig = true
    }
}

private fun LibraryExtension.configureDefaultBuildTypes() {
    buildTypes {
        release {
            proguardFiles(
                getDefaultProguardFile(Proguard.FILE),
                Proguard.RULES
            )
        }
    }
}

private fun LibraryExtension.configureJava() {
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_21
        targetCompatibility = JavaVersion.VERSION_21
    }
}
