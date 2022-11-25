package com.example.komoke

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import com.example.komoke.MainActivity
import com.example.komoke.maps.MapsActivity
import kotlinx.android.synthetic.main.fragment_home.*
import kotlinx.android.synthetic.main.fragment_home.*
import com.example.komoke.HomeActivity
import com.example.komoke.event.EventActivity
import com.example.komoke.pesawat.PDFActivity
import com.example.komoke.pesawat.PesawatActivity

class HomeFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_home, container, false)

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupListener()



//        btnPesawat.setOnClickListener(){
//            requireActivity().run{
//                startActivity(Intent(this, PesawatActivity::class.java))
//                finish()
//            }
//        }

//        btnPesawat.setOnClickListener() {
//            activity?.let {
//                val intent = Intent(it, PesawatActivity::class.java)
//                it.startActivity(intent)
//            }
//        }



    }

    private fun setupListener(){
        btnPesawat.setOnClickListener() {
            startActivity(
                Intent(requireActivity().applicationContext, PDFActivity::class.java)
            )
        }

        btnEvent.setOnClickListener(){
            startActivity(
                Intent(requireActivity().applicationContext, EventActivity::class.java)
            )
//            val moveEvent = Intent(activity, EventActivity::class.java)
//            activity?.startActivity(moveEvent)
        }

        btnHotel.setOnClickListener(){
            startActivity(
                Intent(requireActivity().applicationContext, MapsActivity::class.java)
            )
//            val moveMaps =  Intent(context, MapsActivity::class.java)
//            startActivity(moveMaps)
        }
    }



//    override fun onCreate(savedInstanceState: Bundle?) {
//        super.onCreate(savedInstanceState)
//
//        btnHotel.setOnClickListener(){
//            val moveMaps = Intent(context, MapsActivity::class.java)
//            startActivity(moveMaps)
//        }
//    }

    // pembuat mengalami kendala di HomeActivity dan HomeFragment

}