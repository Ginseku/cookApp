package com.example.cookbook.Fragments

//import android.app.Fragment
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.fragment.app.setFragmentResultListener
import androidx.lifecycle.DefaultLifecycleObserver
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.android.volley.Request
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.example.cookbook.Listener
import com.example.cookbook.MovingButton
import com.example.cookbook.MovingToFragment.sendIdToAnotherFragment
import com.example.cookbook.R
import com.example.cookbook.adapters.FullListAdapter
import com.example.cookbook.adapters.TodayListAdapter
import com.example.cookbook.databinding.FragmentSecondBinding
import com.example.cookbook.models.FullListItems
import com.example.cookbook.models.TodayItems
import com.example.cookbook.viewModels.RecipesViewModel
import org.json.JSONObject
import java.util.SortedMap

//Вторая страница
const val API_KEY = "6f9b6671250249e793df7f41e9d98194"

class SecondFragment : Fragment(), Listener {
    private lateinit var binding: FragmentSecondBinding
    private val todayList = ArrayList<TodayItems>()
    private lateinit var viewModel: RecipesViewModel
    private val todayAdapter = TodayListAdapter(this)
    private val fullListAdapter = FullListAdapter(this)

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentSecondBinding.inflate(inflater, container, false)
        return binding.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding = FragmentSecondBinding.bind(view)

        // Инициализация ViewModel
        // Initializing ViewModel
        viewModel = ViewModelProvider(this).get(RecipesViewModel::class.java)

        setupRecyclerViews()

        // Подписываемся на изменения в LiveData для популярных блюд
        // Observing LiveData for today items
        viewModel.todayListLiveData.observe(viewLifecycleOwner, Observer { todayList ->
            Log.d("MyLog", "Today list updated: ${todayList.size} items")
            todayAdapter.setRecipes(todayList)
        })

        // Подписываемся на изменения в LiveData для полного списка блюд
        // Observing LiveData for full list items
        viewModel.fullListLiveData.observe(viewLifecycleOwner, Observer { fullList ->
            Log.d("MyLog", "Full list updated: ${fullList.size} items")
            fullListAdapter.setRecipes(fullList)
        })

        // Загрузка данных (например, при первом создании фрагмента)
        // Loading data (e.g., on first creation of the fragment)
        viewModel.loadTodayData(requireContext())
        viewModel.loadFullListData(requireContext())

        MovingButton.setupButtonNavigation(
            view,
            findNavController(),
            R.id.but_more,
            R.id.allListFragment
        )
    }

    private fun setupRecyclerViews() {
        // Настраиваем RecyclerView для популярных блюд
        // Setting up RecyclerView for today items
        val todayRecyclerView: RecyclerView = binding.rvPopToday
        todayRecyclerView.adapter = todayAdapter
        todayRecyclerView.layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)

        // Настраиваем RecyclerView для полного списка блюд
        // Setting up RecyclerView for full list items
        val fullListRecyclerView: RecyclerView = binding.rvAllList
        fullListRecyclerView.adapter = fullListAdapter
        fullListRecyclerView.layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
    }

    override fun onClick(itemId: Int) {
        Log.d("ItemClick", "Item clicked with id: $itemId")
        sendIdToAnotherFragment(itemId.toString())
        Log.d("ItemClick", "ID sended: $itemId")
        Log.d("Navigation", "Moving to the next fragment")
        if (findNavController().currentDestination?.id == R.id.second_fragment) {
            findNavController().navigate(R.id.action_second_fragment_to_popularTodaySecondList)
        }
        Log.d("Navigation", "Navigation ended")

    }


}