package com.example.cookbook.adapters

import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.example.cookbook.Listener
import com.example.cookbook.Fragments.PageFragment
import com.example.cookbook.models.FullListItems

class VpAdapter (fragmentActivity: FragmentActivity, private val items: List<List<FullListItems>>, private val listener: Listener) : FragmentStateAdapter(fragmentActivity) {
    override fun getItemCount(): Int = items.size

    override fun createFragment(position: Int): Fragment {
        val fragment = PageFragment()
        fragment.arguments = Bundle().apply {
            putParcelableArrayList("items", ArrayList(items[position]))
        }
        return fragment
    }
}