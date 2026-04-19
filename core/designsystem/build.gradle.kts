plugins {
    id(Plugins.ANDROID_LIBRARY)
    id(Plugins.ANDROID_KOTLIN)
    id(AndroidX.Compose.COMPILER_PLUGIN)
}

android.configure(
    namespace = "com.mateuszholik.designsystem",
    isUsingCompose = true,
)

dependencies {

    coreKtx()
    compose()
    coil()
    lottie()
}
