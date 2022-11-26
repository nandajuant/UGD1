package com.example.komoke

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.komoke.MainActivity
import com.example.komoke.entity.ItemOrder

class OrderFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_order, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val layoutManager = LinearLayoutManager(context)
        val adapter: RVOrderAdapter = RVOrderAdapter(ItemOrder.listOfOrder)

        val rvOrder: RecyclerView = view.findViewById(R.id.rv_order)

        rvOrder.layoutManager = layoutManager

        rvOrder.setHasFixedSize(true)

        rvOrder.adapter = adapter
    }

}