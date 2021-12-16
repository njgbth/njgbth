package com.example.njgbth


import android.content.ContentValues.TAG
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import java.util.regex.Pattern


class SignUpActivity : AppCompatActivity() {
    private lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sign_up)

        val inputEmail = findViewById<EditText>(R.id.edtSignUpId)
        val button = findViewById<Button>(R.id.btnSignUpSignUp)
        val firstPW = findViewById<EditText>(R.id.edtSignUpPw)
        val secondPW = findViewById<EditText>(R.id.edtSignUpRePw)
        val name = findViewById<EditText>(R.id.edtSignUpName)
        auth = FirebaseAuth.getInstance()
        fun checkEmail():Boolean {
            var email = inputEmail.text.toString().trim()
            var idPattern = android.util.Patterns.EMAIL_ADDRESS

            if (idPattern.matcher(email).matches()) {
                inputEmail.setTextColor(R.color.black.toInt())
                return true
            }
            else {
                inputEmail.setTextColor(-65536)//빨강
                return false
            }
        }
        fun checkPW():Boolean {
            var PW1 = firstPW.text.toString().trim()
            var PW2 = secondPW.text.toString().trim()
            val pattern = Pattern.matches(PW1,PW2)
            if (pattern) {
                secondPW.setTextColor(R.color.black.toInt())
                return true
            }
            else {
                secondPW.setTextColor(-65536)
                return false
            }
        }
        button.setOnClickListener() {
            if (!checkEmail()) {
                Toast.makeText(applicationContext,"이메일 형식이 아닙니다. 다시 입력하세요.",Toast.LENGTH_SHORT).show()
                println("1")
            }
            else if (!checkPW()) {
                    Toast.makeText(
                        applicationContext,
                        "비밀번호가 일치하지 않습니다. 다시 입력하세요.",
                        Toast.LENGTH_SHORT
                    ).show()
                println("2")
            }
            else{
                auth.createUserWithEmailAndPassword(inputEmail.text.toString(), firstPW.text.toString())
                    .addOnCompleteListener(this) { task ->
                        if (task.isSuccessful) {
                            println("${inputEmail.text.toString()}+${firstPW.text.toString()}")
                            Log.d(TAG, "축하합니다! 회원가입이 완료되었습니다.")
                            Toast.makeText(
                                baseContext, "축하합니다! 회원가입이 완료되었습니다.",
                                Toast.LENGTH_SHORT
                            ).show()
                            val user = auth.currentUser
                            println("3")
                            finish()
                        } else {
                            Log.w(TAG, "createUserWithEmail:failure", task.exception)
                            Toast.makeText(
                                baseContext, "회원가입 실패",
                                Toast.LENGTH_SHORT
                            ).show()
                            println("4")
                        }
                    }
            }
        }
    }

}