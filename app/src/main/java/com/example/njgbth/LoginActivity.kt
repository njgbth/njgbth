package com.example.njgbth

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.njgbth.databinding.ActivityLoginBinding
import com.example.njgbth.databinding.ActivitySignUpBinding

class LoginActivity : AppCompatActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val binding = ActivityLoginBinding.inflate(layoutInflater);
        setContentView(binding.root)
        binding.btnSignUp.setOnClickListener {
            val intent = Intent(this,SignUpActivity::class.java)
            startActivity(intent)
        }
        binding.btnFindId.setOnClickListener {
            val intent = Intent(this,FindIdActivity::class.java)
            startActivity(intent)
        }
        binding.btnFindPw.setOnClickListener {
            val intent = Intent(this,FindPwActivity::class.java)
            startActivity(intent)
        }
        binding.btnLogin.setOnClickListener {
            val intent = Intent(this,SearchActivity::class.java)
            startActivity(intent)
        }

    }
}