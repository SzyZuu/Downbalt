package com.szyzu.downbalt.ui

import androidx.lifecycle.ViewModel
import com.szyzu.downbalt.data.Settings
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

class MainViewModel : ViewModel() {
    private val _uiState = MutableStateFlow(Settings())
    val uiState : StateFlow<Settings> = _uiState.asStateFlow()

    fun updateLink(newLink : String){
        _uiState.value = Settings(newLink)
    }
}