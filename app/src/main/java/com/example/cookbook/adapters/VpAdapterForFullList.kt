package com.example.cookbook.adapters

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.bumptech.glide.Glide
import com.example.cookbook.Listener
import com.example.cookbook.Fragments.PageFragment
import com.example.cookbook.R
import com.example.cookbook.models.FullListItems
//Адаптер для списка который появляется после нажатия на кнопку "MORE" и открывается полный список рецептов

// Адаптер для ViewPager2, использующий FragmentStateAdapter
class VpAdapterForFullList(
    fragmentActivity: FragmentActivity,
    private val pages: List<List<FullListItems>>,
    private val listener: Listener  // Добавляем Listener для передачи кликов
) : FragmentStateAdapter(fragmentActivity) {

    override fun createFragment(position: Int): Fragment {
        val fragment = PageFragment()
        fragment.arguments = Bundle().apply {
            putParcelableArrayList("items", ArrayList(pages[position]))
        }
        fragment.listener = listener  // Устанавливаем Listener для фрагмента
        return fragment
    }

    override fun getItemCount(): Int = pages.size
}