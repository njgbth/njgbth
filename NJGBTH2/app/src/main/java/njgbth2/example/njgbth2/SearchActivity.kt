package com.example.njgbth2

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.njgbth2.databinding.ActivitySearchBinding
import kotlin.collections.ArrayList


class SearchActivity : AppCompatActivity() {
    private lateinit var binding: ActivitySearchBinding
    private val dataItem: ArrayList<ItemData> = arrayListOf()
    private val dataIngredient : ArrayList<IngredientData> = arrayListOf()
    private val dataSelect : ArrayList<IngredientData> = arrayListOf()

    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        binding = ActivitySearchBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.searchCategory.layoutManager = LinearLayoutManager(this,LinearLayoutManager.VERTICAL,false)
        binding.searchCategory.adapter = RecyclerItemAdapter(dataItem)
        binding.searchCategory.addItemDecoration(DividerItemDecoration(this,DividerItemDecoration.VERTICAL))
        addDataItem()


        binding.searchIng.layoutManager = GridLayoutManager(this,3)
        binding.searchIng.adapter = RecyclerIngrediAdapter(dataIngredient)
        binding.searchIng.addItemDecoration(DividerItemDecoration(this,DividerItemDecoration.VERTICAL))
        addDataIngredient()


        binding.searchSelect.layoutManager = LinearLayoutManager(this,LinearLayoutManager.HORIZONTAL,false)
        binding.searchSelect.adapter = RecyclerSelectAdapter(dataSelect)
        binding.searchSelect.addItemDecoration(DividerItemDecoration(this,DividerItemDecoration.VERTICAL))
        addDataSelect()

        binding.searchRecommend.setOnClickListener {
            val intent = Intent(this,ResultActivity::class.java)
            startActivity(intent)
        }

    }

    private fun addDataItem() {
        dataItem.add(ItemData("전체"))
        dataItem.add(ItemData("면"))
        dataItem.add(ItemData("육류"))
        dataItem.add(ItemData("채소"))
        dataItem.add(ItemData("과일"))
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

    private fun addDataSelect() {
        dataSelect.add(IngredientData("벼"))
        dataSelect.add(IngredientData("쌀"))
        dataSelect.add(IngredientData("밀"))
        dataSelect.add(IngredientData("고등어"))
        dataSelect.add(IngredientData("삼치"))
        dataSelect.add(IngredientData("한우"))

    }
}