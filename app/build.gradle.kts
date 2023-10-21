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

android.configure(
    versionCode = androidGitVersion.code(),
    versionName = androidGitVersion.name()
)

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
