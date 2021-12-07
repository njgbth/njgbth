package com.example.njgbth2

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.njgbth2.databinding.IngredRecyclerBinding
import com.google.firebase.database.core.view.View

class RecyclerIngrediAdapter(private val dataSet : ArrayList<IngredientData>) :RecyclerView.Adapter<RecyclerIngrediAdapter.ViewHolder>() {


    class ViewHolder(private val binding: IngredRecyclerBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(data: IngredientData) {
            binding.ingredName.text = data.name
            Glide.with(binding.ingredImg.context).load(R.drawable.basic).into(binding.ingredImg)
        }
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): RecyclerIngrediAdapter.ViewHolder {
        val binding = IngredRecyclerBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: RecyclerIngrediAdapter.ViewHolder, position: Int) {
     holder.bind(dataSet[position])
    }
    override fun getItemCount(): Int {
        return dataSet.size
    }


}