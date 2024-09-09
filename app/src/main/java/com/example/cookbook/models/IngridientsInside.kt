package com.example.cookbook.models
//этот дата класс относится к fragment_popular_today_screen
data class IngridientsInside(
    val originalName: String,
    val amount: Double, // Здесь используем amount из metric
    val unit: String, // Здесь используем unitShort из metric
    val measures: Measures
)

