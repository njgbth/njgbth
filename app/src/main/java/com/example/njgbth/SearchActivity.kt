package com.example.njgbth

import android.content.ContentValues
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.SearchView
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.njgbth.databinding.ActivitySearchBinding
import com.google.firebase.FirebaseException
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.tasks.await
import kotlin.collections.ArrayList


class SearchActivity : AppCompatActivity() {
    private lateinit var binding: ActivitySearchBinding
    private val dataItem: ArrayList<ItemData> = arrayListOf()
    private val dataIngredient : ArrayList<IngredientData> = arrayListOf()
    public val dataSelect : ArrayList<String> = arrayListOf()

    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        binding = ActivitySearchBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.searchCategory.layoutManager = LinearLayoutManager(this,LinearLayoutManager.VERTICAL,false)
        binding.searchCategory.adapter = RecyclerItemAdapter(dataItem,dataSelect,binding)
        binding.searchCategory.addItemDecoration(DividerItemDecoration(this,DividerItemDecoration.VERTICAL))
        addDataItem()


        binding.searchIng.layoutManager = GridLayoutManager(this,3)
        binding.searchIng.addItemDecoration(DividerItemDecoration(this,DividerItemDecoration.VERTICAL))
        CoroutineScope(Dispatchers.IO).launch {
            runBlocking {
                alldb()
            }
            CoroutineScope(Dispatchers.Main).launch {
                binding.searchIng.adapter = RecyclerIngrediAdapter(dataIngredient,dataSelect,binding)
            }
        }


        binding.searchSelect.layoutManager = LinearLayoutManager(this,LinearLayoutManager.HORIZONTAL,false)
        binding.searchSelect.adapter = RecyclerSelectAdapter(dataSelect,binding)
        binding.searchSelect.addItemDecoration(DividerItemDecoration(this,DividerItemDecoration.VERTICAL))
        //addDataSelect()

        binding.searchRecommend.setOnClickListener {
            val intent = Intent(this,ResultActivity::class.java)
            println(dataSelect)
            intent.putExtra("data",dataSelect)
            startActivity(intent)
        }
        search_click()

    }

    private fun addDataItem() {
        dataItem.add(ItemData("전체"))
        dataItem.add(ItemData("면"))
        dataItem.add(ItemData("육류"))
        dataItem.add(ItemData("어패류"))
        dataItem.add(ItemData("채소"))
        dataItem.add(ItemData("과일"))
        dataItem.add(ItemData("유제품"))
    }
    /*
    private fun addDataIngredient() {
        //DB에 재료가 구분되면 저장. 아래는 예시
        dataIngredient.add(IngredientData("벼"))
        dataIngredient.add(IngredientData("오징어"))
        dataIngredient.add(IngredientData("쌀"))
        dataIngredient.add(IngredientData("밀"))
        dataIngredient.add(IngredientData("고등어"))
        dataIngredient.add(IngredientData("삼치"))
        dataIngredient.add(IngredientData("한우"))
    }

    private fun addDataSelect() {   //나중에 삭제
        dataSelect.add(IngredientData("벼"))
        dataSelect.add(IngredientData("쌀"))
        dataSelect.add(IngredientData("밀"))
        dataSelect.add(IngredientData("고등어"))
        dataSelect.add(IngredientData("삼치"))
        dataSelect.add(IngredientData("한우"))

    }
*/
    private fun search_click(){
        binding.searchSearch.setOnQueryTextListener(object : SearchView.OnQueryTextListener{
            override fun onQueryTextSubmit(query: String?): Boolean {  //검색 눌렀을 때
                if(query!=null)                                 //검색내용이 있을 때
                    println(query)
                    CoroutineScope(Dispatchers.IO).launch {
                        runBlocking {
                            saerch_ingredi(query.toString())
                        }
                        CoroutineScope(Dispatchers.Main).launch {
                            binding.searchIng.adapter = RecyclerIngrediAdapter(dataIngredient,dataSelect,binding)
                            println("bbb")
                        }
                    }
                return true
            }
            override fun onQueryTextChange(newText: String?): Boolean { //검색창 수정될 때
                return true
            }
        })
    }
    suspend fun saerch_ingredi(s : String): Boolean {
        val db = Firebase.firestore
        var name : ArrayList<String> = arrayListOf()
        var r = false
        if(s=="")
            return false
        dataIngredient.clear()

        return try {
            db.collection("재료db").document("재료명")
                .get()
                .addOnSuccessListener { result ->
                    Log.d(ContentValues.TAG, "${result.data}")
                    if(result.data!=null){
                        name= result.data!!.keys.toList() as ArrayList<String>
                        println(name)
                        for(i in 0..name.size-1){
                            if(name[i].startsWith(s)){
                                dataIngredient.add(IngredientData(name[i]))
                            }
                        }

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

    suspend fun alldb(): Boolean {
        val db = Firebase.firestore
        var name : ArrayList<String> = arrayListOf()
        var r = false
        dataIngredient.clear()

        return try {
            db.collection("재료db").document("재료명")
                .get()
                .addOnSuccessListener { result ->
                    Log.d(ContentValues.TAG, "${result.data}")
                    if(result.data!=null){
                        name= result.data!!.keys.toList() as ArrayList<String>
                        for(i in 0..name.size-1){
                            dataIngredient.add(IngredientData(name[i]))
                        }
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