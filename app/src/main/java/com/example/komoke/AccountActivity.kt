package com.example.komoke

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.example.komoke.roomUser.UserDB
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class AccountActivity : AppCompatActivity() {
    val db by lazy { UserDB(this) }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_account)

        CoroutineScope(Dispatchers.IO).launch {
            val users = db.userDao().getUser()
            Log.d("LoginActivity","dbResponse: $users")
            withContext(Dispatchers.Main){


            }
        }
    }


}