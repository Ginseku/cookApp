package com.example.cookbook.Fragments

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.RecyclerView
import androidx.navigation.fragment.findNavController  // Импорт для findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.cookbook.R
import com.example.cookbook.adapters.FullListAdapter
import com.example.cookbook.Listener
import com.example.cookbook.MovingToFragment.sendIdToAnotherFragment
import com.example.cookbook.models.FullListItems
class PageFragment : Fragment(), Listener {

    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter: FullListAdapter
    var listener: Listener? = null
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_full_list_second_screen, container, false)
        recyclerView = view.findViewById(R.id.recyclerView)
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val items = arguments?.getParcelableArrayList<FullListItems>("items")
        items?.let {
            adapter = FullListAdapter(this)
            recyclerView.layoutManager = LinearLayoutManager(requireContext())
            recyclerView.adapter = adapter
            adapter.setRecipes(it)
        }
    }

//    override fun onClick(itemId: Int) {
//        val bundle = Bundle().apply {
//            putString("id_key", itemId.toString())
//        }
//        parentFragmentManager.setFragmentResult("request_key", bundle)
//        findNavController().navigate(R.id.action_allListFragment_to_popularTodaySecondList)
//    }
    override fun onClick(itemId: Int) {
        Log.d("ItemClick", "Item clicked with id: $itemId")
        listener?.onClick(itemId)  // Передаем клик наверх, в AllListFragment
    }
}