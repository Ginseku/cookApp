package com.example.cookbook.models
//этот дата класс относится к fragment_popular_today_screen
data class InformationInsideView(
    val id: Int,
    val title: String,
    val readyInMinutes: Int,
    val servings: Int,
    val summary: String,
    val image: String

)
