import org.gradle.api.artifacts.dsl.DependencyHandler

fun DependencyHandler.room() {
    implementation(Androidx.Room.RUNTIME_DEPENDENCY)
    ksp(Androidx.Room.COMPILER_DEPENDENCY)
    testImplementation(Androidx.Room.TEST_DEPENDENCY)
    implementation(Androidx.Room.RX_JAVA_DEPENDENCY)
}

fun DependencyHandler.unitTesting() {
    testImplementation(Testing.JUnit.DEPENDENCY)
    testCompileOnly(Testing.JUnit.API_DEPENDENCY)
    testRuntimeOnly(Testing.JUnit.ENGINE)
    testImplementation(Testing.JUnit.PARAMS)
    testImplementation(Testing.AssertJ.DEPENDENCY)
    testImplementation(Androidx.Core.Testing.DEPENDENCY)
    testImplementation(Mockk.DEPENDENCY)
}

fun DependencyHandler.androidTesting() {
    androidTestImplementation(Testing.JUnit.DEPENDENCY)
    androidTestCompileOnly(Testing.JUnit.API_DEPENDENCY)
    androidTestRuntimeOnly(Testing.JUnit.ENGINE)
    androidTestImplementation(Testing.JUnit.PARAMS)
    androidTestImplementation(Testing.AssertJ.DEPENDENCY)
    androidTestImplementation(Mockk.Android.DEPENDENCY)
}

fun DependencyHandler.common() {
    implementation(Androidx.Core.DEPENDENCY)
    implementation(Androidx.AppCompat.DEPENDENCY)
}

fun DependencyHandler.rxJava() {
    implementation(RxJava.DEPENDENCY)
    implementation(RxJava.Android.DEPENDENCY)
}

fun DependencyHandler.koin(withWorkerDependency: Boolean = false) {
    implementation(Koin.DEPENDENCY)
    if (withWorkerDependency) {
        implementation(Koin.Worker.DEPENDENCY)
    }
}

fun DependencyHandler.securityCrypto() {
    implementation(Androidx.Security.CRYPTO_DEPENDENCY)
}

fun DependencyHandler.cryptography() {
    securityCrypto()
    implementation(Androidx.Security.APP_AUTHENTICATOR_DEPENDENCY)
    implementation(Androidx.Security.IDENTITY_CREDENTIAL_DEPENDENCY)
    androidTestImplementation(Androidx.Security.APP_AUTHENTICATOR_TESTING_DEPENDENCY)
}

fun DependencyHandler.fragmentNavigation() {
    implementation(Androidx.Navigation.FRAGMENT_NAVIGATION_DEPENDENCY)
    implementation(Androidx.Navigation.UI_KTX_NAVIGATION_DEPENDENCY)
}

fun DependencyHandler.ui() {
    implementation(Androidx.Activity.DEPENDENCY)
    implementation(Google.MaterialDesign.DEPENDENCY)
    implementation(Androidx.ConstraintLayout.DEPENDENCY)
    implementation(Androidx.SplashScreen.DEPENDENCY)
    implementation(Lottie.DEPENDENCY)
}

fun DependencyHandler.viewModel() {
    implementation(Androidx.Lifecycle.ViewModel.DEPENDENCY)
}

fun DependencyHandler.logging() {
    implementation(Timber.DEPENDENCY)
}

fun DependencyHandler.workManager() {
    implementation(Androidx.WorkManager.DEPENDENCY)
    implementation(Androidx.WorkManager.Rx.DEPENDENCY)
}

fun DependencyHandler.biometricManager() {
    implementation(Androidx.Biometric.DEPENDENCY)
}

fun DependencyHandler.leakCanary() {
    debugImplementation(SquareUp.LeakCanary.DEPENDENCY)
}

fun DependencyHandler.ossLicenses() {
    implementation(Google.PlayServices.OssLicences.DEPENDENCY)
}

fun DependencyHandler.crashlytics() {
    implementation(Google.PlayServices.Firebase.Crashlytics.DEPENDENCY)
}

fun DependencyHandler.autofill() {
    implementation(Androidx.Autofill.DEPENDENCY)
}

private fun DependencyHandler.implementation(dependency: String) {
    add("implementation", dependency)
}

private fun DependencyHandler.ksp(dependency: String) {
    add("ksp", dependency)
}

private fun DependencyHandler.testImplementation(dependency: String) {
    add("testImplementation", dependency)
}

private fun DependencyHandler.testRuntimeOnly(dependency: String) {
    add("testRuntimeOnly", dependency)
}

private fun DependencyHandler.testCompileOnly(dependency: String) {
    add("testCompileOnly", dependency)
}

private fun DependencyHandler.androidTestImplementation(dependency: String) {
    add("androidTestImplementation", dependency)
}

private fun DependencyHandler.androidTestRuntimeOnly(dependency: String) {
    add("androidTestRuntimeOnly", dependency)
}

private fun DependencyHandler.androidTestCompileOnly(dependency: String) {
    add("androidTestCompileOnly", dependency)
}

private fun DependencyHandler.debugImplementation(dependency: String) {
    add("debugImplementation", dependency)
}
