package com.example.njgbth

import android.content.ContentValues.TAG
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import com.example.njgbth.databinding.ActivityLoginBinding
import com.example.njgbth.databinding.ActivitySignUpBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser

class LoginActivity : AppCompatActivity() {

    private var auth : FirebaseAuth? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val binding = ActivityLoginBinding.inflate(layoutInflater);
        setContentView(binding.root)

        auth = FirebaseAuth.getInstance()
        val email = binding.edtLoginId
        val password = binding.edtLoginPw

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
            if (email.text.toString().length == 0 || password.text.toString().length == 0){
                Toast.makeText(this, "email 혹은 password를 반드시 입력하세요.", Toast.LENGTH_SHORT).show()
            }
            else {
                auth!!.signInWithEmailAndPassword(email.text.toString(), password.text.toString())
                    .addOnCompleteListener(this) { task ->
                        if (task.isSuccessful) {
                            // Sign in success, update UI with the signed-in user's information
                            Log.d(TAG, "로그인 성공")
                            val user = auth!!.currentUser
                            val intent = Intent(this,SearchActivity::class.java)
                            startActivity(intent)
                        } else {
                            // If sign in fails, display a message to the user.
                            Log.w(TAG, "signInWithEmail:failure", task.exception)
                            println("${email.text.toString()}, ${password.text.toString()}")
                            Toast.makeText(baseContext, "로그인 실패",
                                Toast.LENGTH_SHORT).show()
                        }

                        // ...
                    }
            }

        }

    }
    fun updateUI(cUser : FirebaseUser? = null){
        if(cUser != null) {
            //로그인 버튼과 기타 등등을 사용할 수 없게 함(일괄 묶어서 처리 하는 방법?)
            val intent = Intent(this,SearchActivity::class.java)
            startActivity(intent)
        } else {
            Toast.makeText(this, "로그인인", Toast.LENGTH_SHORT).show()

        }
        //Toast.makeText(this, "유저: "+cUser.toString(), Toast.LENGTH_SHORT).show()
    }
}