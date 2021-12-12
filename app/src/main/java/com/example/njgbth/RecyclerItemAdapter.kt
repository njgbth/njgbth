package com.example.njgbth

import android.content.ContentValues
import android.graphics.Color
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.njgbth.databinding.ActivitySearchBinding
import com.example.njgbth.databinding.ItemRecyclerBinding
import com.google.firebase.FirebaseException
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.tasks.await

class RecyclerItemAdapter(
    private val dataSet: ArrayList<ItemData>,
    private val dataSelect: ArrayList<IngredientData>,
    private val Sbinding: ActivitySearchBinding
    ) : RecyclerView.Adapter<RecyclerItemAdapter.ViewHolder>() {////일어나서 다시해보기. select받아와서 취소하고 되면 하기. 이미 선택된 자료에서 한번더 선택 -> 취소됨-> 그림제거
    //onBindViewHoler를 위한 정의 ViewHolder 정의
    var item : ArrayList<IngredientData> = arrayListOf()
    class ViewHolder( private val binding: ItemRecyclerBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(data: ItemData) {
            binding.itemName.text = data.name
        }
    }
    //바인딩으로 아이템 레이아웃 결합
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerItemAdapter.ViewHolder {
        val binding = ItemRecyclerBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        binding.itemName.setOnClickListener(){
            val n = getIngredi(binding.itemName.text.toString())
            CoroutineScope(Dispatchers.IO).launch {
                var result =false
                runBlocking {
                    result = db(n)
                }
                println("출력:"+result)
                println("item"+item)
                CoroutineScope(Dispatchers.Main).launch {
                    Sbinding.searchIng.adapter = RecyclerIngrediAdapter(item,dataSelect,Sbinding)
                    println("bbb")
                }
            }
        }
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
    fun getIngredi(catename : String) : Int{

        when (catename){
            "전체" -> return 0
            "곡류" -> return 1
            "면" -> return 2
            "육류" -> return 3
            "채소" -> return 4
            "과일" -> return 5
            "어패류" -> return 6
            "유제품" -> return 7
        }
        return -1
    }
    suspend fun db(n : Int): Boolean {
        val db = Firebase.firestore
        var name : ArrayList<String> = arrayListOf()
        var cate : ArrayList<Int> = arrayListOf()
        var r = false
        if(n==-1)
            return false
        item.clear()

        return try {
            db.collection("재료db").document("재료명")
                .get()
                .addOnSuccessListener { result ->
                    Log.d(ContentValues.TAG, "${result.data}")
                    if(result.data!=null){
                        name= result.data!!.keys.toList() as ArrayList<String>
                        cate= result.data!!.values.toList() as ArrayList<Int>
                        for(i in 0..cate.size-1){
                            if(cate[i].toInt()==n){
                                item.add(IngredientData(name[i],cate[i]))
                            }
                        }
                        println("n : "+n)
                        println("db완료"+item)
                    }
                    r=true
                }
                .addOnFailureListener { exception ->
                    Log.w(ContentValues.TAG, "Error getting documents.", exception)
                    r=false
                }.await()
            r
        }catch (e: FirebaseException){
            Log.e("error:","erroe:"+e.message.toString())
            false
        }
    }
}