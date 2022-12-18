package com.example.komoke

import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.graphics.BitmapFactory
import android.graphics.Color
import android.os.Build
import android.os.Bundle
import android.view.View
import android.widget.*
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import com.android.volley.AuthFailureError
import com.android.volley.RequestQueue
import com.android.volley.Response
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.example.komoke.databinding.ActivityMainBinding
import com.example.komoke.event.EventModel
import com.example.komoke.register.AccountApi
import com.example.komoke.register.AccountModel
import com.example.komoke.roomUser.UserDB
import com.google.android.material.textfield.TextInputLayout
import com.google.gson.Gson
import kotlinx.android.synthetic.main.activity_login.*
import kotlinx.android.synthetic.main.activity_login.view.*
import kotlinx.android.synthetic.main.activity_register.view.*
import kotlinx.coroutines.*
import org.json.JSONObject
import java.lang.Exception
import java.nio.charset.StandardCharsets
import java.util.*


class LoginActivity : AppCompatActivity() {

    val db by lazy { UserDB(this) }
//    lateinit var db: UserDB
    lateinit var registerAdapter: RegisterAdapter

    private var queue: RequestQueue? = null
    private var inputUsername: EditText? = null
    private var inputPassword: EditText? = null
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
//        inputUsername = findViewById(R.id.til_emailLogin)
//        inputPassword = findViewById(R.id.til_passwordLogin)

        queue = Volley.newRequestQueue(this)

        inputUsername = findViewById(R.id.et_emailLogin)
        inputPassword = findViewById(R.id.et_passwordLogin)

        mainLayout = findViewById(R.id.mainLayout)
        val btnLogin: Button = findViewById(R.id.btn_login)
        val btnHaventAccount: Button = findViewById(R.id.btn_havent_account)

        getBundle()
        usrnm = ""
        pwd = ""

        btnLogin.setOnClickListener { loginAccount() }
        btnHaventAccount.setOnClickListener {
            val moveRegist = Intent(this,RegisterActivity::class.java)
            startActivity(moveRegist)
        }


    }

    private fun loginAccount(){
//            inputUsername = findViewById(R.id.et_email)
//            inputPassword = findViewById(R.id.et_password)
//            var checkLogin = false
//            val username: String = inputUsername.getEditText()?.getText().toString()
//            val password: String = inputPassword.getEditText()?.getText().toString()

            // ini kalo dibuka loginnya eror, pake default admin-admin juga eror

//            lifecycleScope.launch{
//                loadData(inputUsername.getEditText()?.getText().toString())
//            }

            if (inputUsername!!.text.toString().isEmpty()) {
                Toast.makeText(
                    this@LoginActivity,
                    "Username tidak boleh kosong!",
                    Toast.LENGTH_SHORT
                ).show()
            } else if (inputPassword!!.text.toString().isEmpty()) {
                Toast.makeText(
                    this@LoginActivity,
                    "Password tidak boleh kosong!",
                    Toast.LENGTH_SHORT
                ).show()
            } else {
//                val id = intent.getLongExtra("id", -1)
//                getAccountById(id)

                if (inputUsername!!.text.toString() == "admin" && inputPassword!!.text.toString() == "admin") {
//                    checkLogin = true
                    var strUser: String = usrnm
                    var strPw: String = pwd
                    val editor: SharedPreferences.Editor = sharedPreferences!!.edit()
                    editor.putString(userK, strUser)
                    editor.putString(passK, strPw)
                    editor.apply()
                    sendNotification2()

                    val moveHome = Intent(this@LoginActivity, HomeActivity::class.java)
                    startActivity(moveHome)


                } else if (inputUsername!!.text.toString() == usrnm && inputPassword!!.text.toString() == pwd) {
//                    checkLogin = true
                    var strUser: String = usrnm
                    var strPw: String = pwd
                    val editor: SharedPreferences.Editor = sharedPreferences!!.edit()
                    editor.putString(userK, strUser)
                    editor.putString(passK, strPw)
                    editor.apply()
                    sendNotification2()

                    val moveHome = Intent(this@LoginActivity, HomeActivity::class.java)
                    startActivity(moveHome)

                } else if (inputUsername!!.text.toString() != usrnm && inputPassword!!.text.toString() != pwd) {
//                    checkLogin = false
                    val builder: AlertDialog.Builder = AlertDialog.Builder(this@LoginActivity)
                    builder.setTitle("Username atau Password Salah")
                    builder.setMessage("Isi dengan benar!")
                        .setPositiveButton("Yes") { dialog, which ->
                        }
                        .show()
                }

            }




    }

