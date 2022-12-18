package com.example.komoke

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import com.example.komoke.MainActivity
import com.example.komoke.maps.MapsActivity
import kotlinx.android.synthetic.main.fragment_home.*
import kotlinx.android.synthetic.main.fragment_home.*
import com.example.komoke.HomeActivity
import com.example.komoke.event.EventActivity
import com.example.komoke.pesawat.PDFActivity
import com.example.komoke.pesawat.PesawatActivity
import com.example.komoke.scantiket.ScanActivity
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.viewbinding.ViewBinding
import androidx.viewpager.widget.ViewPager
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.example.komoke.adapter.SliderAdapter
import com.example.komoke.bus.BusActivity
import com.example.komoke.databinding.FragmentHomeBinding
import com.example.komoke.kereta.KeretaActivity


class HomeFragment : Fragment() {

    lateinit var vpSlider : ViewPager

    private var binding : FragmentHomeBinding? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_home, container, false)
        //<-- Slider -->
        vpSlider = view.findViewById(R.id.vp_slider)

        val arraySlider = ArrayList<Int>()

        arraySlider.add(R.drawable.carosel1)
        arraySlider.add(R.drawable.carosel2)
        arraySlider.add(R.drawable.carosel3)
        arraySlider.add(R.drawable.carosel4)
        arraySlider.add(R.drawable.carosel5)
        arraySlider.add(R.drawable.carosel6)

        val sliderAdapter = SliderAdapter(arraySlider, activity)
        vpSlider.adapter = sliderAdapter

        return view


    }
//    override fun onDestroyView() {
//        super.onDestroyView()
//        binding = null
//    }
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

        btnScan.setOnClickListener() {
            startActivity(
                Intent(requireActivity().applicationContext, ScanActivity::class.java)
            )
        }

        btnKereta.setOnClickListener(){
            startActivity(
                Intent(requireActivity().applicationContext, KeretaActivity::class.java)
            )
//            val moveEvent = Intent(activity, EventActivity::class.java)
//            activity?.startActivity(moveEvent)
        }

        btnBus.setOnClickListener(){
            startActivity(
                Intent(requireActivity().applicationContext, BusActivity::class.java)
            )
//            val moveEvent = Intent(activity, EventActivity::class.java)
//            activity?.startActivity(moveEvent)
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