plugins {
    id("mffi.android.library")
    id("mffi.android.compose")
}

android {
    namespace = "com.flutterffi.mffiapp.feature.shared"
}

dependencies {
    implementation(project(":core:designsystem"))
    api(project(":core:domain"))

    implementation(platform(libs.androidx.compose.bom))
    implementation(libs.androidx.compose.ui)
    implementation(libs.androidx.compose.material3)
}
