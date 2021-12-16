package com.example.njgbth

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.constraintlayout.widget.ConstraintSet.VISIBLE
import androidx.core.content.ContextCompat
import androidx.core.content.ContextCompat.startActivity
import androidx.core.view.isVisible
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.njgbth.databinding.IngredRecyclerBinding
import com.example.njgbth.databinding.RecipeRecyclerBinding
import com.google.firebase.firestore.core.View
import java.lang.System.load

class RecyclerResultAdapter(private val dataString : ArrayList<List<String>>,private val dataInt : ArrayList<List<Int>>)
    :RecyclerView.Adapter<RecyclerResultAdapter.ViewHolder>() {
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

            binding.emptyHeart.setOnClickListener() {
                if (binding.emptyHeart.isVisible) {
                    binding.emptyHeart.isVisible = false
                    binding.fullHeart.isVisible = true

                }
            }
            binding.fullHeart.setOnClickListener() {
                if (binding.fullHeart.isVisible) {
                    binding.fullHeart.isVisible = false
                    binding.emptyHeart.isVisible = true

                }
            }
            binding.nameOFRecipe.setOnClickListener() {
                val intent = Intent(binding.nameOFRecipe?.context, WebActivity::class.java)//웹Activity intent 생성
                ContextCompat.startActivity(binding.nameOFRecipe.context, intent, null)//레시피 이름 클릭시, intent로 변환
            }
        }
    }
}