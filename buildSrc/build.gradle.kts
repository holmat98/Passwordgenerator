repositories {
    google()
    mavenCentral()
}

plugins {
    `kotlin-dsl`
}

gradlePlugin {
    plugins {
        create("jacoco-reports") {
            id = "jacoco-reports"
            implementationClass = "JacocoReportsPlugin"
        }
    }
}

dependencies {
    implementation("com.android.tools.build:gradle:8.9.1")
    implementation("org.jetbrains.kotlin:kotlin-gradle-plugin:2.0.20")
}
