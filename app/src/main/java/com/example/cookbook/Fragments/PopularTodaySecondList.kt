package com.example.cookbook.Fragments

import android.os.Bundle
import org.jsoup.Jsoup
import com.bumptech.glide.Glide
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.setFragmentResultListener
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.cookbook.MovingButton
import com.example.cookbook.R
import com.example.cookbook.RetrofitClient
import com.example.cookbook.adapters.CookingReceptAdapter
import com.example.cookbook.databinding.FragmentPopularTodaySecondScreenBinding
import com.example.cookbook.models.InformationInsideView
import com.example.cookbook.models.IngridientsInside
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

//Страница которая открывается после нажатия на элемент ресайкл вью(Спсок популярных блюд сегодня)
class PopularTodaySecondList : Fragment() {

    private val apiKey = "6f9b6671250249e793df7f41e9d98194"
    private var isDataLoaded = false

    lateinit var binding: FragmentPopularTodaySecondScreenBinding
    private val adapter = CookingReceptAdapter()
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentPopularTodaySecondScreenBinding.inflate(inflater, container, false)
        return binding.root
    }


    private fun init(data: List<IngridientsInside>) {
        Log.d("MyLog", "Init called with recipes: $data")
        val recyclerView: RecyclerView? = view?.findViewById(R.id.rv_sec_sreen)
        recyclerView?.adapter = adapter
        recyclerView?.layoutManager =
            LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
        adapter.setRecept(data)
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

//        binding.progressBar.visibility = View.VISIBLE // Показываем прогресс бар
//        binding.secScreenLayout.visibility = View.GONE

        parentFragmentManager.setFragmentResultListener("request_key", this) { _, bundle ->
            val id = bundle.getString("id_key")
            id?.let {
                fetchRecipeInformation(it.toInt()) // Загружаем данные
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
        Log.d("API_CALL", "Запрос на получение информации о рецепте")
        val apiService = RetrofitClient.instance
        apiService.getFoodInformation(recipeId, apiKey)
            .enqueue(object : Callback<InformationInsideView> {
                override fun onResponse(
                    call: Call<InformationInsideView>, response: Response<InformationInsideView>
                ) {
                    Log.d("API_CALL", "Ответ от API получен")
                    binding.progressBar.visibility = View.GONE // Скрываем прогресс бар
                    binding.secScreenLayout.visibility = View.VISIBLE

                    if (response.isSuccessful) {
                        Log.d("API_CALL", "Ответ успешный: ${response.body()}")
                        val recipe = response.body()
                        recipe?.let {
                            //Cleaning fields from html's tags
                            val cleanTitle = Jsoup.parse(it.title).text()
                            val cleanSummary = Jsoup.parse(it.summary).text()

                            binding.title.text = cleanTitle
                            binding.cookingTime.text =
                                getString(R.string.cooking_time_f_p_t_s_s, it.readyInMinutes)
                            binding.servering.text =
                                getString(R.string.servering_f_p_t_s_s, it.servings)
                            binding.summary.text = cleanSummary

                            // Загрузка изображения с использованием Glide
                            Glide.with(this@PopularTodaySecondList)
                                .load(it.image)
                                .into(binding.productImage)

                        }
                    }else {
                        Log.d("API_CALL", "Ошибка ответа: ${response.errorBody()?.string()}")
                    }
                }

                override fun onFailure(call: Call<InformationInsideView>, t: Throwable) {
                    Log.e("API_CALL", "Ошибка запроса: ${t.message}")
                    binding.progressBar.visibility = View.GONE                }
            })

    }


}




