package com.example.njgbth

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ListAdapter
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.njgbth.databinding.IngredRecyclerBinding
import com.example.njgbth.databinding.ItemRecyclerBinding

class RecyclerSelectAdapter(private val dataSet : ArrayList<IngredientData>): RecyclerView.Adapter<RecyclerSelectAdapter.ViewHolder>()  {



    class ViewHolder(private val binding: IngredRecyclerBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(data: IngredientData ) {
            binding.ingredName.text = data.name
            Glide.with(binding.ingredImg.context).load(R.drawable.basic).into(binding.ingredImg)

        }
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): RecyclerSelectAdapter.ViewHolder {
   /*   val view = LayoutInflater.from(parent.context).inflate(R.layout.item_recycler,parent, false)
        return ViewHolder(view)*/
        val binding = IngredRecyclerBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: RecyclerSelectAdapter.ViewHolder, position: Int) {
      holder.bind(dataSet[position])
    }

    override fun getItemCount(): Int {
        return dataSet.size
    }
   /* class ViewHolder(view: View): RecyclerView.ViewHolder(view) {
        private val itemName: TextView = view.name

        fun bind(data:List<String>) {
            itemName.text = data[0]
        }
    }*/

}