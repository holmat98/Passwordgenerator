object DefaultConfig {
    const val COMPILE_SDK = 33
    const val APPLICATION_ID = "com.mateuszholik.passwordgenerator"
    const val MIN_SDK = 30
    const val TARGET_SDK = 33
    const val VERSION_CODE = 3
    const val VERSION_NAME = "3.0.0"
    const val TEST_INSTRUMENTATION_RUNNER = "androidx.test.runner.AndroidJUnitRunner"
}

object Proguard {
    const val FILE = "proguard-android-optimize.txt"
    const val RULES = "proguard-rules.pro"
}

object Gradle {
    private const val version = "7.3.1"

    const val DEPENDENCY = "com.android.tools.build:gradle:$version"
}

object Jetbrains {

    object KotlinGradlePlugin {
        private const val version = "1.7.20"

        const val DEPENDENCY = "org.jetbrains.kotlin:kotlin-gradle-plugin:$version"
    }


}

object Androidx {

    object Core {
        private const val version = "1.6.0"
        private const val versionTesting = "2.1.0"

        const val DEPENDENCY = "androidx.core:core-ktx:$version"

        object Testing {
            const val DEPENDENCY = "androidx.arch.core:core-testing:$versionTesting"
        }
    }

    object AppCompat {
        private const val version = "1.4.1"

        const val DEPENDENCY = "androidx.appcompat:appcompat:$version"
    }

    object Navigation {
        private const val version = "2.4.1"

        const val FRAGMENT_NAVIGATION_DEPENDENCY =
            "androidx.navigation:navigation-fragment-ktx:$version"
        const val UI_KTX_NAVIGATION_DEPENDENCY = "androidx.navigation:navigation-ui-ktx:$version"
        const val SAFE_ARGS_DEPENDENCY =
            "androidx.navigation:navigation-safe-args-gradle-plugin:$version"
    }

    object ConstraintLayout {
        private const val version = "2.1.3"

        const val DEPENDENCY = "androidx.constraintlayout:constraintlayout:$version"
    }

    object Room {
        private const val version = "2.4.1"

        const val RUNTIME_DEPENDENCY = "androidx.room:room-runtime:$version"
        const val COMPILER_DEPENDENCY = "androidx.room:room-compiler:$version"
        const val ROOM_KTX_DEPENDENCY = "androidx.room:room-ktx:$version"
        const val TEST_DEPENDENCY = "androidx.room:room-testing:$version"
        const val RX_JAVA_DEPENDENCY = "androidx.room:room-rxjava3:$version"
    }

    object Lifecycle {

        object ViewModel {
            private const val version = "2.4.1"

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

    object ViewPager {
        private const val version = "1.0.0"

        const val DEPENDENCY = "androidx.viewpager2:viewpager2:$version"
    }

    object WorkManager {
        private const val version = "2.7.1"

        const val DEPENDENCY = "androidx.work:work-runtime-ktx:$version"

        object Rx {
            const val DEPENDENCY = "androidx.work:work-rxjava3:$version"
        }
    }

    object Biometric {
        private const val version = "1.0.0-beta01"

        const val DEPENDENCY = "androidx.biometric:biometric:$version"
    }
}

object Google {

    object MaterialDesign {
        private const val version = "1.5.0"

        const val DEPENDENCY = "com.google.android.material:material:$version"
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

    object Espresso {
        private const val version = "3.4.0"

        const val DEPENDENCY = "androidx.test.espresso:espresso-core:$version"
    }

    object AssertJ {
        private const val version = "3.21.0"

        const val DEPENDENCY = "org.assertj:assertj-core:$version"
    }

}

object SquareUp {

    object LeakCanary {
        private const val version = "2.8.1"

        const val DEPENDENCY = "com.squareup.leakcanary:leakcanary-android:$version"
    }
}

object RxJava {
    private const val version = "3.1.3"
    private const val versionAndroid = "3.0.0"

    const val DEPENDENCY = "io.reactivex.rxjava3:rxjava:$version"

    object Android {
        const val DEPENDENCY = "io.reactivex.rxjava3:rxandroid:$versionAndroid"
    }
}

object Koin {
    private const val version = "2.0.1"

    const val DEPENDENCY = "io.insert-koin:koin-android:$version"

    object ViewModel {
        const val DEPENDENCY = "io.insert-koin:koin-androidx-viewmodel:$version"
    }

    object Scope {
        const val DEPENDENCY = "io.insert-koin:koin-androidx-scope:$version"
    }
}

object Mockk {
    private const val version = "1.12.1"

    const val DEPENDENCY = "io.mockk:mockk:$version"

    object Android {
        const val DEPENDENCY = "io.mockk:mockk-android:$version"
    }
}

object Lottie {
    private const val version = "5.0.2"

    const val DEPENDENCY = "com.airbnb.android:lottie:$version"
}

object Retrofit {
    private const val version = "2.9.0"

    object Converter {
        const val DEPENDENCY = "com.squareup.retrofit2:converter-gson:$version"
    }
}

object Timber {
    private const val version = "5.0.1"

    const val DEPENDENCY = "com.jakewharton.timber:timber:$version"
}