package com.example.cookbook.Fragments

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.navigation.fragment.findNavController
import androidx.viewpager2.widget.ViewPager2
import com.android.volley.Request
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.example.cookbook.BackButton
import com.example.cookbook.Fragments.API_KEY
import com.example.cookbook.Listener
import com.example.cookbook.R
import com.example.cookbook.adapters.VpAdapter
import com.example.cookbook.databinding.AllListFragmentBinding
import com.example.cookbook.models.FullListItems
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator
import org.json.JSONObject
//Страница которая идет по нажатию на кнопку"MORE" в полном списке
class AllListFragment : Fragment(), Listener {
    private lateinit var binding: AllListFragmentBinding
    private lateinit var viewPager: ViewPager2
    private lateinit var tabLayout: TabLayout

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = AllListFragmentBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewPager = binding.viewPager
        tabLayout = binding.tabLayout

        dataFullRequest()
        BackButton.setupButtonNavigation(view,findNavController(), R.id.but_back_ALF, R.id.second_fragment )
    }

    private fun init(pages: List<List<FullListItems>>) {
        Log.d("MyLog", "Init called with pages: $pages")
        viewPager.adapter = VpAdapter(activity as FragmentActivity, pages, this)

        TabLayoutMediator(tabLayout, viewPager) { tab, position ->
            tab.text = (position + 1).toString()
        }.attach()
    }

    private fun dataFullRequest() {
        val url = "https://api.spoonacular.com/recipes/complexSearch?" +
                "number=100&" +
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
        result?.let {
            val mainObject = JSONObject(result)
            val fullList = parseFull(mainObject, result)
            val pages = fullList.chunked(5) // сколько будет элементов на странице
            init(pages)
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

    override fun onClick(itemId: Int) {
        Log.d("MyLog", "You pressed the button")
    }
}
