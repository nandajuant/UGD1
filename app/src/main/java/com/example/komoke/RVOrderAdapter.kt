package com.example.komoke

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.komoke.entity.ItemOrder

class RVOrderAdapter (private val data: Array<ItemOrder>) : RecyclerView.Adapter<RVOrderAdapter.viewHolder>(){

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): viewHolder {
        //Disini kita menghubungkan layout item recycler view kita
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.rv_item_order, parent, false)
        return viewHolder(itemView)
    }

    override fun onBindViewHolder(holder: viewHolder, position: Int) {
        //Karena kita sudha mendefinisikan dan menghubungkan view kita
        //Kita bisa memakai view tersebut dan melakukan set text view tersebut
        val currentItem = data[position]
        holder.tvItem.text = currentItem.tujuan
        holder.tvID.text = currentItem.id
        holder.tvNama.text = currentItem.event
        holder.tvJumlah.text = currentItem.tanggal
        holder.tvTanggal.text = currentItem.detail
    }

    override fun getItemCount(): Int {
        //Disini kita mmeberitahu jumlah dari item pada recycler view kita
        return data.size
    }

    //Kelas ini berguna unutk menghubungkan view yang ada pada item di recycler view kita
    class viewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val tvItem: TextView = itemView.findViewById(R.id.tv_item)
        val tvID: TextView = itemView.findViewById(R.id.tv_id)
        val tvNama: TextView = itemView.findViewById(R.id.tv_judul)
        val tvJumlah: TextView = itemView.findViewById(R.id.tv_jumlah)
        val tvTanggal: TextView = itemView.findViewById(R.id.tv_tanggal)
        val tvDetail: TextView = itemView.findViewById(R.id.tv_detail)
    }
}