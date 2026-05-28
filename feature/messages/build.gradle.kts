plugins {
    id("mffi.android.library")
    id("mffi.android.compose")
}

android {
    namespace = "com.flutterffi.mffiapp.feature.messages"
}

dependencies {
    api(project(":core:domain"))
    implementation(project(":core:navigation"))
    implementation(project(":core:model"))
    api(project(":feature:shared"))

    implementation(platform(libs.androidx.compose.bom))
    implementation(libs.androidx.compose.ui)
    implementation(libs.androidx.lifecycle.runtime.compose)
    implementation(libs.androidx.lifecycle.viewmodel.compose)
    implementation(libs.androidx.navigation.compose)
    implementation(platform(libs.koin.bom))
    implementation(libs.koin.androidx.compose)
    implementation(libs.koin.core)
    implementation(libs.koin.core.viewmodel)
    implementation(libs.kotlinx.coroutines.android)
}
