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



    class ViewHolder(private val binding: IngredRecyclerBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(data: IngredientData ) {
            binding.ingredName.text = data.name
            Glide.with(binding.ingredImg.context).load(R.drawable.basic).into(binding.ingredImg)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerSelectAdapter.ViewHolder {
   /*   val view = LayoutInflater.from(parent.context).inflate(R.layout.item_recycler,parent, false)
        return ViewHolder(view)*/
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
   /* class ViewHolder(view: View): RecyclerView.ViewHolder(view) {
        private val itemName: TextView = view.name

        fun bind(data:List<String>) {
            itemName.text = data[0]
        }
    }*/
   fun removeDataSelect(text : String){       //dateSelect에 추가
       dataSet.remove(IngredientData(text))
       Sbinding.searchSelect.adapter = RecyclerSelectAdapter(dataSet,Sbinding)  //반영

   }

}