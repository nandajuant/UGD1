package com.example.komoke

import android.app.DatePickerDialog
import android.content.Intent
import android.icu.util.Calendar
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import com.example.komoke.databinding.ActivityHomeBinding

class HomeActivity : AppCompatActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)


        setContentView(R.layout.activity_home)

        val btnLogout = findViewById<Button>(R.id.btn_logout)

        btnLogout.setOnClickListener{
            val moveMain = Intent(this, MainActivity::class.java)
            startActivity(moveMain)
        }

//

    }


}