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
    implementation("com.android.tools.build:gradle:8.12.3")
    implementation("org.jetbrains.kotlin:kotlin-gradle-plugin:2.2.10")
    implementation("com.squareup:javapoet:1.13.0")
}
