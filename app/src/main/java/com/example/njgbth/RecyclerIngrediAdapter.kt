package com.example.njgbth

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.njgbth.databinding.ActivitySearchBinding
import com.example.njgbth.databinding.IngredRecyclerBinding

class RecyclerIngrediAdapter(
    private val dataSet: ArrayList<IngredientData>,
    private val dataSelect: ArrayList<IngredientData>,
    private val Sbinding: ActivitySearchBinding
) :RecyclerView.Adapter<RecyclerIngrediAdapter.ViewHolder>() {
    class ViewHolder(private val binding: IngredRecyclerBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(data: IngredientData) {
            binding.ingredName.text = data.name
            Glide.with(binding.ingredImg.context).load(R.drawable.basic).into(binding.ingredImg)    //glide 뭐하는 코드인교
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerIngrediAdapter.ViewHolder {
        val binding = IngredRecyclerBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        binding.ingredName.setOnClickListener(){
            addDataSelect(binding.ingredName.text.toString())
        }
        binding.ingredImg.setOnClickListener(){
            addDataSelect(binding.ingredName.text.toString())
        }

        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: RecyclerIngrediAdapter.ViewHolder, position: Int) {
        holder.bind(dataSet[position])
    }
    override fun getItemCount(): Int {
        return dataSet.size
    }
    fun addDataSelect(text : String){       //dateSelect에 추가
        if(!dataSelect.contains(IngredientData(text))){
            dataSelect.add(IngredientData(text))
            Sbinding.searchIng.adapter = RecyclerIngrediAdapter(dataSet,dataSelect,Sbinding)    //반영
        }

    }

}