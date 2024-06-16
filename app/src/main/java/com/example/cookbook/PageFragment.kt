package com.example.cookbook

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.cookbook.adapters.FullListAdapter
import com.example.cookbook.models.FullListItems

class PageFragment : Fragment() {
    private lateinit var adapterF: FullListAdapter
    private lateinit var listener: Listener
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_full_list_second_screen, container, false)
        val recyclerView = view.findViewById<RecyclerView>(R.id.recyclerView)
        recyclerView.layoutManager = LinearLayoutManager(context)

        listener = activity as Listener

        adapterF = FullListAdapter(listener)
        recyclerView.adapter = adapterF

        val items = arguments?.getParcelableArrayList<FullListItems>("items")
        items?.let {
            adapterF.setRecipes(it)
        }

        return view
    }
}
