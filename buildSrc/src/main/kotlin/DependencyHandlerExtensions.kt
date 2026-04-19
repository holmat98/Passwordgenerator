import org.gradle.api.artifacts.dsl.DependencyHandler

fun DependencyHandler.room() {
    implementation(AndroidX.Room.RUNTIME_DEPENDENCY)
    ksp(AndroidX.Room.COMPILER_DEPENDENCY)
    testImplementation(AndroidX.Room.TEST_DEPENDENCY)
    implementation(AndroidX.Room.RX_JAVA_DEPENDENCY)
}

fun DependencyHandler.unitTesting() {
    testImplementation(Testing.JUnit.DEPENDENCY)
    testCompileOnly(Testing.JUnit.API_DEPENDENCY)
    testRuntimeOnly(Testing.JUnit.ENGINE)
    testImplementation(Testing.JUnit.PARAMS)
    testImplementation(Testing.AssertJ.DEPENDENCY)
    testImplementation(AndroidX.Core.Testing.DEPENDENCY)
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
    implementation(AndroidX.Core.DEPENDENCY)
    implementation(AndroidX.AppCompat.DEPENDENCY)
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
    implementation(AndroidX.Security.CRYPTO_DEPENDENCY)
}

fun DependencyHandler.cryptography() {
    securityCrypto()
    implementation(AndroidX.Security.APP_AUTHENTICATOR_DEPENDENCY)
    implementation(AndroidX.Security.IDENTITY_CREDENTIAL_DEPENDENCY)
    androidTestImplementation(AndroidX.Security.APP_AUTHENTICATOR_TESTING_DEPENDENCY)
}

fun DependencyHandler.fragmentNavigation() {
    implementation(AndroidX.Navigation.FRAGMENT_NAVIGATION_DEPENDENCY)
    implementation(AndroidX.Navigation.UI_KTX_NAVIGATION_DEPENDENCY)
}

fun DependencyHandler.ui() {
    implementation(AndroidX.Activity.DEPENDENCY)
    implementation(Google.MaterialDesign.DEPENDENCY)
    implementation(AndroidX.ConstraintLayout.DEPENDENCY)
    implementation(AndroidX.SplashScreen.DEPENDENCY)
    implementation(Lottie.DEPENDENCY)
}

fun DependencyHandler.coroutines() {
    api(Jetbrains.Coroutines.DEPENDENCY)
}

fun DependencyHandler.compose() {
    implementation(platform(AndroidX.Compose.BOM))
    implementation(AndroidX.Compose.UI)
    implementation(AndroidX.Compose.MATERIAL)
    implementation(AndroidX.Compose.PREVIEW)
    implementation(AndroidX.Compose.NAVIGATION)
    debugImplementation(AndroidX.Compose.UI_TOOLING)
    debugImplementation(AndroidX.Compose.TEST_MANIFEST)
    implementation(AndroidX.Compose.Hilt.DEPENDENCY)
    implementation(AndroidX.Compose.Lifecycle.DEPENDENCY)
    implementation(AndroidX.Compose.LIVEDATA)
    implementation(AndroidX.Compose.FOUNDATION)
    implementation(AndroidX.Compose.LIFECYCLE)
}

fun DependencyHandler.coil() {
    implementation(Coil.DEPENDENCY)
}

fun DependencyHandler.lottie() {
    implementation(Airbnb.Lottie.DEPENDENCY)
}

fun DependencyHandler.viewModel() {
    api(AndroidX.Lifecycle.ViewModel.DEPENDENCY)
}

fun DependencyHandler.logging() {
    implementation(Timber.DEPENDENCY)
}

fun DependencyHandler.coreKtx() {
    implementation(AndroidX.CoreKtx.DEPENDENCY)
}

fun DependencyHandler.hilt() {
    implementation(Google.Hilt.DEPENDENCY)
    ksp(Google.Hilt.Compiler.DEPENDENCY)
}

fun DependencyHandler.workManager() {
    implementation(AndroidX.WorkManager.DEPENDENCY)
    implementation(AndroidX.WorkManager.Rx.DEPENDENCY)
}

fun DependencyHandler.biometricManager() {
    implementation(AndroidX.Biometric.DEPENDENCY)
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
    implementation(AndroidX.Autofill.DEPENDENCY)
}

fun DependencyHandler.module(module: Module) {
    implementation(project(mapOf("path" to module.value)))
}

private fun DependencyHandler.api(dependency: Any) {
    add("api", dependency)
}

private fun DependencyHandler.implementation(dependency: Any) {
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
