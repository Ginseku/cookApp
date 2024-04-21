package com.example.cookbook

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.android.volley.Request
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.example.cookbook.adapters.FullListAdapter
import com.example.cookbook.databinding.AllListFragmentBinding
import com.example.cookbook.models.FullListItems
import org.json.JSONObject
import kotlin.math.log

class AllListFragment: Fragment(), Listener{
    private lateinit var binding: AllListFragmentBinding
    private val adapterF = FullListAdapter(this)

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = AllListFragmentBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        backBut()
        dataFullRequest()
    }

    private fun init2(recipes: List<FullListItems>) {
        Log.d("MyLog", "Init called with recipes: $recipes")
        val recyclerView: RecyclerView? = view?.findViewById(R.id.rv_all_list2)
        recyclerView?.adapter = adapterF
        recyclerView?.layoutManager =
            LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
        adapterF.setRecipes(recipes)
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

    private fun backBut() {
        val controler = findNavController()
        val b1 = view?.findViewById<Button>(R.id.but_back_ALF)
        b1?.setOnClickListener{
            controler.navigate(R.id.second_fragment)
        }
    }

    override fun onClick(itemId: Int) {
        Log.d("MyLog", "You pressed the button")
    }
}