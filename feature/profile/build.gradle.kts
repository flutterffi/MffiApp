plugins {
    id("mffi.android.library")
    id("mffi.android.compose")
}

android {
    namespace = "com.flutterffi.mffiapp.feature.profile"
}

dependencies {
    api(project(":core:domain"))
    implementation(project(":core:model"))
    api(project(":feature:shared"))

    implementation(platform(libs.androidx.compose.bom))
    implementation(libs.androidx.compose.ui)
    implementation(libs.androidx.lifecycle.viewmodel.compose)
    implementation(libs.kotlinx.coroutines.android)
}
