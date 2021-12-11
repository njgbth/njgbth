package com.example.njgbth

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.njgbth.databinding.ActivityResultBinding
import com.example.njgbth.databinding.ActivitySearchBinding

class ResultActivity : AppCompatActivity() {
    private lateinit var binding: ActivityResultBinding
    private val dataString: ArrayList<List<String>> = arrayListOf()
    private val dataInt : ArrayList<List<Int>> = arrayListOf()
    private var getdata : ArrayList<IngredientData> =arrayListOf()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityResultBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val intent: Intent =getIntent()     //데이터 받아옴
        getdata = intent.getSerializableExtra("data") as ArrayList<IngredientData>  //데이터 받아옴
        for(i in getdata){      //확인용
            println(i.name)
        }

        binding.resultRecycle.layoutManager = LinearLayoutManager(this,
            LinearLayoutManager.VERTICAL,false)
        binding.resultRecycle.adapter = RecyclerResultAdapter(dataString,dataInt)
        binding.resultRecycle.addItemDecoration(
            DividerItemDecoration(this,
                DividerItemDecoration.VERTICAL)
        )

        addDataString()
        addDataInt()
    }
    private fun addDataString() {
        dataString.add(listOf("소고기청경채볶음"))
        dataString.add(listOf("청경채말림"))
        dataString.add(listOf("청경채 무침"))
        dataString.add(listOf("소고기볶음청경채"))
        dataString.add(listOf("청경채볶음소고기"))
        dataString.add(listOf("볶음소고기청경채"))
    }
    private fun addDataInt() {
        dataInt.add(listOf(1))
        dataInt.add(listOf(2))
        dataInt.add(listOf(3))
        dataInt.add(listOf(4))
        dataInt.add(listOf(5))
        dataInt.add(listOf(0))

    }
}