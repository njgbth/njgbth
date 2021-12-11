package com.example.njgbth

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.SearchView
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.njgbth.databinding.ActivitySearchBinding
import kotlin.collections.ArrayList


class SearchActivity : AppCompatActivity() {
    private lateinit var binding: ActivitySearchBinding
    private val dataItem: ArrayList<ItemData> = arrayListOf()
    private val dataIngredient : ArrayList<IngredientData> = arrayListOf()
    public val dataSelect : ArrayList<IngredientData> = arrayListOf()

    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        binding = ActivitySearchBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.searchCategory.layoutManager = LinearLayoutManager(this,LinearLayoutManager.VERTICAL,false)
        binding.searchCategory.adapter = RecyclerItemAdapter(dataItem)
        binding.searchCategory.addItemDecoration(DividerItemDecoration(this,DividerItemDecoration.VERTICAL))
        addDataItem()


        binding.searchIng.layoutManager = GridLayoutManager(this,3)
        binding.searchIng.adapter = RecyclerIngrediAdapter(dataIngredient,dataSelect,binding)
        binding.searchIng.addItemDecoration(DividerItemDecoration(this,DividerItemDecoration.VERTICAL))
        addDataIngredient()


        binding.searchSelect.layoutManager = LinearLayoutManager(this,LinearLayoutManager.HORIZONTAL,false)
        binding.searchSelect.adapter = RecyclerSelectAdapter(dataSelect,binding)
        binding.searchSelect.addItemDecoration(DividerItemDecoration(this,DividerItemDecoration.VERTICAL))
        //addDataSelect()

        binding.searchRecommend.setOnClickListener {
            val intent = Intent(this,ResultActivity::class.java)
            intent.putExtra("data",dataSelect)
            startActivity(intent)
        }
        search()

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

    private fun search(){
        binding.searchSearch.setOnQueryTextListener(object : SearchView.OnQueryTextListener{
            override fun onQueryTextSubmit(query: String?): Boolean {  //검색 눌렀을 때
                if(query!=null)                                 //검색내용이 있을 때
                    println(query)
                return true
            }
            override fun onQueryTextChange(newText: String?): Boolean { //검색창 수정될 때
                return true
            }
        })
    }
}