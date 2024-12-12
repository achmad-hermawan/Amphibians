package com.example.amphibians.data

import com.example.amphibians.network.AmphibiansApiService
import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import kotlinx.serialization.json.Json
import okhttp3.MediaType.Companion.toMediaType
import retrofit2.Retrofit

interface AppContainer { // Interface untuk mendefinisikan Dependency Injection container
    val amphibiansRepository: AmphibiansRepository // Deklarasi repository untuk Amphibians
}

class DefaultAppContainer : AppContainer { // Implementasi Dependency Injection container
    private val BASE_URL = "https://android-kotlin-fun-mars-server.appspot.com/" // URL dasar API

    private val retrofit: Retrofit = Retrofit.Builder() // Membuat instance Retrofit
        .addConverterFactory(Json.asConverterFactory("application/json".toMediaType())) // Menambahkan converter untuk JSON
        .baseUrl(BASE_URL) // Mengatur URL dasar
        .build()

    private val retrofitService: AmphibiansApiService by lazy { // Membuat service Retrofit secara lazy
        retrofit.create(AmphibiansApiService::class.java) // Inisialisasi API service
    }

    override val amphibiansRepository: AmphibiansRepository by lazy { // Implementasi repository secara lazy
        DefaultAmphibiansRepository(retrofitService) // Membuat instance repository dengan service
    }
}
