package com.example.njgbth2

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat.startActivity
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.njgbth2.databinding.IngredRecyclerBinding
import com.example.njgbth2.databinding.RecipeRecyclerBinding

class RecyclerResultAdapter(
    private val dataString : ArrayList<List<String>>,private val dataInt : ArrayList<List<Int>>
    ) :RecyclerView.Adapter<RecyclerResultAdapter.ViewHolder>() {
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): RecyclerResultAdapter.ViewHolder {
        val binding = RecipeRecyclerBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: RecyclerResultAdapter.ViewHolder, position: Int) {
        holder.bind(dataString[position],dataInt[position])
    }

    override fun getItemCount(): Int {
        return dataString.size
    }

    class ViewHolder(private val binding: RecipeRecyclerBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(dataS: List<String>,dataI: List<Int>) {
            binding.nameOFRecipe.text = dataS[0]
            binding.numOfHeart.text = dataI[0].toString()
            Glide.with(binding.imgOfRecipe.context).load(R.drawable.basic).into(binding.imgOfRecipe)
            binding.heart.setOnClickListener() {
                Glide.with(binding.heart.context).load(R.drawable.fullheart).into(binding.heart)
            }
        }
    }
}