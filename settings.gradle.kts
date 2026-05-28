pluginManagement {
    repositories {
        google()
        mavenCentral()
        gradlePluginPortal()
    }
}

dependencyResolutionManagement {
    repositoriesMode.set(RepositoriesMode.FAIL_ON_PROJECT_REPOS)
    repositories {
        google()
        mavenCentral()
    }
}

rootProject.name = "MffiApp"
include(":app")
include(":core:model")
include(":core:domain")
include(":core:data")
include(":core:designsystem")
include(":core:navigation")
include(":feature:shared")
include(":feature:home")
include(":feature:explore")
include(":feature:messages")
include(":feature:profile")
