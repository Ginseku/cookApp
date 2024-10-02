package com.example.cookbook.adapters

import android.annotation.SuppressLint
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.navigation.Navigation
import androidx.navigation.Navigation.findNavController
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.example.cookbook.Listener
import com.example.cookbook.MovingButton
import com.example.cookbook.R
import com.example.cookbook.databinding.FillerPopularBinding
import com.example.cookbook.databinding.FragmentSecondBinding
import com.example.cookbook.models.TodayItems
import com.squareup.picasso.Picasso
//Адаптер который находится сверху(популярное сегодня)
class TodayListAdapter(private val listener: Listener) :
    RecyclerView.Adapter<TodayListAdapter.TodayListHolder>() {
    var todayList = ArrayList<TodayItems>()

    class TodayListHolder(view: View) : RecyclerView.ViewHolder(view) {
        val binding = FillerPopularBinding.bind(view)
        fun bind(item: TodayItems) {
            with(binding) {
                Picasso.get().load(item.imgUrl)
                    .into(imPop)// Picasso облегчает загрузку изображений из различных источников, таких как URL, ресурсы, файлы и другие.
                tvTitle.text = truncateText(item.title, 20)
            }
        }

        //обрезает текст что бы он не привышал заданное кол-во символо, если привышает - (...)
        private fun truncateText(originalText: String, maxLength: Int): String {
            return if (originalText.length > maxLength) {
                originalText.substring(0, maxLength) + "..."
            } else {
                originalText
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TodayListHolder {
        val view =
            LayoutInflater.from(parent.context).inflate(R.layout.filler_popular, parent, false)
        return TodayListHolder(view)
    }

    override fun onBindViewHolder(holder: TodayListHolder, position: Int) {
        val item = todayList[position]
        holder.bind(item)
        holder.itemView.setOnClickListener {
            Log.d("MyLog", "Item clicked: ${item.id}")
            listener.onClick(item.id)
        }

    }

    override fun getItemCount(): Int {
        return todayList.size
    }


    //служит для заполнения списка TodayItems
    @SuppressLint("NotifyDataSetChanged")
    fun setRecipes(recipes: List<TodayItems>) {
        todayList = ArrayList(recipes)
        notifyDataSetChanged()
    }
}