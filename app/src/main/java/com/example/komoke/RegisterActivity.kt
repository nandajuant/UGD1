package com.example.komoke

import android.app.DatePickerDialog
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.graphics.BitmapFactory
import android.graphics.Color
import android.icu.util.Calendar
import android.os.Build
import android.os.Bundle
import android.util.Patterns
import android.view.View
import android.widget.Button
import android.widget.DatePicker
import android.widget.EditText
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import com.example.komoke.databinding.ActivityMainBinding
import com.example.komoke.roomUser.Constant
import com.example.komoke.roomUser.User
import com.example.komoke.roomUser.UserDB
import com.google.android.material.textfield.TextInputLayout
import kotlinx.android.synthetic.main.activity_register.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch


class RegisterActivity : AppCompatActivity() {

    val db by lazy { UserDB(this)}
    private var userId: Int = 0
    private lateinit var inputUsername: TextInputLayout
    private lateinit var inputPassword: TextInputLayout
    private lateinit var inputEmail: TextInputLayout
    private lateinit var inputPhone: TextInputLayout
    var sharedPreferences: SharedPreferences? = null
    private val myPreference = "myPref"
    private val userK = "userKey"
    private val passK = "passKey"

    private var binding: ActivityMainBinding? = null
    private val CHANNEL_ID_1 = "channel_notification_01"
    private val CHANNEL_ID_2 = "channel_notification_02"
    private val notificationId1 = 101
    private val notificationId2 = 102

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_register)

        createNotificationChannel()

        sharedPreferences = getSharedPreferences(myPreference, Context.MODE_PRIVATE)


        inputUsername = findViewById(R.id.til_email)
        inputPassword = findViewById(R.id.til_password)
        inputEmail = findViewById(R.id.til_mail)
        inputPhone = findViewById(R.id.til_phone)
//        inputUsername = findViewById(R.id.et_email)
//        inputPassword = findViewById(R.id.et_password)
//        inputEmail = findViewById(R.id.et_mail)
//        inputPhone = findViewById(R.id.et_phone)
        val btnRegister: Button = findViewById(R.id.btn_register)
        val btnHaveAccount: Button = findViewById(R.id.btn_have_account)
        val tv = findViewById<Button>(R.id.btn_datePicker)
        val cal = Calendar.getInstance()
        val myYear = cal.get(Calendar.YEAR)
        val myMonth = cal.get(Calendar.MONTH)
        val day = cal.get(Calendar.DAY_OF_MONTH)


        btn_datePicker.setOnClickListener {
            val datePickerDialog = DatePickerDialog(this, DatePickerDialog.OnDateSetListener{ view, year, month, dayOfMonth ->
                tv.text= "Date: " + dayOfMonth + "/ " + (month+1) + "/ " + year
            },myYear,myMonth,day)
            datePickerDialog.show()
        }


        btnRegister.setOnClickListener (View.OnClickListener{
//
//            inputUsername = findViewById(R.id.et_email)
//            inputPassword = findViewById(R.id.et_password)
//            inputEmail = findViewById(R.id.et_mail)
//            inputPhone = findViewById(R.id.et_phone)
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

            if(phone.isEmpty()) {
                inputPhone.setError("Phone Must Be Filled With Text ")
                checkRegister = false

            }

            if(!username.isEmpty() && !password.isEmpty() && !email.isEmpty() && !phone.isEmpty() && phone.length == 12){
                checkRegister = true

                // Database ngebuat eror jadi app ketutup

                CoroutineScope(Dispatchers.IO).launch {
                    db.userDao().addUser(
                        User(0, username, password, email, phone)
                    )
                    finish()
                }

                var user: String = inputUsername.editText?.text.toString().trim()
                var pass: String = inputPassword.editText?.text.toString().trim()
                val editor: SharedPreferences.Editor = sharedPreferences!!.edit()
                editor.putString(userK, user)
                editor.putString(passK, pass)
                editor.apply()

                sendNotification1()
            }
            if(!checkRegister) return@OnClickListener



//            CoroutineScope(Dispatchers.IO).launch {
//                db.userDao().addUser(
//                    User(0, username, password, email, phone)
//                )
//                finish()
//            }

//            val mBundle = Bundle()
//
//            mBundle.putString("user", inputUsername.toString())
//            mBundle.putString("pw", inputPassword.toString())
//            mBundle.putString("email", inputEmail.toString())
//            mBundle.putString("phone", inputPhone.toString())
//
//            moveHome.putExtra("register", mBundle)

            val moveHome = Intent(this, LoginActivity::class.java)

            startActivity(moveHome)
//            startActivity(Intent(this, LoginActivity::class.java))

        })

        // tidk ada akun pindah ke login
        btnHaveAccount.setOnClickListener{
            startActivity(Intent(this, LoginActivity::class.java))
        }


