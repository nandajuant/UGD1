package com.example.komoke

import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import androidx.appcompat.app.AlertDialog
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.lifecycle.LifecycleOwner
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import androidx.core.view.get
import androidx.room.Room
import com.example.komoke.roomUser.UserDB
import com.google.android.material.textfield.TextInputLayout
import kotlinx.android.synthetic.main.activity_login.*
import kotlinx.android.synthetic.main.activity_login.view.*
import kotlinx.android.synthetic.main.activity_register.view.*
import kotlinx.coroutines.*
import android.widget.*
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import androidx.lifecycle.lifecycleScope
import com.example.komoke.databinding.ActivityMainBinding


class LoginActivity : AppCompatActivity() {

    val db by lazy { UserDB(this) }
//    lateinit var db: UserDB
    lateinit var registerAdapter: RegisterAdapter

    private lateinit var inputUsername: TextInputLayout
    private lateinit var inputPassword: TextInputLayout
    private lateinit var mainLayout: ConstraintLayout

    lateinit var usrnm: String
    lateinit var pwd: String

    var sharedPreferences: SharedPreferences? = null
    private val myPreference = "myPref"
    private val userK = "userKey"
    private val passK = "passKey"

    lateinit var mBundle:Bundle
    lateinit var vUser:String
    lateinit var vPw:String

    private var binding: ActivityMainBinding? = null
    private val CHANNEL_ID_1 = "channel_notification_01"
    private val CHANNEL_ID_2 = "channel_notification_02"
    private val notificationId1 = 101
    private val notificationId2 = 102

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        createNotificationChannel()

//        db = Room.databaseBuilder(applicationContext, UserDB::class.java, "12345.db").build()
//
//        GlobalScope.launch {
//            initData() //memanggil function di dalam coroutine

//        }

//click
        inputUsername = findViewById(R.id.til_emailLogin)
        inputPassword = findViewById(R.id.til_passwordLogin)
        mainLayout = findViewById(R.id.mainLayout)
        val btnLogin: Button = findViewById(R.id.btn_login)
        val btnHaventAccount: Button = findViewById(R.id.btn_havent_account)

        getBundle()
        usrnm=""
        pwd=""

        btnLogin.setOnClickListener(View.OnClickListener {
//            inputUsername = findViewById(R.id.et_email)
//            inputPassword = findViewById(R.id.et_password)
            var checkLogin = false
            val username: String = inputUsername.getEditText()?.getText().toString()
            val password: String = inputPassword.getEditText()?.getText().toString()

            // ini kalo dibuka loginnya eror, pake default admin-admin juga eror

//            lifecycleScope.launch{
//                loadData(inputUsername.getEditText()?.getText().toString())
//            }

            if(username.isEmpty()){
                inputUsername.setError("Username Must Be Filled With Text")
                checkLogin = false
            }

            if(password.isEmpty()){
               inputPassword.setError("Password Must Be Filled With Text")
                checkLogin = false
            }

            if(username == "admin" && password == "admin"){
                checkLogin = true
                var strUser: String = usrnm
                var strPw: String = pwd
                val editor: SharedPreferences.Editor = sharedPreferences!!.edit()
                editor.putString(userK, strUser)
                editor.putString(passK, strPw)
                editor.apply()
                sendNotification2()
            }
            else if(username == usrnm && password == pwd){
                checkLogin = true
                var strUser: String = usrnm
                var strPw: String = pwd
                val editor: SharedPreferences.Editor = sharedPreferences!!.edit()
                editor.putString(userK, strUser)
                editor.putString(passK, strPw)
                editor.apply()
                sendNotification2()
            }
            else if(username != usrnm || password != pwd){
                checkLogin = false
                val builder: AlertDialog.Builder = AlertDialog.Builder(this@LoginActivity)
                builder.setTitle("Username atau Password Salah")
                builder.setMessage("Isi dengan benar!")
                    .setPositiveButton("Yes"){ dialog, which ->
                    }
                    .show()
            }
            if(!checkLogin) return@OnClickListener
            val moveHome = Intent(this@LoginActivity, HomeActivity::class.java)
            startActivity(moveHome)


        })

        btnHaventAccount.setOnClickListener {
            val moveRegist = Intent(this,RegisterActivity::class.java)
            startActivity(moveRegist)
        }
    }

    fun getBundle(){

        //preference
        sharedPreferences = getSharedPreferences(myPreference, Context.MODE_PRIVATE)
        if (sharedPreferences!!.contains(userK)){
            inputUsername.getEditText()?.setText(sharedPreferences!!.getString(userK, ""))
        }
        if (sharedPreferences!!.contains(passK)){
            inputPassword.getEditText()?.setText(sharedPreferences!!.getString(passK, ""))
        }
    }

//    override fun onStart() {
//        super.onStart()
//        loadData()
//    }
    //untuk load data yang tersimpan pada database yang sudah create
    //data
    fun loadData(str : String) {
        CoroutineScope(Dispatchers.Main).launch {
            val users = db.userDao().getUser(str)[0]
            usrnm = users.username
            pwd = users.password
//            Log.d("LoginActivity","dbResponse: $users")
//            withContext(Dispatchers.Main){
//                registerAdapter.setData( users )
            }
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

    private fun sendNotification2() {

        val builder = NotificationCompat.Builder(this, CHANNEL_ID_1)
            .setSmallIcon(R.drawable.ic_baseline_looks_two_24)
            .setContentTitle("Selamat Login" + inputUsername.getEditText()?.getText().toString())
            .setContentText("Silahkan pilih agenda apa yang ingin anda lakukan")
            .setPriority(NotificationCompat.PRIORITY_LOW)

        with(NotificationManagerCompat.from(this)){
            notify(notificationId2,builder.build())
        }
    }


}