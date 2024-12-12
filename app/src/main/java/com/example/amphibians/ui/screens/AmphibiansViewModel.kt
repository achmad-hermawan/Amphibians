package com.example.amphibians.ui.screens

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.example.amphibians.AmphibiansApplication
import com.example.amphibians.data.AmphibiansRepository
import com.example.amphibians.model.Amphibian
import kotlinx.coroutines.launch
import retrofit2.HttpException
import java.io.IOException

// Representasi state UI untuk Home screen
sealed interface AmphibiansUiState {
    data class Success(val amphibians: List<Amphibian>) : AmphibiansUiState // State saat data berhasil di-load
    object Error : AmphibiansUiState // State saat terjadi error
    object Loading : AmphibiansUiState // State saat data sedang dimuat
}

// ViewModel untuk mengelola data dan logika aplikasi
class AmphibiansViewModel(private val amphibiansRepository: AmphibiansRepository) : ViewModel() {

    var amphibiansUiState: AmphibiansUiState by mutableStateOf(AmphibiansUiState.Loading) // State UI
        private set

    init {
        getAmphibians() // Memuat data saat ViewModel dibuat
    }

    fun getAmphibians() { // Fungsi untuk mengambil data dari repository
        viewModelScope.launch {
            amphibiansUiState = AmphibiansUiState.Loading // Set state ke Loading
            amphibiansUiState = try {
                AmphibiansUiState.Success(amphibiansRepository.getAmphibians()) // Jika sukses, set state ke Success
            } catch (e: IOException) {
                AmphibiansUiState.Error // Set state ke Error jika terjadi IOException
            } catch (e: HttpException) {
                AmphibiansUiState.Error // Set state ke Error jika terjadi HttpException
            }
        }
    }

    // Factory untuk membuat instance AmphibiansViewModel dengan dependency
    companion object {
        val Factory: ViewModelProvider.Factory = viewModelFactory {
            initializer {
                val application = (this[ViewModelProvider.AndroidViewModelFactory.APPLICATION_KEY]
                        as AmphibiansApplication) // Mengambil instance aplikasi
                val amphibiansRepository = application.container.amphibiansRepository // Mengambil repository dari container
                AmphibiansViewModel(amphibiansRepository = amphibiansRepository) // Membuat ViewModel dengan repository
            }
        }
    }
}
