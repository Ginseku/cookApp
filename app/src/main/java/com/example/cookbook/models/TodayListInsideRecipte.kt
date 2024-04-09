package com.example.cookbook.models

data class TodayListInsideRecipte(
    val productName: String,
    val amount: String,//int вес (200)
    val unit: String, //добавить сюда и в amount граммы из списка "measures"..//мера измерения (грамм)
    val meta: String, //состояние продукта -> (свежее, замороженное... и тд)
    val imgUrlInside: String, //фото конкретного продукта

)
