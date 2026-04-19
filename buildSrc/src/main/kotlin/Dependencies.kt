object Plugins {
    const val ANDROID_APPLICATION = "com.android.application"
    const val KOTLIN = "kotlin"
    const val ANDROID_KOTLIN = "org.jetbrains.kotlin.android"
    const val FIREBASE_CRASHLYTICS = "com.google.firebase.crashlytics"
    const val GOOGLE_SERVICES = "com.google.gms.google-services"
    const val ANDROID_LIBRARY = "com.android.library"
    const val KAPT = "kapt"
    const val SAFE_ARGS = "androidx.navigation.safeargs"
    const val OSS_LICENSES = "com.google.android.gms.oss-licenses-plugin"
    const val KOTLIN_PARCELIZE = "kotlin-parcelize"
    const val JACOCO = "jacoco-reports"
    const val HILT = "com.google.dagger.hilt.android"

    const val JAVA_LIBRARY = "java-library"
    const val KOTLIN_JVM = "org.jetbrains.kotlin.jvm"
}

object DefaultConfig {
    const val COMPILE_SDK = 36
    const val MIN_SDK = 30
    const val TARGET_SDK = 36
    const val TEST_INSTRUMENTATION_RUNNER = "androidx.test.runner.AndroidJUnitRunner"
    const val CONSUMER_RULES_FILE = "consumer-rules.pro"
}

object Proguard {
    const val FILE = "proguard-android-optimize.txt"
    const val RULES = "proguard-rules.pro"
}

object AndroidX {

    object Core {
        private const val version = "1.12.0"

        const val DEPENDENCY = "androidx.core:core-ktx:$version"

        object Testing {
            private const val version = "2.2.0"

            const val DEPENDENCY = "androidx.arch.core:core-testing:$version"
        }
    }

    object CoreKtx {
        private const val VERSION = "1.17.0"

        const val DEPENDENCY = "androidx.core:core-ktx:$VERSION"

        object Testing {
            private const val VERSION = "2.2.0"

            const val DEPENDENCY = "androidx.arch.core:core-testing:$VERSION"
        }
    }

    object ActivityCompose {
        private const val VERSION = "1.10.1"

        const val DEPENDENCY = "androidx.activity:activity-compose:$VERSION"
    }

    object Compose {
        const val KOTLIN_COMPILER_PLUGIN_VERSION = "2.2.10"
        const val COMPILER_PLUGIN = "org.jetbrains.kotlin.plugin.compose"
        const val BOM = "androidx.compose:compose-bom:2025.08.01"
        const val UI = "androidx.compose.ui:ui"
        const val MATERIAL = "androidx.compose.material3:material3"
        const val PREVIEW = "androidx.compose.ui:ui-tooling-preview"
        const val UI_TOOLING = "androidx.compose.ui:ui-tooling"
        const val NAVIGATION = "androidx.navigation:navigation-compose:2.8.0-beta01"
        const val TEST_MANIFEST = "androidx.compose.ui:ui-test-manifest"
        const val LIVEDATA = "androidx.compose.runtime:runtime-livedata"
        const val LIFECYCLE = "androidx.lifecycle:lifecycle-runtime-compose:2.8.7"

        const val FOUNDATION = "androidx.compose.foundation:foundation"

        object Hilt {
            private const val VERSION = "1.2.0"

            const val DEPENDENCY = "androidx.hilt:hilt-navigation-compose:$VERSION"
        }

        object Lifecycle {
            private const val VERSION = "2.9.2"

            const val DEPENDENCY = "androidx.lifecycle:lifecycle-runtime-compose:$VERSION"
        }
    }

    object AppCompat {
        private const val version = "1.6.1"

        const val DEPENDENCY = "androidx.appcompat:appcompat:$version"
    }

    object Activity {
        private const val version = "1.7.2"

        const val DEPENDENCY = "androidx.activity:activity-ktx:$version"
    }

    object Navigation {
        private const val version = "2.5.3"

        const val FRAGMENT_NAVIGATION_DEPENDENCY =
            "androidx.navigation:navigation-fragment-ktx:$version"
        const val UI_KTX_NAVIGATION_DEPENDENCY = "androidx.navigation:navigation-ui-ktx:$version"
        const val SAFE_ARGS_DEPENDENCY =
            "androidx.navigation:navigation-safe-args-gradle-plugin:$version"
    }

    object ConstraintLayout {
        private const val version = "2.1.4"

        const val DEPENDENCY = "androidx.constraintlayout:constraintlayout:$version"
    }

    object Room {
        private const val version = "2.5.2"

        const val RUNTIME_DEPENDENCY = "androidx.room:room-runtime:$version"
        const val COMPILER_DEPENDENCY = "androidx.room:room-compiler:$version"
        const val TEST_DEPENDENCY = "androidx.room:room-testing:$version"
        const val RX_JAVA_DEPENDENCY = "androidx.room:room-rxjava3:$version"
    }

    object Lifecycle {
        private const val VERSION = "2.9.2"

        const val DEPENDENCY = "androidx.lifecycle:lifecycle-runtime-ktx:$VERSION"

        object ViewModel {
            const val DEPENDENCY = "androidx.lifecycle:lifecycle-viewmodel-ktx:$VERSION"
        }
    }

    object Security {
        private const val version = "1.0.0"

