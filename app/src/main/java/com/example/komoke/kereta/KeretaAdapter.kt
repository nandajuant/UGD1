package com.example.komoke.kereta

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Filter
import android.widget.Filterable
import android.widget.ImageButton
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import com.example.komoke.R
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import java.util.*
import kotlin.collections.ArrayList

class KeretaAdapter (private var keretaList: List<KeretaModel>, context: Context) :
    RecyclerView.Adapter<KeretaAdapter.ViewHolder>() , Filterable {

    private var filteredKeretaList : MutableList<KeretaModel>
    private val context : Context

    init {
        filteredKeretaList = ArrayList(keretaList)
        this.context = context
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val view = inflater.inflate(R.layout.item_kereta, parent, false)

        return ViewHolder(view)
    }

    override fun getItemCount(): Int {
        return  filteredKeretaList.size
    }

    fun setKeretaList(keretaList: Array<KeretaModel>) {
        this.keretaList = keretaList.toList()
        filteredKeretaList = keretaList.toMutableList()
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val kereta = filteredKeretaList[position]
        holder.tvNama.text = kereta.nama
        holder.tvKeberangkatan.text = kereta.keberangkatan
        holder.tvTujuan.text = kereta.tujuan
        holder.tvTanggal.text = kereta.tanggal

        holder.btnDelete.setOnClickListener {
            val materialAlertDialogBuilder = MaterialAlertDialogBuilder(context)
            materialAlertDialogBuilder.setTitle("Konfirmasi")
                .setMessage("Apakah anda yakin ingin menghapus data kereta ini?")
                .setNegativeButton("batal", null)
                .setPositiveButton("Hapus") { _, _ ->
                    if (context is KeretaActivity) kereta.id?.let { it1 ->
                        context.deleteKereta(
                            it1
                        )
                    }
                }
                .show()
        }
        holder.cvKereta.setOnClickListener {
            val i = Intent(context, AddEditKereta::class.java)
            i.putExtra("id", kereta.id)
            if (context is KeretaActivity)
                context.startActivityForResult(i, KeretaActivity.LAUNCH_ADD_ACTIVITY)
        }
    }

    override fun getFilter(): Filter {
        return object : Filter() {
            override fun performFiltering(charSequence : CharSequence): FilterResults {
                val charSequenceString = charSequence.toString()
                val filtered: MutableList<KeretaModel> = java.util.ArrayList()
                if (charSequenceString.isEmpty()) {
                    filtered.addAll(keretaList)
                } else {
                    for (kereta in keretaList) {
                        if (kereta.nama.lowercase(Locale.getDefault())
                                .contains(charSequenceString.lowercase(Locale.getDefault()))
                        ) filtered.add (kereta)
                    }
                }
                val filterResults = FilterResults()
                filterResults.values = filtered
                return filterResults
            }

            override fun publishResults(charSequence: CharSequence, filterResults: FilterResults) {
                filteredKeretaList.clear()
                filteredKeretaList.addAll((filterResults.values as List<KeretaModel>))
                notifyDataSetChanged()
            }
        }
    }

    inner class  ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var tvNama : TextView
        var tvKeberangkatan : TextView
        var tvTujuan : TextView
        var tvTanggal : TextView
        var btnDelete : ImageButton
        var cvKereta : CardView

        init {
            tvNama = itemView.findViewById(R.id.tv_nama)
            tvKeberangkatan = itemView.findViewById(R.id.tv_keberangkatan)
            tvTujuan = itemView.findViewById(R.id.tv_tujuan)
            tvTanggal = itemView.findViewById(R.id.tv_tanggal)
            btnDelete = itemView.findViewById(R.id.btn_delete)
            cvKereta = itemView.findViewById(R.id.cv_kereta)
        }

    }

}