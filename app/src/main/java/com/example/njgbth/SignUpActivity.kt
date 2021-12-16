package com.example.njgbth

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import java.util.regex.Pattern

class SignUpActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sign_up)


        val inputEmail = findViewById<EditText>(R.id.edtSignUpId)
        val button = findViewById<Button>(R.id.btnSignUpSignUp)
        val firstPW = findViewById<EditText>(R.id.edtSignUpPw)
        val secondPW = findViewById<EditText>(R.id.edtSignUpRePw)
        val name = findViewById<EditText>(R.id.edtSignUpName)
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
            }
            else {
                if (!checkPW()) {
                    Toast.makeText(
                        applicationContext,
                        "비밀번호가 일치하지 않습니다. 다시 입력하세요.",
                        Toast.LENGTH_SHORT
                    ).show()
                }
                else {
                    Toast.makeText(applicationContext,"축하합니다! 회원가입이 완료되었습니다.",Toast.LENGTH_SHORT).show()
                    val intent = Intent(this,LoginActivity::class.java)
                    startActivity(intent)
                }
            }
        }

    }

}