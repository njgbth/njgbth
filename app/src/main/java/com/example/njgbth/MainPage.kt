package com.example.njgbth

import android.content.ContentValues.TAG
import android.content.Intent
import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.SearchView
import android.widget.TextView
import com.example.njgbth.databinding.ActivityMainPageBinding
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.google.firebase.firestore.QueryDocumentSnapshot

import com.google.firebase.firestore.QuerySnapshot

import androidx.annotation.NonNull

import com.google.android.gms.tasks.OnCompleteListener




class MainPage : AppCompatActivity() {
    private lateinit var binding: ActivityMainPageBinding           //viewbinding
    var catename="전체"       //선택된 카테고리 이름
    var cateid=0            //선택된 카테고리 id
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainPageBinding.inflate(layoutInflater)   //viewbinding
        setContentView(binding.root)                                //화면출력
        binding.T1.setBackgroundColor(Color.LTGRAY)                 //초기선택값 색설정
        cateid=binding.T1.id                                        //초기 cateid 설정
        binding.searchIngredient.setOnQueryTextListener(object : SearchView.OnQueryTextListener{
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
    fun get_category(view: View) {              //textview onclick

        //println(binding.T2.text)              T2의 text값 출력
        if(cateid!=view.id){
            findViewById<TextView>(cateid).setBackgroundColor(Color.WHITE)  //기존선택값 색변경
            cateid=view.id                                                  //cateid 재설정
            catename=findViewById<TextView>(cateid).text.toString()         //catename 재설정
            findViewById<TextView>(cateid).setBackgroundColor(Color.LTGRAY)     //새로운 선택값 색 변경
        }
        //println(findViewById<TextView>(cateid).text)          //cateid의 text값 출력
        db()

    }
    fun db(){
        val db = Firebase.firestore
        db.collection("recipe")
            .get()
            .addOnSuccessListener { result ->
                for (document in result) {
                    document.reference.collection("재료").document("재료").get()
                        .addOnSuccessListener { result ->
                            Log.d(TAG, "${result.data}")

                        }
                        .addOnFailureListener { exception ->
                            Log.w(TAG, "Error getting documents.", exception)
                        }
                    Log.d(TAG, "${document.data}")
                }
            }
            .addOnFailureListener { exception ->
                Log.w(TAG, "Error getting documents.", exception)
            }
    }

}