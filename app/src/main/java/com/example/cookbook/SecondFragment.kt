package com.example.cookbook

//import android.app.Fragment
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.android.volley.Request
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.example.cookbook.adapters.FullListAdapter
import com.example.cookbook.adapters.TodayListAdapter
import com.example.cookbook.databinding.FragmentSecondBinding
import com.example.cookbook.models.FullListItems
import com.example.cookbook.models.TodayItems
import org.json.JSONObject

const val API_KEY = "6f9b6671250249e793df7f41e9d98194"

class SecondFragment : Fragment(), Listener {
    private lateinit var binding: FragmentSecondBinding
    private val todayList = ArrayList<TodayItems>()
    private val adapterR = TodayListAdapter(this)
    private val adapterF = FullListAdapter(this)

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentSecondBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        dataFullRequest()
        dataRandomRequest()
        moreButton()

    }

    //Тут мы вызываем и инициализируем наш viewbinding а так же adapter и прочее
    private fun init(recipes: List<TodayItems>) {
        Log.d("MyLog", "Init called with recipes: $recipes")
        val recyclerView: RecyclerView? = view?.findViewById(R.id.rv_pop_today)
        recyclerView?.adapter = adapterR
        recyclerView?.layoutManager =
            LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
        adapterR.setRecipes(recipes)
    }

    private fun init2(recipes: List<FullListItems>) {
        Log.d("MyLog", "Init called with recipes: $recipes")
        val recyclerView: RecyclerView? = view?.findViewById(R.id.rv_all_list)
        recyclerView?.adapter = adapterF
        recyclerView?.layoutManager =
            LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
        adapterF.setRecipes(recipes)
    }

    //получаем JSonObject из Api
    private fun dataFullRequest() {//Все блюда, вертикальный список
        val url = "https://api.spoonacular.com/recipes/complexSearch?" +
                "number=7&" +
                "apiKey=${API_KEY}"
        val queue = Volley.newRequestQueue(context)
        val request = StringRequest(
            Request.Method.GET,
            url,
            { result ->
                parseFoodDataFull(result)
                Log.d("MyLog", "Request: $result")
            },
            { error ->
                Log.d("MyLog", "Error: $error")
            }
        )
        queue.add(request)

    }

    private fun parseFoodDataFull(result: String?) {
        result?.let { // проверяем, что result не null
            val mainObject = JSONObject(result)
            val fullList = parseFull(mainObject, result)
            init2(fullList)
        }
    }

    private fun parseFull(mainObject: JSONObject, result: String): List<FullListItems> {
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

    //получаем JSonObject из Api
    private fun dataRandomRequest() { //Популярное сегодня
        val url = "https://api.spoonacular.com/recipes/random?number=3&" +
                "apiKey=${API_KEY}"
        val queue = Volley.newRequestQueue(context)
        val request = StringRequest(
            Request.Method.GET,
            url,
            { result ->
                parseFoodDataRandom(result)
                Log.d("MyLog2", "Request: $result")
            },
            { error ->
                Log.d("MyLog2", "Error: $error")
            }
        )
        queue.add(request)
    }

    //Сделано для удобного переиспользования кода
    private fun parseFoodDataRandom(result: String) {
        val mainObject = JSONObject(result)
        val randomList = parseRandom(mainObject, result)
        init(randomList)
//        addRecipesToList(randomList) ???????
    }

    //Обрабатываем JSonObject который получили из dataRandomRequest(), вытаскиваем из него название и картинку
    private fun parseRandom(mainObject: JSONObject, result: String): List<TodayItems> {
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

    override fun onClick(itemId: Int) {
        Log.d("ItemClick", "Item clicked with id: $itemId")
    }

    private fun moreButton() {
        val controler = findNavController()
        val b1 = view?.findViewById<Button>(R.id.but_more)
        b1?.setOnClickListener{
            controler.navigate(R.id.allListFragment)
        }
    }



}