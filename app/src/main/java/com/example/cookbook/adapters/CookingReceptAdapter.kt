package com.example.cookbook.adapters
//это страница после нажатия на элемент из списка сверху( популярные рецепты)
import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.cookbook.R
import com.example.cookbook.databinding.FillerIngridientsBinding
import com.example.cookbook.databinding.FragmentPopularTodaySecondScreenBinding
import com.example.cookbook.models.IngridientsInside
import com.example.cookbook.models.InsideRedyToCookRecept
import com.example.cookbook.models.TodayListInsideRecipte

class CookingReceptAdapter: RecyclerView.Adapter<CookingReceptAdapter.CookingReceptHolder>() {
    var list = ArrayList<IngridientsInside>()
    class CookingReceptHolder(view: View): RecyclerView.ViewHolder(view) {
        val binding = FillerIngridientsBinding.bind(view)
        fun bind(item: IngridientsInside) = with(binding){
            ammount.text = item.amount.toString()
            originalName.text = item.originalName
            unitShorts.text = item.unitShort

        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CookingReceptHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.fragment_popular_today_second_screen, parent, false)
        return CookingReceptHolder(view)
    }

    override fun getItemCount(): Int {
        return list.size
    }

    override fun onBindViewHolder(holder: CookingReceptHolder, position: Int) {
        holder.bind(list[position])
    }

    @SuppressLint("NotifyDataSetChanged")
    fun setRecept(ingrInside: List<IngridientsInside>){
        list = ArrayList(ingrInside)
        notifyDataSetChanged()
    }
}