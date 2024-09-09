package com.example.cookbook.models

data class Measures(
    val us: Measurement,
    val metric: Measurement
)

data class Measurement(
    val amount: Double,
    val unitShort: String
)