        const val CRYPTO_DEPENDENCY = "androidx.security:security-crypto:$version"
        const val IDENTITY_CREDENTIAL_DEPENDENCY =
            "androidx.security:security-identity-credential:$version-alpha03"
        const val APP_AUTHENTICATOR_DEPENDENCY =
            "androidx.security:security-app-authenticator:$version-alpha02"
        const val APP_AUTHENTICATOR_TESTING_DEPENDENCY =
            "androidx.security:security-app-authenticator-testing:$version-alpha01"
    }

    object WorkManager {
        private const val version = "2.8.1"

        const val DEPENDENCY = "androidx.work:work-runtime-ktx:$version"

        object Rx {
            const val DEPENDENCY = "androidx.work:work-rxjava3:$version"
        }
    }

    object Biometric {
        private const val version = "1.1.0"

        const val DEPENDENCY = "androidx.biometric:biometric:$version"
    }

    object SplashScreen {
        private const val version = "1.0.0"

        const val DEPENDENCY = "androidx.core:core-splashscreen:$version"
    }

    object Autofill {
        private const val version = "1.3.0-alpha01"

        const val DEPENDENCY = "androidx.autofill:autofill:$version"
    }
}

object Google {

    object KSP {
        const val VERSION = "2.2.10-2.0.2"

        const val PLUGIN = "com.google.devtools.ksp"
    }

    object Hilt {
        const val VERSION = "2.57.1"

        const val PLUGIN = "com.google.dagger.hilt.android"
        const val DEPENDENCY = "com.google.dagger:hilt-android:$VERSION"

        object Compiler {
            const val DEPENDENCY = "com.google.dagger:hilt-android-compiler:$VERSION"
        }
    }

    object MaterialDesign {
        private const val version = "1.9.0"

        const val DEPENDENCY = "com.google.android.material:material:$version"
    }

    object PlayServices {
        private const val version = "4.3.15"

        const val DEPENDENCY = "com.google.gms:google-services:$version"

        object OssLicences {
            private const val version = "17.0.0"

            const val DEPENDENCY = "com.google.android.gms:play-services-oss-licenses:$version"
            const val PLUGIN = "com.google.android.gms:oss-licenses-plugin:0.10.6"
        }

        object Firebase {
            object Crashlytics {
                private const val gradleVersion = "2.9.6"
                private const val version = "18.3.7"

                const val GRADLE = "com.google.firebase:firebase-crashlytics-gradle:$gradleVersion"
                const val DEPENDENCY = "com.google.firebase:firebase-crashlytics:$version"
            }
        }
    }
}

object Jetbrains {

    object Coroutines {
        private const val VERSION = "1.10.2"

        const val DEPENDENCY = "org.jetbrains.kotlinx:kotlinx-coroutines-android:$VERSION"

        object UnitTesting {
            const val DEPENDENCY = "org.jetbrains.kotlinx:kotlinx-coroutines-test:$VERSION"
        }
    }

    object Serialization {
        private const val VERSION = "1.9.0"
        const val PLUGIN_VERSION = "2.2.10"

        const val DEPENDENCY = "org.jetbrains.kotlinx:kotlinx-serialization-json:$VERSION"
        const val PLUGIN = "org.jetbrains.kotlin.plugin.serialization"
    }
}

object Testing {

    object JUnit {
        private const val version = "5.9.1"

        const val DEPENDENCY = "org.junit.jupiter:junit-jupiter:$version"
        const val API_DEPENDENCY = "org.junit.jupiter:junit-jupiter-api:$version"
        const val ENGINE = "org.junit.jupiter:junit-jupiter-engine:$version"
        const val PARAMS = "org.junit.jupiter:junit-jupiter-params:$version"
    }

    object AssertJ {
        private const val version = "3.21.0"

        const val DEPENDENCY = "org.assertj:assertj-core:$version"
    }

}

object SquareUp {

    object LeakCanary {
        private const val version = "2.11"

        const val DEPENDENCY = "com.squareup.leakcanary:leakcanary-android:$version"
    }
}

object RxJava {
    private const val version = "3.1.6"

    const val DEPENDENCY = "io.reactivex.rxjava3:rxjava:$version"

    object Android {
        private const val version = "3.0.2"

        const val DEPENDENCY = "io.reactivex.rxjava3:rxandroid:$version"
    }
}

object Koin {
    private const val version = "3.4.2"

    const val DEPENDENCY = "io.insert-koin:koin-android:$version"

    object Worker {
        const val DEPENDENCY = "io.insert-koin:koin-androidx-workmanager:$version"
    }
}

object Mockk {
    private const val version = "1.13.5"

    const val DEPENDENCY = "io.mockk:mockk:$version"

    object Android {
        const val DEPENDENCY = "io.mockk:mockk-android:$version"
    }
}

object Lottie {
    private const val version = "6.0.1"

    const val DEPENDENCY = "com.airbnb.android:lottie:$version"
}

object Timber {
    private const val version = "5.0.1"

    const val DEPENDENCY = "com.jakewharton.timber:timber:$version"
}

object Coil {
    private const val VERSION = "3.0.0"

    const val DEPENDENCY = "io.coil-kt.coil3:coil-compose:$VERSION"
}

object Airbnb {

    object Lottie {
        private const val VERSION = "6.6.7"

        const val DEPENDENCY = "com.airbnb.android:lottie-compose:$VERSION"
    }
}

object AndroidGitVersion {
    const val VERSION = "0.4.14"
    const val PLUGIN = "com.gladed.androidgitversion"
}
