object Plugins {
    const val ANDROID_APPLICATION = "com.android.application"
    const val KOTLIN = "kotlin"
    const val KOTLIN_ANDROID = "org.jetbrains.kotlin.android"
    const val FIREBASE_CRASHLYTICS = "com.google.firebase.crashlytics"
    const val GOOGLE_SERVICES = "com.google.gms.google-services"
    const val ANDROID_LIBRARY = "com.android.library"
    const val KAPT = "kapt"
    const val SAFE_ARGS = "androidx.navigation.safeargs"
    const val OSS_LICENSES = "com.google.android.gms.oss-licenses-plugin"
    const val KOTLIN_PARCELIZE = "kotlin-parcelize"
}

object DefaultConfig {
    const val COMPILE_SDK = 33
    const val APPLICATION_ID = "com.mateuszholik.passwordgenerator"
    const val MIN_SDK = 30
    const val TARGET_SDK = 33
    const val VERSION_CODE = 1
    const val VERSION_NAME = "1.0.0"
    const val TEST_INSTRUMENTATION_RUNNER = "androidx.test.runner.AndroidJUnitRunner"
}

object GradlePlugins {
    const val kotlinVersion = "1.8.22"
    private const val gradlePluginVersion = "7.4.0"

    const val OSS_LICENSES = "com.google.android.gms:oss-licenses-plugin:0.10.6"
    const val ANDROID_GRADLE_PLUGIN = "com.android.tools.build:gradle:$gradlePluginVersion"
    const val KOTLIN_GRADLE_PLUGIN = "org.jetbrains.kotlin:kotlin-gradle-plugin:$kotlinVersion"
    const val KOTLIN_JVM = "org.jetbrains.kotlin.jvm"
}
object Proguard {
    const val FILE = "proguard-android-optimize.txt"
    const val RULES = "proguard-rules.pro"
}

object Androidx {

    object Core {
        private const val version = "1.10.1"

        const val DEPENDENCY = "androidx.core:core-ktx:$version"

        object Testing {
            private const val version = "2.2.0"

            const val DEPENDENCY = "androidx.arch.core:core-testing:$version"
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
        private const val version = "2.5.1"

        const val RUNTIME_DEPENDENCY = "androidx.room:room-runtime:$version"
        const val COMPILER_DEPENDENCY = "androidx.room:room-compiler:$version"
        const val ROOM_KTX_DEPENDENCY = "androidx.room:room-ktx:$version"
        const val TEST_DEPENDENCY = "androidx.room:room-testing:$version"
        const val RX_JAVA_DEPENDENCY = "androidx.room:room-rxjava3:$version"
    }

    object Lifecycle {

        object ViewModel {
            private const val version = "2.6.1"

            const val DEPENDENCY = "androidx.lifecycle:lifecycle-viewmodel-ktx:$version"
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

object AndroidGitVersion {
    const val VERSION = "0.4.14"
    const val PLUGIN = "com.gladed.androidgitversion"
}
