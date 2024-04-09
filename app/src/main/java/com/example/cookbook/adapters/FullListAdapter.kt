package com.example.cookbook.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.cookbook.R
import com.example.cookbook.databinding.FillerListBinding
import com.example.cookbook.databinding.FillerPopularBinding
import com.example.cookbook.models.FullListItems
import com.example.cookbook.models.TodayItems

class FullListAdapter: RecyclerView.Adapter<FullListAdapter.FullListHolder>() {

    var fullList = ArrayList<FullListItems>()

    class FullListHolder(view: View): RecyclerView.ViewHolder(view) {
        val binding = FillerListBinding.bind(view)
        fun bind(item: FullListItems) = with(binding){
            imFullList.setImageResource(item.imgUrl)
            tvTitleFullList.text = item.title
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FullListHolder {
        val view =LayoutInflater.from(parent.context).inflate(R.layout.filler_list, parent, false)
        return FullListHolder(view)
    }

    override fun onBindViewHolder(holder: FullListHolder, position: Int) {
        holder.bind(fullList[position])
    }

    override fun getItemCount(): Int {
        return fullList.size
    }
}