package com.example.cookbook

import com.example.cookbook.models.FullListItems
import com.example.cookbook.models.InformationInsideView
import com.example.cookbook.models.TodayItems
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface ApiService {

    @GET("recipes/{id}/information")
    suspend fun getFoodInformation(@Path("id") id: Int, @Query("apiKey") apikey: String): InformationInsideView

    @GET("recipes/complexSearch")
    fun  getAllRecept(@Query("apiKey") apikey: String, @Query("number") number: Int): Call<RecipeResponse>
}

data class RecipeResponse(
    val results: List<FullListItems>
)
