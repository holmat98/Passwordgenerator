import com.android.build.gradle.LibraryExtension
import org.gradle.api.Action
import org.gradle.api.JavaVersion
import org.gradle.api.plugins.ExtensionAware
import org.jetbrains.kotlin.gradle.dsl.KotlinJvmOptions

fun LibraryExtension.configure(namespace: String) {
    this.namespace = namespace
    compileSdk = DefaultConfig.COMPILE_SDK

    buildFeatures {
        buildConfig = true
    }

    configureDefaultConfig()
    configureDefaultBuildTypes()
    configureJava()
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

private fun LibraryExtension.configureDefaultConfig() {
    defaultConfig {
        minSdk = DefaultConfig.MIN_SDK
        testInstrumentationRunner = DefaultConfig.TEST_INSTRUMENTATION_RUNNER
    }
}

private fun LibraryExtension.configureJava() {
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
    }
    kotlinOptions {
        jvmTarget = "17"
    }
}

private fun LibraryExtension.kotlinOptions(configure: Action<KotlinJvmOptions>) {
    (this as ExtensionAware).extensions.configure("kotlinOptions", configure)
}
