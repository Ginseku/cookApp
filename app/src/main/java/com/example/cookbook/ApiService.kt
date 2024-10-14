package com.example.cookbook

import com.example.cookbook.models.FullListItems
import com.example.cookbook.models.InformationInsideView
//import com.example.cookbook.models.LoginCredentials
import com.example.cookbook.models.TodayItems
//import com.example.cookbook.models.UserData
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path
import retrofit2.http.Query

interface ApiService {

    @GET("recipes/{id}/information")
    suspend fun getFoodInformation(@Path("id") id: Int, @Query("apiKey") apikey: String): InformationInsideView

    @GET("recipes/complexSearch")
    fun  getAllRecept(@Query("apiKey") apikey: String, @Query("number") number: Int): Call<RecipeResponse>


    @POST("login.php")
    fun loginUsers(@Body userData: LoginCredentials
    ) : Call<ResponseBody>


    @POST("register.php")
    fun registerUsers(@Body userData: UserData
    ) : Call<ResponseBody>
}


data class RecipeResponse(
    val results: List<FullListItems>
)
data class LoginCredentials(
    val emailOrNickname: String,
    val password: String
)
data class UserData(
    val email: String,
    val nickname: String,
    val password: String
)
