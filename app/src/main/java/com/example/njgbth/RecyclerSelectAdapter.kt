package com.example.njgbth

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.njgbth.databinding.ActivitySearchBinding
import com.example.njgbth.databinding.IngredRecyclerBinding

class RecyclerSelectAdapter(
    private val dataSet: ArrayList<String>,
    private val Sbinding: ActivitySearchBinding
): RecyclerView.Adapter<RecyclerSelectAdapter.ViewHolder>()  {

    class ViewHolder(private val binding: IngredRecyclerBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(data: String ) {
            binding.ingredName.text = data
            Glide.with(binding.ingredImg.context).load(R.drawable.basic).into(binding.ingredImg)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerSelectAdapter.ViewHolder {

        val binding = IngredRecyclerBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        binding.ingredName.setOnClickListener(){
            removeDataSelect(binding.ingredName.text.toString())
        }
        binding.ingredImg.setOnClickListener(){
            removeDataSelect(binding.ingredName.text.toString())
        }

        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: RecyclerSelectAdapter.ViewHolder, position: Int) {
      holder.bind(dataSet[position])
    }

    override fun getItemCount(): Int {
        return dataSet.size
    }
   fun removeDataSelect(text : String){       //dateSelect에 추가
       dataSet.remove(text)
       Sbinding.searchSelect.adapter = RecyclerSelectAdapter(dataSet,Sbinding)  //반영

   }

}