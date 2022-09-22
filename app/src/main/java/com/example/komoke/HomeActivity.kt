package com.example.komoke

import android.app.DatePickerDialog
import android.content.Intent
import android.icu.util.Calendar
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import androidx.fragment.app.Fragment
import com.example.komoke.OrderFragment
import com.example.komoke.AccountFragment
import com.google.android.material.bottomnavigation.BottomNavigationView

class HomeActivity : AppCompatActivity() {

        lateinit var bottomNav : BottomNavigationView

        override fun onCreate(savedInstanceState: Bundle?) {
            super.onCreate(savedInstanceState)
            setContentView(R.layout.activity_home)

            loadFragment(HomeFragment())
            //changeFragment(HomeFragment())
            bottomNav = findViewById(R.id.nav_view) as BottomNavigationView
            bottomNav.setOnNavigationItemReselectedListener {
                when (it.itemId) {
                    R.id.navigation_home -> {
                        loadFragment(HomeFragment())
                        return@setOnNavigationItemReselectedListener
                    }
                    R.id.navigation_order -> {
                        loadFragment(OrderFragment())
                        return@setOnNavigationItemReselectedListener
                    }
                    R.id.navigation_account -> {
                        loadFragment(AccountFragment())
                        return@setOnNavigationItemReselectedListener
                    }
                }
            }
        }

        private fun loadFragment(fragment: Fragment){
            val transaction = supportFragmentManager.beginTransaction()
            transaction.replace(R.id.container,fragment)
            transaction.addToBackStack(null)
            transaction.commit()
        }


}