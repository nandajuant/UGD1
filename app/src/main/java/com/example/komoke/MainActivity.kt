package com.example.komoke

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button


class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_main)



        val btnLoginMain = findViewById<Button>(R.id.btn_login_main)
        val btnRegisterMain = findViewById<Button>(R.id.btn_register_main)

        btnLoginMain.setOnClickListener{
            val moveLogin = Intent(this,LoginActivity::class.java)
            startActivity(moveLogin)
        }

        btnRegisterMain.setOnClickListener{
            val moveRegister = Intent(this,RegisterActivity::class.java)
            startActivity(moveRegister)
        }
    }
}