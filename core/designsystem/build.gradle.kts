plugins {
    id("mffi.android.library")
    id("mffi.android.compose")
}

android {
    namespace = "com.flutterffi.mffiapp.core.designsystem"
}

dependencies {
    implementation(libs.androidx.annotation)
    implementation(platform(libs.androidx.compose.bom))
    implementation(libs.androidx.compose.ui)
    implementation(libs.androidx.compose.material3)
}