//       datePicker.setOnClickListener {
//            val datePicker = DatePickerDialog(this, DatePickerDialog.OnDateSetListener{ view, year, month, dayOfMonth ->
//                val dat = (dayOfMonth.toString() + "-" + (month + 1) + "-" + year)
//                   datePicker.setText(dat)
//
//            },myYear,myMonth,day)
//            datePicker.show()
//        }

//        btn_register.setOnClickListener {
//            CoroutineScope(Dispatchers.IO).launch {
//                db.userDao().addUser(
//                    User(0, til_email.getEditText()?.getText().toString(),
//                        til_password.getEditText()?.getText().toString(),
//                        til_mail.getEditText()?.getText().toString(),
//                        til_phone.getEditText()?.getText().toString())
//                )
//                finish()
//            }
//        }
    }




//    fun getUser() {
//        userId = intent.getIntExtra("intent_id", 0)
//        CoroutineScope(Dispatchers.IO).launch {
//            val notes = db.userDao().getUser(userId)[0]
//            edit_title.setText(notes.title)
//            edit_note.setText(notes.note)
//        }
//    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return super.onSupportNavigateUp()
    }

    private fun createNotificationChannel() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O){
            val name = "Notification Title"
            val descriptionText = "Notification Description"
            val channel1 = NotificationChannel(CHANNEL_ID_1,name, NotificationManager.IMPORTANCE_DEFAULT).apply {
                description = descriptionText
            }
            val channel2 = NotificationChannel(CHANNEL_ID_2,name, NotificationManager.IMPORTANCE_DEFAULT).apply {
                description = descriptionText
            }
            val notificationManager : NotificationManager =
                getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
            notificationManager.createNotificationChannel(channel1)
            notificationManager.createNotificationChannel(channel2)
        }
    }

    private fun sendNotification1() {

        val intent : Intent = Intent(this, MainActivity::class.java).apply {
            flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
        }
        val pendingIntent: PendingIntent = PendingIntent.getActivity(this, 0,intent, PendingIntent.FLAG_MUTABLE)

        val broadcastIntent : Intent = Intent(this,NotificationReceiver::class.java)
        broadcastIntent.putExtra("toastMessage", "SELAMAT ANDA BERHASIL REGISTRASI")
        val actionIntent = PendingIntent.getBroadcast(this, 0, broadcastIntent, PendingIntent.FLAG_MUTABLE)

        val builder = NotificationCompat.Builder(this, CHANNEL_ID_1)
            .setSmallIcon(R.drawable.ic_baseline_looks_one_24)
            .setContentTitle(inputUsername?.editText?.text.toString())
            .setContentText("ANDA TELAH REGISTRASI")
            .setCategory(NotificationCompat.CATEGORY_MESSAGE)
            .setStyle(NotificationCompat.BigPictureStyle()
                .bigPicture(BitmapFactory.decodeResource(getResources(), R.drawable.illustration_started)))
            .setColor(Color.BLUE)
            .setAutoCancel(true)
            .setOnlyAlertOnce(true)
            .setContentIntent(pendingIntent)
            .addAction(R.mipmap.ic_launcher, "Toast", actionIntent)
            .setPriority(NotificationCompat.PRIORITY_DEFAULT)

        with(NotificationManagerCompat.from(this)){
            notify(notificationId1,builder.build())
        }
    }

}
