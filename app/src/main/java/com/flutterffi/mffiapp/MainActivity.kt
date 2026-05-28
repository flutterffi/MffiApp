package com.flutterffi.mffiapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.material3.windowsizeclass.ExperimentalMaterial3WindowSizeClassApi
import androidx.compose.material3.windowsizeclass.calculateWindowSizeClass
import com.flutterffi.mffiapp.core.designsystem.adaptive.toMffiAdaptiveInfo
import com.flutterffi.mffiapp.core.designsystem.theme.MffiTheme
import com.flutterffi.mffiapp.navigation.MffiApp

class MainActivity : ComponentActivity() {
    @OptIn(ExperimentalMaterial3WindowSizeClassApi::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            MffiTheme(
                adaptiveInfo = calculateWindowSizeClass(this).toMffiAdaptiveInfo(),
            ) {
                MffiApp()
            }
        }
    }
}
