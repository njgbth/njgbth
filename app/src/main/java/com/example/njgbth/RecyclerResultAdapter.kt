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
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import java.lang.System.load

class RecyclerResultAdapter(private val dataRecipe : ArrayList<RecipeData>)
    :RecyclerView.Adapter<RecyclerResultAdapter.ViewHolder>() {
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): RecyclerResultAdapter.ViewHolder {
        val binding = RecipeRecyclerBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: RecyclerResultAdapter.ViewHolder, position: Int) {
        holder.bind(dataRecipe[position].name,dataRecipe[position].link,dataRecipe[position].weight,dataRecipe[position].numOFHeart)
    }

    override fun getItemCount(): Int {
        return dataRecipe.size
    }

    class ViewHolder(private val binding: RecipeRecyclerBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(name: String, link: String, weight:Int, numOFHeart : Int) {
            binding.nameOFRecipe.text = name
            binding.numOfHeart.text = numOFHeart.toString()
            Glide.with(binding.imgOfRecipe.context).load(R.drawable.basic).into(binding.imgOfRecipe)

            binding.emptyHeart.setOnClickListener() {
                if (binding.emptyHeart.isVisible) {
                    binding.emptyHeart.isVisible = false
                    binding.fullHeart.isVisible = true

                    //선호도 숫자 반영
                    var presentHeart = binding.numOfHeart.text.toString()
                    var changeHeart = presentHeart.toInt() + 1
                    presentHeart = changeHeart.toString()
                    binding.numOfHeart.text = presentHeart
                    updatetest(binding.nameOFRecipe.text.toString(),changeHeart)
                }
            }
            binding.fullHeart.setOnClickListener() {
                if (binding.fullHeart.isVisible) {
                    binding.fullHeart.isVisible = false
                    binding.emptyHeart.isVisible = true



                   //선호도 숫자 반영
                    var presentHeart = binding.numOfHeart.text.toString()
                    var changeHeart = presentHeart.toInt() - 1
                    presentHeart = changeHeart.toString()
                    binding.numOfHeart.text = presentHeart
                    updatetest(binding.nameOFRecipe.text.toString(),changeHeart)
                }
            }
            binding.nameOFRecipe.setOnClickListener() {
                val intent = Intent(binding.nameOFRecipe?.context, WebActivity::class.java)//웹Activity intent 생성
                intent.putExtra("link",link)
                ContextCompat.startActivity(binding.nameOFRecipe.context, intent, null)//레시피 이름 클릭시, intent로 변환
            }

            binding.imgOfRecipe.setOnClickListener() {
                val intent = Intent(binding.nameOFRecipe?.context, WebActivity::class.java)//웹Activity intent 생성
                intent.putExtra("link",link)
                ContextCompat.startActivity(binding.nameOFRecipe.context, intent, null)//레시피 이름 클릭시, intent로 변환
            }
        }
        fun updatetest(docu : String,num : Int){
            val db = Firebase.firestore
            db.collection("testdb").document("레시피명").update("선호도",num)
        }
    }
}