plugins {
    id(Plugins.ANDROID_LIBRARY)
    id(Plugins.ANDROID_KOTLIN)
    id(Google.KSP.PLUGIN)
    id(Plugins.HILT)
}

android.configure(
    namespace = "com.mateuszholik.domaincommon",
    isUsingCompose = false,
)

dependencies {
    // Modules
    module(module = Module.CORE_DOMAIN_BASE)

    // Dependencies
    coreKtx()
    hilt()
}
