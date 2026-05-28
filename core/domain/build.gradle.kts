plugins {
    id("mffi.android.library")
}

android {
    namespace = "com.flutterffi.mffiapp.core.domain"
}

dependencies {
    api(project(":core:model"))
    implementation(platform(libs.koin.bom))
    implementation(libs.koin.core)
    implementation(libs.kotlinx.coroutines.android)

    testImplementation(libs.kotlin.test.junit)
    testImplementation(libs.kotlinx.coroutines.test)
}
