package com.example.cookbook.viewModels
import android.content.Context
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.cookbook.models.FullListItems
import com.example.cookbook.models.TodayItems
import com.android.volley.Request
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.example.cookbook.Fragments.API_KEY
import org.json.JSONObject

class RecipesViewModel : ViewModel() {

    // LiveData для популярных блюд и полного списка
    // LiveData for today items and full list
    val todayListLiveData: MutableLiveData<List<TodayItems>> = MutableLiveData()
    val fullListLiveData: MutableLiveData<List<FullListItems>> = MutableLiveData()

    // Метод для загрузки данных популярных блюд
    // Method to load today's items data
    fun loadTodayData(context: Context) {
        val url = "https://api.spoonacular.com/recipes/random?number=3&apiKey=$API_KEY"
        val queue = Volley.newRequestQueue(context)
        val request = StringRequest(Request.Method.GET, url,
            { result ->
                val mainObject = JSONObject(result)
                val randomList = parseTodayItems(mainObject)
                todayListLiveData.value = randomList // Устанавливаем данные в LiveData
                // Setting data to LiveData
            },
            { error ->
                // Обработка ошибок
                // Error handling
            }
        )
        queue.add(request)
    }

    // Метод для загрузки полного списка блюд
    // Method to load full list data
    fun loadFullListData(context: Context) {
        val url = "https://api.spoonacular.com/recipes/complexSearch?number=7&apiKey=$API_KEY"
        val queue = Volley.newRequestQueue(context)
        val request = StringRequest(Request.Method.GET, url,
            { result ->
                val mainObject = JSONObject(result)
                val fullList = parseFullListItems(mainObject)
                fullListLiveData.value = fullList // Устанавливаем данные в LiveData
                // Setting data to LiveData
            },
            { error ->
                // Обработка ошибок
                // Error handling
            }
        )
        queue.add(request)
    }

    // Парсинг данных для популярных блюд
    // Parsing data for today items
    private fun parseTodayItems(mainObject: JSONObject): List<TodayItems> {
        val list = ArrayList<TodayItems>()
        val recipeArray = mainObject.getJSONArray("recipes")
        for (i in 0 until recipeArray.length()) {
            val ar = recipeArray[i] as JSONObject
            val recipes = TodayItems(
                ar.getInt("id"),
                ar.getString("title"),
                ar.getString("image")
            )
            list.add(recipes)
        }
        return list
    }

    // Парсинг данных для полного списка блюд
    // Parsing data for full list items
    private fun parseFullListItems(mainObject: JSONObject): List<FullListItems> {
        val list = ArrayList<FullListItems>()
        val recipeArray = mainObject.getJSONArray("results")
        for (i in 0 until recipeArray.length()) {
            val ar = recipeArray[i] as JSONObject
            val recipes = FullListItems(
                ar.getInt("id"),
                ar.getString("title"),
                ar.getString("image")
            )
            list.add(recipes)
        }
        return list
    }
}
