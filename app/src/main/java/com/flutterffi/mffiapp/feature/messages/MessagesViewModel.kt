package com.flutterffi.mffiapp.feature.messages

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel

class MessagesViewModel : ViewModel() {
    var uiState by mutableStateOf(MessagesUiState())
        private set
}
