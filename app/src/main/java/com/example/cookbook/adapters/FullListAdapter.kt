package com.example.cookbook.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.cookbook.Listener
import com.example.cookbook.R
import com.example.cookbook.databinding.FillerListBinding
import com.example.cookbook.databinding.FillerPopularBinding
import com.example.cookbook.models.FullListItems
import com.example.cookbook.models.TodayItems
import com.squareup.picasso.Picasso

class FullListAdapter(private val listener: Listener) : RecyclerView.Adapter<FullListAdapter.FullListHolder>() {

    var fullList = ArrayList<FullListItems>()

    class FullListHolder(view: View) : RecyclerView.ViewHolder(view) {
        val binding = FillerListBinding.bind(view)

        fun bind(item: FullListItems){
            with(binding){
                Picasso.get().load(item.imgUrlF).into(imFullList)
                tvTitleFullList.text = truncateText(item.title, 40)
            }
        }
        private fun truncateText(originalText: String, maxLength: Int): String {
            return if (originalText.length > maxLength) {
                originalText.substring(0, maxLength) + "..."
            } else {
                originalText
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FullListHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.filler_list, parent, false)
        return FullListHolder(view)
    }

    override fun onBindViewHolder(holder: FullListHolder, position: Int) {
        val item = fullList[position]
        holder.bind(item)
        holder.itemView.setOnClickListener {
            listener.onClick(item.id)
        }
    }

    override fun getItemCount(): Int {
        return fullList.size
    }
    fun setRecipes(recipes: List<FullListItems>) {
        fullList = ArrayList(recipes)
    }
}