package com.flutterffi.mffiapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import com.flutterffi.mffiapp.core.navigation.MffiApp
import com.flutterffi.mffiapp.core.designsystem.theme.MffiTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            MffiTheme {
                MffiApp()
            }
        }
    }
}