//    override fun onQueryTextSubmit(s: String?): Boolean {
//        filter.filter(s)
//        return false
//    }
//
//    override fun getFilter(): Filter {
//        return object : Filter() {
//            override fun performFiltering(charSequence : CharSequence): FilterResults {
//                val charSequenceString = charSequence.toString()
//                val filtered: MutableList<AccountModel> = java.util.ArrayList()
//                if (charSequenceString.isEmpty()) {
//                    filtered.addAll(accountList)
//                } else {
//                    for (account in accountList) {
//                        if (account.nama.lowercase(Locale.getDefault())
//                                .contains(charSequenceString.lowercase(Locale.getDefault()))
//                        ) filtered.add (account)
//                    }
//                }
//                val filterResults = FilterResults()
//                filterResults.values = filtered
//                return filterResults
//            }
//
//            override fun publishResults(charSequence: CharSequence, filterResults: FilterResults) {
//                filteredAccountList.clear()
//                filteredAccountList.addAll((filterResults.values as List<AccountModel>))
//                notifyDataSetChanged()
//            }
//        }
//    }



//    private fun getAccountById(id: Long) {
////        setLoading(true)
//        val stringRequest: StringRequest = object :
//            StringRequest(
//                Method.GET,
//                AccountApi.GET_BY_ID_URL + id,
//                Response.Listener { response ->
//                    val gson = Gson()
//                    val event = gson.fromJson(response, AccountModel::class.java)
//
//                    Toast.makeText(
//                        this@LoginActivity,
//                        "Data berhasil diambil!!",
//                        Toast.LENGTH_SHORT
//                    ).show()
////                    setLoading(false)
//                },
//                Response.ErrorListener { error ->
////                    setLoading(false)
//
//                    try {
//                        val responseBody =
//                            String(error.networkResponse.data, StandardCharsets.UTF_8)
//                        val errors = JSONObject(responseBody)
//                        Toast.makeText(
//                            this@LoginActivity,
//                            errors.getString("message"),
//                            Toast.LENGTH_SHORT
//                        ).show()
//                    } catch (e: Exception) {
//                        Toast.makeText(this@LoginActivity, e.message, Toast.LENGTH_SHORT).show()
//                    }
//                }) {
//            @Throws(AuthFailureError::class)
//            override fun getHeaders(): Map<String, String> {
//                val headers = HashMap<String, String>()
//                headers["Accept"] = "application/json"
//                return headers
//            }
//        }
//        queue!!.add(stringRequest)
//    }


    fun getBundle(){

        //preference
        sharedPreferences = getSharedPreferences(myPreference, Context.MODE_PRIVATE)
        if (sharedPreferences!!.contains(userK)){
            inputPassword!!.setText(sharedPreferences!!.getString(userK, ""))
        }
        if (sharedPreferences!!.contains(passK)){
            inputPassword!!.setText(sharedPreferences!!.getString(passK, ""))
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
            val channel1 = NotificationChannel(CHANNEL_ID_1,name, NotificationManager.IMPORTANCE_HIGH).apply {
                description = descriptionText
            }
            val channel2 = NotificationChannel(CHANNEL_ID_2,name, NotificationManager.IMPORTANCE_HIGH).apply {
                description = descriptionText
            }
            val notificationManager : NotificationManager =
                getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
            notificationManager.createNotificationChannel(channel1)
            notificationManager.createNotificationChannel(channel2)
        }
    }

    private fun sendNotification2() {
        val largeIcon = BitmapFactory.decodeResource(resources, R.mipmap.ic_launcher_round)
        val pendingIntent: PendingIntent = PendingIntent.getActivity(this, 0,intent, PendingIntent.FLAG_MUTABLE)
        val builder = NotificationCompat.Builder(this, CHANNEL_ID_1)
            .setSmallIcon(R.drawable.ic_message)
            .setContentTitle("Welcome To Komoke: " + inputUsername!!.text.toString())
            .setContentText("You Have A New Message")
            .setLargeIcon(largeIcon)
            .setStyle(NotificationCompat.BigTextStyle()
                .bigText(getString(R.string.long_dummy_text))
                .setBigContentTitle("From : Komoke")
                .setSummaryText("Login Message"))
            .setColor(Color.CYAN)
            .setContentIntent(pendingIntent)
            .setAutoCancel(true)
            .setOnlyAlertOnce(true)
            .setPriority(NotificationCompat.PRIORITY_HIGH)

        with(NotificationManagerCompat.from(this)){
            notify(notificationId2,builder.build())
        }
    }


}