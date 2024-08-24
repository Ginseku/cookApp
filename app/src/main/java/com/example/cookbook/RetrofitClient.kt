package com.example.cookbook
import com.example.cookbook.adapters.HtmlCleaner
import com.google.gson.GsonBuilder
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor

object RetrofitClient {
    private const val BASE_URL = "https://api.spoonacular.com/"

    private val loggingInterceptor = HttpLoggingInterceptor().apply {
        setLevel(HttpLoggingInterceptor.Level.BODY)
    }

    private val httpClient = OkHttpClient.Builder()
        .addInterceptor(loggingInterceptor)
        .build()

    // Создание кастомного десериализатора для удаления HTML-тегов
    private val gson = GsonBuilder()
        .registerTypeAdapter(String::class.java, HtmlCleaner())
        .create()

    val instance: ApiService by lazy {
        val retrofit = Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create(gson)) // Используем кастомный Gson
            .client(httpClient)
            .build()

        retrofit.create(ApiService::class.java)
    }
}
