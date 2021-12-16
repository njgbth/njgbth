package com.example.njgbth

import android.content.ContentValues.TAG
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import com.example.njgbth.databinding.ActivityLoginBinding
import com.example.njgbth.databinding.ActivitySignUpBinding
import com.google.firebase.auth.FirebaseAuth

class SignUpActivity : AppCompatActivity() {
    private lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = ActivitySignUpBinding.inflate(layoutInflater);
        setContentView(binding.root)
        auth = FirebaseAuth.getInstance()
        val id = binding.edtSignUpId
        val password = binding.edtSignUpPw
        val repassword = binding.edtSignUpPw
        val name = binding.edtSignUpPw
        val email = binding.edtSignUpEmail

        binding.btnSignUpSignUp.setOnClickListener {
            if (email.text.toString().length == 0 || password.text.toString().length == 0 || id.text.toString().length==0 || repassword.text.toString().length==0 || name.text.toString().length==0){
                Toast.makeText(this, "모든 정보를 입력해주세요.", Toast.LENGTH_SHORT).show()
            }
            else if(password.text.toString()!=repassword.text.toString()){
                Toast.makeText(this, "비밀번호가 일치하지 않습니다.", Toast.LENGTH_SHORT).show()
            }

            else {
                auth.createUserWithEmailAndPassword(email.text.toString(), password.text.toString())
                    .addOnCompleteListener(this) { task ->
                        if (task.isSuccessful) {
                            Log.d(TAG, "회원가입성공")
                            val user = auth.currentUser

                            finish()
                        } else {
                            Log.w(TAG, "createUserWithEmail:failure", task.exception)
                            Toast.makeText(
                                baseContext, "회원가입 실패",
                                Toast.LENGTH_SHORT
                            ).show()
                        }
                    }
            }
        }
    }
}