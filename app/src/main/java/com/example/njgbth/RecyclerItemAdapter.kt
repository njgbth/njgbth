package com.example.njgbth

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.njgbth.databinding.ItemRecyclerBinding

class RecyclerItemAdapter(private val dataSet: ArrayList<ItemData>) : RecyclerView.Adapter<RecyclerItemAdapter.ViewHolder>() {////일어나서 다시해보기. select받아와서 취소하고 되면 하기. 이미 선택된 자료에서 한번더 선택 -> 취소됨-> 그림제거

    //onBindViewHoler를 위한 정의 ViewHolder 정의
    class ViewHolder( private val binding: ItemRecyclerBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(data: ItemData) {
            binding.itemName.text = data.name
        }
    }
    //바인딩으로 아이템 레이아웃 결합
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): RecyclerItemAdapter.ViewHolder {
        val binding =
            ItemRecyclerBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }
    //View에 내용을 입력
    override fun onBindViewHolder(holder: RecyclerItemAdapter.ViewHolder, position: Int) {
      holder.bind(dataSet[position])
      }
    //아이템의 수
    override fun getItemCount(): Int {
        return dataSet.size
    }

}