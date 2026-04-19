plugins {
    id(Plugins.ANDROID_LIBRARY)
    id(Plugins.ANDROID_KOTLIN)
    id(AndroidX.Compose.COMPILER_PLUGIN)
}

android.configure(
    namespace = "com.mateuszholik.mvi",
    isUsingCompose = true
)

dependencies {

    coroutines()
    viewModel()
    compose()
}
