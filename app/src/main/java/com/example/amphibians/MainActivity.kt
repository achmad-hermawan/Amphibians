package com.example.amphibians

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
import com.example.amphibians.ui.AmphibiansApp
import com.example.amphibians.ui.theme.AmphibiansTheme

class MainActivity : ComponentActivity() { // Main activity sebagai entry point aplikasi
    override fun onCreate(savedInstanceState: Bundle?) { // Metode onCreate untuk inisialisasi activity
        super.onCreate(savedInstanceState)
        setContent { // Mengatur konten UI dengan Jetpack Compose
            AmphibiansTheme { // Menerapkan tema aplikasi
                Surface(
                    modifier = Modifier.fillMaxSize(), // Mengisi seluruh ukuran layar
                    color = MaterialTheme.colorScheme.background // Mengatur warna latar belakang
                ) {
                    AmphibiansApp() // Memanggil fungsi utama aplikasi
                }
            }
        }
    }
}
