package com.example.komoke

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import androidx.constraintlayout.widget.ConstraintLayout
import com.google.android.material.textfield.TextInputLayout


class LoginActivity : AppCompatActivity() {

    private lateinit var inputUsername: TextInputLayout
    private lateinit var inputPassword: TextInputLayout
    private lateinit var mainLayout: ConstraintLayout


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)


//click
        inputUsername = findViewById(R.id.til_email)
        inputPassword = findViewById(R.id.til_password)
        mainLayout = findViewById(R.id.mainLayout)
        val btnLogin: Button = findViewById(R.id.btn_login)
        val btnHaventAccount: Button = findViewById(R.id.btn_havent_account)

        btnLogin.setOnClickListener(View.OnClickListener {
            var checkLogin = false
            val username: String = inputUsername.getEditText()?.getText().toString()
            val password: String = inputPassword.getEditText()?.getText().toString()

            if(username.isEmpty()){
                inputUsername.setError("Username Must Be Filled With Text")
                checkLogin = false
            }

            if(password.isEmpty()){
               inputPassword.setError("Password Must Be Filled With Text")
                checkLogin = false
            }

            if(username == "admin" && password == "0864") checkLogin = true
            if(!checkLogin) return@OnClickListener
            val moveHome = Intent(this@LoginActivity, HomeActivity::class.java)
            startActivity(moveHome)


        })

        btnHaventAccount.setOnClickListener {
            val moveRegist = Intent(this,RegisterActivity::class.java)
            startActivity(moveRegist)
        }

    }

}