package com.example.komoke

import android.app.DatePickerDialog
import android.content.Intent
import android.icu.util.Calendar
import android.os.Bundle
import android.util.Patterns
import android.view.View
import android.widget.Button
import android.widget.DatePicker
import android.widget.EditText
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.textfield.TextInputLayout


class RegisterActivity : AppCompatActivity() {

    private lateinit var inputUsername: TextInputLayout
    private lateinit var inputPassword: TextInputLayout
    private lateinit var inputEmail: TextInputLayout
    private lateinit var inputPhone: TextInputLayout

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_register)

        inputUsername = findViewById(R.id.til_email)
        inputPassword = findViewById(R.id.til_password)
        inputEmail = findViewById(R.id.til_mail)
        inputPhone = findViewById(R.id.til_phone)
        val btnRegister: Button = findViewById(R.id.btn_register)
        val btnHaveAccount: Button = findViewById(R.id.btn_have_account)
        val tv = findViewById<Button>(R.id.btn_datePicker)
        val cal = Calendar.getInstance()
        val myYear = cal.get(Calendar.YEAR)
        val myMonth = cal.get(Calendar.MONTH)
        val day = cal.get(Calendar.DAY_OF_MONTH)

        btnRegister.setOnClickListener(View.OnClickListener {
            var checkRegister = false
            val username: String = inputUsername.getEditText()?.getText().toString()
            val password: String = inputPassword.getEditText()?.getText().toString()
            val email: String = inputEmail.getEditText()?.getText().toString()
            val phone: String = inputPhone.getEditText()?.getText().toString()
            val emailPattern = "[a-zA-Z0-9._-]+@[a-z]+ +[a-z]+"

            if(username.isEmpty()){
                inputUsername.setError("Username Must Be Filled With Text")
                checkRegister = false
            }

            if(password.isEmpty()){
                inputPassword.setError("Password Must Be Filled With Text")
                checkRegister = false
            }

            if(email.isEmpty()){
                inputEmail.setError("Email Must Be Filled With Text")
                checkRegister = false
            } else if(emailPattern == email){
                inputEmail.setError("Its not an email addressed")
                checkRegister
            }

            if(phone.isEmpty()){
                inputPhone.setError("Phone Must Be Filled With Text ")
                checkRegister = false

            } else if (phone.length <11){
                inputPhone.setError("Phone Number Was To Short")
                checkRegister = false
            }


            if(!checkRegister) return@OnClickListener
            val moveHome = Intent(this@RegisterActivity, HomeActivity::class.java)
            startActivity(moveHome)


        })


        btnHaveAccount.setOnClickListener{
            startActivity(Intent(this, LoginActivity::class.java))
        }

        tv.setOnClickListener {
            val datePickerDialog = DatePickerDialog(this, DatePickerDialog.OnDateSetListener{ view, year, month, dayOfMonth ->
                tv.text= "Date: " + dayOfMonth + "/ " + (month+1) + "/ " + year
            },myYear,myMonth,day)
            datePickerDialog.show()
        }

//        datePicker.setOnClickListener {
//            val datePicker = DatePickerDialog(this, DatePickerDialog.OnDateSetListener{ view, year, month, dayOfMonth ->
//                val dat = (dayOfMonth.toString() + "-" + (month + 1) + "-" + year)
//                   datePicker.setText(dat)
//
//            },myYear,myMonth,day)
//            datePicker.show()
//        }

    }


}
