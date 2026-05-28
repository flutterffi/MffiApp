plugins {
    id("mffi.android.library")
    alias(libs.plugins.kotlin.serialization)
    alias(libs.plugins.ksp)
}

android {
    namespace = "com.flutterffi.mffiapp.core.data"
}

dependencies {
    implementation(project(":core:domain"))

    implementation(libs.androidx.room.runtime)
    implementation(libs.androidx.room.ktx)
    implementation(libs.ktor.client.core)
    implementation(libs.kotlinx.coroutines.android)
    implementation(libs.ktor.serialization.kotlinx.json)
    ksp(libs.androidx.room.compiler)
}
