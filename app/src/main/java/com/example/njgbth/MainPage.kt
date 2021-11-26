package com.example.njgbth

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import com.example.njgbth.databinding.ActivityMainPageBinding

class MainPage : AppCompatActivity() {
    private lateinit var binding: ActivityMainPageBinding           //viewbinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainPageBinding.inflate(layoutInflater)   //viewbinding
        setContentView(binding.root)                                //화면출력

    }
    fun get_category(view: View) {
        println(binding.T1.text)
    }
}