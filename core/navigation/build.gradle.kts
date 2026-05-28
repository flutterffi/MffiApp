plugins {
    id("mffi.android.library")
    alias(libs.plugins.kotlin.serialization)
}

android {
    namespace = "com.flutterffi.mffiapp.core.navigation"
}

dependencies {
    implementation(project(":core:designsystem"))
    implementation(libs.androidx.navigation.compose)
}
