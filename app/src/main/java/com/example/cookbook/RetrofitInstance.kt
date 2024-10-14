package com.example.cookbook

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitInstance {
    private const val BASE_URL = "http://10.0.0.6/"

    // Экземпляр Retrofit, который инициализируется при первом вызове
    private val retrofit by lazy {
        Retrofit.Builder()
            .baseUrl(BASE_URL) // Задаем базовый URL для всех запросов
            .addConverterFactory(GsonConverterFactory.create()) // Конвертер для работы с JSON
            .build()
    }

    // Возвращает объект интерфейса API
    val api: ApiService by lazy {
        retrofit.create(ApiService::class.java)
    }
}