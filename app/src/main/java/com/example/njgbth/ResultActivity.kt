package com.example.njgbth

import android.content.ContentValues
import android.content.ContentValues.TAG
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.util.Log
import android.view.View
import android.widget.*
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.njgbth.databinding.ActivityResultBinding
import com.example.njgbth.databinding.ActivitySearchBinding
import com.google.firebase.firestore.QueryDocumentSnapshot
import com.google.firebase.firestore.QuerySnapshot
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.tasks.await
import kotlin.concurrent.timer
import android.os.Looper
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking


class ResultActivity : AppCompatActivity() {
    private lateinit var binding: ActivityResultBinding
    private val dataString: ArrayList<String> = arrayListOf()
    private val datalink: ArrayList<String> = arrayListOf()
    private val dataInt : ArrayList<Int> = arrayListOf()
    private var getdata : ArrayList<String> =arrayListOf()
    private var getweight : ArrayList<Int> =arrayListOf()
    private lateinit var document : QuerySnapshot
    private val dataRecipe: ArrayList<RecipeData> = arrayListOf()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityResultBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val intent: Intent =getIntent()     //데이터 받아옴
        getdata = intent.getSerializableExtra("data") as ArrayList<String>  //데이터 받아옴
        for(i in getdata){      //확인용
            println(i)
        }
        if(getdata.isEmpty()){
            println("empty")
            println(getdata)
        }
        if(getdata==null){
            println("null")
            println(getdata)
        }
        if(getdata.isNullOrEmpty()){
            println("nullor empty")
            println(getdata)
            Toast.makeText(applicationContext,"재료를 선택해주세요.", Toast.LENGTH_SHORT).show()
            finish()
        }

        binding.resultRecycle.layoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL,false)
        binding.resultRecycle.adapter = RecyclerResultAdapter(dataRecipe)
        binding.resultRecycle.addItemDecoration(
            DividerItemDecoration(this,
                DividerItemDecoration.VERTICAL)
        )
        addRecipe()
        val spinner = binding.spinner
        spinner.adapter = ArrayAdapter.createFromResource(this, R.array.sortList, android.R.layout.simple_spinner_item)
        spinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onNothingSelected(parent: AdapterView<*>?) {
            }
            override fun onItemSelected(
                parent: AdapterView<*>?,
                view: View?,
                position: Int,
                id: Long
            ) {
                when (position) {
                    //가중치 순으로 보여주기
                    0 -> {
                        weight_sort()
                    }
                    //선호도 순으로 보여주기 선택시
                    1 -> {
                        like_sort()
                    }

                }
            }
        }
        var second = 0
        timer(period = 1000,initialDelay = 1000){
            second++
            if(second==3){
                if(dataRecipe.isNullOrEmpty()){
                    val handler = Handler(Looper.getMainLooper())
                    handler.postDelayed({
                        Toast.makeText(applicationContext,"맞는 레시피가 없습니다. 다시 재료를 선택해주세요", Toast.LENGTH_SHORT).show()
                    }, 0)

                    finish()
                }
                cancel()
            }
        }
        //




    }
    fun weight_sort(){
        dataRecipe.sortBy { it.weight }
        dataRecipe.reverse()
        binding.resultRecycle.adapter = RecyclerResultAdapter(dataRecipe)
    }
    fun like_sort(){
        dataRecipe.sortBy { it.numOFHeart }
        dataRecipe.reverse()
        binding.resultRecycle.adapter = RecyclerResultAdapter(dataRecipe)
    }
    fun addRecipe(){
        val db = Firebase.firestore
        db.collection("recipe")
            .get()
            .addOnSuccessListener { documents ->
                for(document in documents){
                    document.reference.collection("재료").document("재료").get()
                        .addOnSuccessListener { result ->
                            if (result != null) {
                                val a = (getdata.toSet()?.subtract(result.data!!.keys.toSet()))
                                if (a.isEmpty()) {
                                    println("이 레시피다 : ${document.id} ${document.get("링크")} ${document.get("선호도")}")
                                    println(result.data)
                                    var sum=0
                                    for(i in getdata.toSet())
                                        sum+=result.data!!.get(i).toString().toInt()
                                    dataRecipe.add(RecipeData(document.id,document.get("링크") as String, sum, document.get("선호도").toString().toInt()))
                                    //binding.resultRecycle.adapter = RecyclerResultAdapter(dataRecipe)
                                    weight_sort()
                                }
                            }
                        }
                }

            }
            .addOnFailureListener { exception ->
                Log.w(ContentValues.TAG, "Error getting documents.", exception)

            }
    }
    suspend fun db(){
        val db = Firebase.firestore
        db.collection("recipe")
            .get()
            .addOnSuccessListener { documents ->
                document = documents
            }
            .addOnFailureListener { exception ->
                Log.w(TAG, "Error getting documents.", exception)

            }.await()
    }

    suspend fun getdocu(document : QueryDocumentSnapshot){
        document.reference.collection("재료").document("재료").get()
            .addOnSuccessListener { result ->
                //Log.d(ContentValues.TAG, "${document.id} : ${result.data}")
                if(result !=null){
                    val a = (getdata.toSet()?.subtract(result.data!!.keys.toSet()))
                    if(a.isEmpty()){
                        println("이 레시피다 : ${document.id}")
                        datalink.add(document.get("링크") as String)
                        dataInt.add(document.get("선호도").toString().toInt())
                    }
                    println(document.id)
                    //println("a"+a)
                    //println(result.data?.keys?.toSet())
                    //println(getdata.toSet())
                }
            }.await()
    }
}