package com.sharkmind.practicasotter.samples.anfibios.ui

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProvider.AndroidViewModelFactory.Companion.APPLICATION_KEY
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.sharkmind.practicasotter.samples.anfibios.AnfibioApp
import com.sharkmind.practicasotter.samples.anfibios.data.AnfibioRepository
import com.sharkmind.practicasotter.samples.anfibios.network.AnfibioData
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import java.io.IOException
import javax.inject.Inject

sealed interface AnfibioUiState {
    data class Success(val cards: List<AnfibioData>): AnfibioUiState
    object Error: AnfibioUiState
    object Loading: AnfibioUiState
}

@HiltViewModel
class AnfibioVM @Inject constructor(
    private val repository: AnfibioRepository
): ViewModel() {
    var anfibioUiState: AnfibioUiState by mutableStateOf(AnfibioUiState.Loading)
        private set

    init {
        setAnfibioInfo()
    }

    private fun setAnfibioInfo() {
        viewModelScope.launch {
            anfibioUiState = try {
                AnfibioUiState.Success(repository.getAnfibios())
            } catch (e: IOException) {
                AnfibioUiState.Error
            }
        }
    }

}