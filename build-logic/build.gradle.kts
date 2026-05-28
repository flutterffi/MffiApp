plugins {
    `kotlin-dsl`
}

group = "com.flutterffi.mffiapp.buildlogic"

dependencies {
    implementation(libs.android.gradle.plugin)
    implementation(libs.kotlin.gradle.plugin)
}

gradlePlugin {
    plugins {
        register("androidApplication") {
            id = "mffi.android.application"
            implementationClass = "AndroidApplicationConventionPlugin"
        }
        register("androidLibrary") {
            id = "mffi.android.library"
            implementationClass = "AndroidLibraryConventionPlugin"
        }
        register("androidCompose") {
            id = "mffi.android.compose"
            implementationClass = "AndroidComposeConventionPlugin"
        }
    }
}
