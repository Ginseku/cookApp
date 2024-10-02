package com.example.cookbook.Fragments

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.lifecycle.lifecycleScope
import androidx.navigation.findNavController
import androidx.navigation.fragment.findNavController
import androidx.viewpager2.widget.ViewPager2
import com.android.volley.Request
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.example.cookbook.ApiService
import com.example.cookbook.MovingButton
import com.example.cookbook.Listener
import com.example.cookbook.MovingToFragment.sendIdToAnotherFragment
import com.example.cookbook.R
import com.example.cookbook.RecipeResponse
import com.example.cookbook.RetrofitClient
import com.example.cookbook.adapters.FullListAdapter
import com.example.cookbook.adapters.VpAdapterForFullList
import com.example.cookbook.databinding.AllListFragmentBinding
import com.example.cookbook.models.FullListItems
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import org.json.JSONObject
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory


//Страница которая идет по нажатию на кнопку"MORE" в полном списке
const val apikey = "6f9b6671250249e793df7f41e9d98194"
const val recipeNumber = 20 // Указываем количество рецептов

class AllListFragment : Fragment(), Listener {
    private lateinit var binding: AllListFragmentBinding
    private lateinit var viewPager: ViewPager2
    private lateinit var tabLayout: TabLayout
    private lateinit var viewPagerAdapter: VpAdapterForFullList


    private val retrofit = Retrofit.Builder()
        .baseUrl("https://api.spoonacular.com/")
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    private val apiService: ApiService = retrofit.create(ApiService::class.java)

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
        MovingButton.setupButtonNavigation(
            view,
            findNavController(),
            R.id.but_back_ALF,
            R.id.second_fragment
        )
    }

    private fun init(pages: List<List<FullListItems>>) {
        Log.d("MyLog", "Init called with pages: $pages")
        viewPager.adapter = VpAdapterForFullList(activity as FragmentActivity, pages, this)


        TabLayoutMediator(tabLayout, viewPager) { tab, position ->
            tab.text = (position + 1).toString()
        }.attach()
    }

    private fun dataFullRequest() {
        val call = apiService.getAllRecept(API_KEY, 100)
        call.enqueue(object : Callback<RecipeResponse> {
            override fun onResponse(
                call: Call<RecipeResponse>,
                response: Response<RecipeResponse>
            ) {
                if (response.isSuccessful) {
                    response.body()?.let { recipeResponse ->
                        val pages = recipeResponse.results.chunked(5)
                        init(pages)
                    }
                } else {
                    Log.d("MyLog", "Error: ${response.message()}")
                }
            }

            override fun onFailure(call: Call<RecipeResponse>, t: Throwable) {
                Log.d("MyLog", "Error: $t")
            }
        })
    }

    override fun onClick(itemId: Int) {
        Log.d("ItemClick", "Item clicked with id: $itemId")
        // Здесь реализуем логику навигации при клике
        val bundle = Bundle().apply {
            putString("id_key", itemId.toString())
        }
        parentFragmentManager.setFragmentResult("request_key", bundle)
        findNavController().navigate(R.id.action_allListFragment_to_popularTodaySecondList)
    }
}

