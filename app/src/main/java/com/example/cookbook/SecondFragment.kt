package com.example.cookbook

//import android.app.Fragment
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.android.volley.Request
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.example.cookbook.adapters.TodayListAdapter
import com.example.cookbook.databinding.FragmentSecondBinding
import com.example.cookbook.databinding.StartFragmentBinding
import com.example.cookbook.models.TodayItems
import org.json.JSONObject
import java.security.MessageDigest

const val API_KEY = "6f9b6671250249e793df7f41e9d98194"

class SecondFragment : Fragment() {
    private lateinit var binding: FragmentSecondBinding
    private val todayList = ArrayList<TodayItems>()
    private val adapter = TodayListAdapter()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentSecondBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
//        dataFullRequest()
        dataRandomRequest()

    }

    private fun init(recipes: List<TodayItems>) {
        Log.d("MyLog", "Init called with recipes: $recipes")
        val recyclerView: RecyclerView? = view?.findViewById(R.id.rv_pop_today)
        recyclerView?.adapter = adapter
        recyclerView?.layoutManager = LinearLayoutManager(requireContext())
        adapter.setRecipes(recipes)
//        adapter.notifyDataSetChanged()
    }

    private fun dataFullRequest() {//Все блюда, вертикальный список
        val url = "https://api.spoonacular.com/recipes/complexSearch?" +
                "number=10&" +
                "apiKey=${API_KEY}"
        val queue = Volley.newRequestQueue(context)
        val request = StringRequest(
            Request.Method.GET,
            url,
            { result ->
//                parseFoodData(result)
                Log.d("MyLog", "Request: $result")
            },
            { error ->
                Log.d("MyLog", "Error: $error")
            }
        )
        queue.add(request)

    }

    private fun dataRandomRequest() { //Популярное сегодня
        val url = "https://api.spoonacular.com/recipes/random?number=2&" +
                "apiKey=${API_KEY}"
        val queue = Volley.newRequestQueue(context)
        val request = StringRequest(
            Request.Method.GET,
            url,
            { result ->
                parseFoodData(result)
                Log.d("MyLog2", "Request: $result")
            },
            { error ->
                Log.d("MyLog2", "Error: $error")
            }
        )
        queue.add(request)
    }
//Закончил на том что пытался добавить второй объект в ресайкл вью, и горизонтальный скролл пока что не очень успешно
    private fun parseFoodData(result: String) {
        val mainObject = JSONObject(result)
        val randomList = listOf(parseRandom(mainObject, result))
        init(randomList)
        addRecipesToList(randomList)
    }

    private fun parseRandom(mainObject: JSONObject, result: String): TodayItems {
//        val list = ArrayList<TodayItems>()
        val recipeArray = JSONObject(result).getJSONArray("recipes")
        val firstRecipe = recipeArray.getJSONObject(0)

        val title = firstRecipe.getString("title")
        val imageUrl = firstRecipe.getString("image")

        Log.d("MyLog", "Parsed title: $title, Parsed image URL: $imageUrl")

        return TodayItems(title, imageUrl)

    }

    private fun addRecipesToList(recipes: List<TodayItems>) {
        // Добавить полученный список к существующему списку
        todayList.addAll(recipes)
        adapter.setRecipes(todayList)
    }


}