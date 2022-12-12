dependencyResolutionManagement {
    repositoriesMode.set(RepositoriesMode.FAIL_ON_PROJECT_REPOS)
    repositories {
        google()
        mavenCentral()
    }
}

rootProject.name = "Password generator"
include(":app")
include(":domain")
include(":data")
include(":uicomponents")
include(":cryptography")
include(":passwordvalidation")
