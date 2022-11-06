package com.example.komoke

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.komoke.MainActivity
import com.example.komoke.maps.MapsActivity
import kotlinx.android.synthetic.main.fragment_home.*
import com.example.komoke.HomeActivity

class HomeFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_home, container, false)

//        btnHotel.setOnClickListener(){
//            val moveMaps = Intent(context, MapsActivity::class.java)
//            startActivity(moveMaps)
//        }
    }

//    override fun onCreate(savedInstanceState: Bundle?) {
//        super.onCreate(savedInstanceState)
//
//        btnHotel.setOnClickListener(){
//            val moveMaps = Intent(context, MapsActivity::class.java)
//            startActivity(moveMaps)
//        }
//    }


}