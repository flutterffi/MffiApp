package com.flutterffi.mffiapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import com.flutterffi.mffiapp.core.designsystem.theme.MffiTheme
import com.flutterffi.mffiapp.navigation.MffiApp

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
