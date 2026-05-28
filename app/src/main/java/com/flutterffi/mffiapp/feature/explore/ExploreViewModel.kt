package com.flutterffi.mffiapp.feature.explore

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel

class ExploreViewModel : ViewModel() {
    var uiState by mutableStateOf(ExploreUiState())
        private set
}
