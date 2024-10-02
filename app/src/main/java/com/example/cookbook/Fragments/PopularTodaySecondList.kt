package com.example.cookbook.Fragments

import android.os.Bundle
import org.jsoup.Jsoup
import com.bumptech.glide.Glide
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.setFragmentResultListener
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.cookbook.ApiService
import com.example.cookbook.Listener
import com.example.cookbook.MovingButton
import com.example.cookbook.R
import com.example.cookbook.RetrofitClient
import com.example.cookbook.adapters.CookingReceptAdapter
import com.example.cookbook.adapters.FullListAdapter
import com.example.cookbook.databinding.FillerIngridientsBinding
import com.example.cookbook.databinding.FragmentPopularTodaySecondScreenBinding
import com.example.cookbook.models.FullListItems
import com.example.cookbook.models.InformationInsideView
import com.example.cookbook.models.IngridientsInside
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

// The page that opens after clicking on a RecyclerView item (List of popular dishes today)
class PopularTodaySecondList : Fragment() {

    private val apiKey = "6f9b6671250249e793df7f41e9d98194"
    private var isDataLoaded = false
    lateinit var binding: FragmentPopularTodaySecondScreenBinding
    lateinit var binding2: FillerIngridientsBinding
    private val adapter = CookingReceptAdapter()



    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentPopularTodaySecondScreenBinding.inflate(inflater, container, false)
        return binding.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Initially show the progress bar, hide the content
        binding.progressBar.visibility = View.VISIBLE
        binding.secScreenLayout.visibility = View.GONE

        val recyclerView: RecyclerView = binding.rvSecSreen // Убедитесь, что идентификатор правильный
        recyclerView.adapter = adapter
        recyclerView.layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)

        // Listening for results from the previous fragment
        parentFragmentManager.setFragmentResultListener("request_key", this) { _, bundle ->
            val id = bundle.getString("id_key")
            id?.let {
                Log.d("Fragment", "Recipe ID received: $id")
                fetchRecipeInformation(it.toInt())
            }
        }
        MovingButton.setupButtonNavigation(
            view,
            findNavController(),
            R.id.back_but,
            R.id.second_fragment
        )

    }

    fun fetchRecipeInformation(recipeId: Int) {
        Log.d("API_CALL", "Requesting recipe information")
        Log.d("API_CALL", "Sending request for ID: $recipeId") // Log before the request

        val apiService = RetrofitClient.instance
        CoroutineScope(Dispatchers.Main).launch {
            try {
                val response = withContext(Dispatchers.IO) {
                    apiService.getFoodInformation(recipeId, apiKey)
                }

                Log.d("API_CALL", "API response received")
                binding.progressBar.visibility = View.GONE // Hide the progress bar
                binding.secScreenLayout.visibility = View.VISIBLE

                //Cleaning fields from HTML's tags
                val cleanTitle = Jsoup.parse(response.title).text()
                val cleanSummary = Jsoup.parse(response.summary).text()

                binding.title.text = cleanTitle
                binding.cookingTime.text =
                    getString(R.string.cooking_time_f_p_t_s_s, response.readyInMinutes)
                binding.servering.text =
                    getString(R.string.servering_f_p_t_s_s, response.servings)
                binding.summary.text = cleanSummary

                // Loading the image using Glide
                Glide.with(this@PopularTodaySecondList)
                    .load(response.image)
                    .into(binding.productImage)

                // Теперь передаем список ингредиентов в адаптер
                // Извлекаем список ингредиентов из ответа
                val ingredientsList: List<IngridientsInside> = response.extendedIngredients.map { ingredient ->
                    IngridientsInside(
                        originalName = ingredient.originalName,
                        amount = ingredient.measures.metric.amount, // Получаем amount из measures.metric
                        unit = ingredient.measures.metric.unitShort, // Получаем unitShort из measures.metric
                        measures = ingredient.measures // Передаем measures
                    )
                }

                // Передаем данные в адаптер
                adapter.setRecept(ingredientsList)



            } catch (e: Exception) {
                Log.e("API_CALL", "Request error: ${e.message}")
                binding.progressBar.visibility = View.GONE
                showError("Error loading data.")
            }
        }
    }


    fun showError(message: String) {
        binding.progressBar.visibility = View.GONE
        binding.secScreenLayout.visibility = View.GONE
        Toast.makeText(context, message, Toast.LENGTH_SHORT).show()
    }


}




