package com.example.njgbth

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.njgbth.databinding.ActivitySearchBinding
import com.example.njgbth.databinding.IngredRecyclerBinding

class RecyclerSelectAdapter(
    private val dataSet: ArrayList<IngredientData>,
    private val Sbinding: ActivitySearchBinding
): RecyclerView.Adapter<RecyclerSelectAdapter.ViewHolder>()  {

    var num=0

    class ViewHolder(private val binding: IngredRecyclerBinding, private var num : Int) : RecyclerView.ViewHolder(binding.root) {
        fun bind(data: IngredientData ) {
            binding.ingredName.text = data.name
            Glide.with(binding.ingredImg.context).load(R.drawable.basic).into(binding.ingredImg)
            num=data.cate
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerSelectAdapter.ViewHolder {

        val binding = IngredRecyclerBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        binding.ingredName.setOnClickListener(){
            removeDataSelect(binding.ingredName.text.toString(),num)
        }
        binding.ingredImg.setOnClickListener(){
            removeDataSelect(binding.ingredName.text.toString(),num)
        }

        return ViewHolder(binding,num)
    }

    override fun onBindViewHolder(holder: RecyclerSelectAdapter.ViewHolder, position: Int) {
      holder.bind(dataSet[position])
    }

    override fun getItemCount(): Int {
        return dataSet.size
    }
   fun removeDataSelect(text : String, num : Int){       //dateSelect에 추가
       dataSet.remove(IngredientData(text,num))
       Sbinding.searchSelect.adapter = RecyclerSelectAdapter(dataSet,Sbinding)  //반영

   }

}