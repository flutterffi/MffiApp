plugins {
    id("mffi.android.library")
}

android {
    namespace = "com.flutterffi.mffiapp.core.domain"
}

dependencies {
    api(project(":core:model"))
    implementation(libs.kotlinx.coroutines.android)

    testImplementation(libs.kotlin.test)
    testImplementation(libs.kotlinx.coroutines.test)
}
