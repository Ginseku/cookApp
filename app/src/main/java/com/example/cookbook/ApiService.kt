package com.example.cookbook

import com.example.cookbook.models.InformationInsideView
import com.example.cookbook.models.TodayItems
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface ApiService {

    @GET("recipes/{id}/information")
    fun getFoodInformation(@Path("id") id: Int, @Query("apiKey") apikey: String): Call<InformationInsideView>
}