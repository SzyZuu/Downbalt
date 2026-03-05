package com.szyzu.downbalt.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.CreationExtras
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.szyzu.downbalt.data.DataStoreManager
import com.szyzu.downbalt.data.Settings
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class MainViewModel(private val dataStore: DataStoreManager) : ViewModel() {
    private val _uiState = MutableStateFlow(Settings())
    val uiState : StateFlow<Settings> = _uiState.asStateFlow()

    init {
        viewModelScope.launch {
            val link = dataStore.getFromDataStore() ?: "None"
            _uiState.value = Settings(link)
        }
    }

    fun updateLink(newLink : String){
        _uiState.value = Settings(newLink)
        viewModelScope.launch {
            dataStore.saveToDataStore(newLink)
        }
    }

    companion object{
        fun provideFactory(dataStore: DataStoreManager): ViewModelProvider.Factory =
            viewModelFactory {
                initializer {
                    MainViewModel(dataStore)
                }
            }
    }
}