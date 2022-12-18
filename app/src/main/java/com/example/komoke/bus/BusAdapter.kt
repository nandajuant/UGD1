package com.example.komoke.bus

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
import com.example.komoke.bus.AddEditBus
import com.example.komoke.bus.BusActivity
import com.example.komoke.bus.BusAdapter
import com.example.komoke.bus.BusModel
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import java.util.*
import kotlin.collections.ArrayList

class BusAdapter (private var busList: List<BusModel>, context: Context) :
    RecyclerView.Adapter<BusAdapter.ViewHolder>() , Filterable {

    private var filteredBusList : MutableList<BusModel>
    private val context : Context

    init {
        filteredBusList = ArrayList(busList)
        this.context = context
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val view = inflater.inflate(R.layout.item_bus, parent, false)

        return ViewHolder(view)
    }

    override fun getItemCount(): Int {
        return  filteredBusList.size
    }

    fun setBusList(busList: Array<BusModel>) {
        this.busList = busList.toList()
        filteredBusList = busList.toMutableList()
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val bus = filteredBusList[position]
        holder.tvNama.text = bus.nama
        holder.tvKeberangkatan.text = bus.keberangkatan
        holder.tvTujuan.text = bus.tujuan
        holder.tvTanggal.text = bus.tanggal

        holder.btnDelete.setOnClickListener {
            val materialAlertDialogBuilder = MaterialAlertDialogBuilder(context)
            materialAlertDialogBuilder.setTitle("Konfirmasi")
                .setMessage("Apakah anda yakin ingin menghapus data bus ini?")
                .setNegativeButton("batal", null)
                .setPositiveButton("Hapus") { _, _ ->
                    if (context is BusActivity) bus.id?.let { it1 ->
                        context.deleteBus(
                            it1
                        )
                    }
                }
                .show()
        }
        holder.cvBus.setOnClickListener {
            val i = Intent(context, AddEditBus::class.java)
            i.putExtra("id", bus.id)
            if (context is BusActivity)
                context.startActivityForResult(i, BusActivity.LAUNCH_ADD_ACTIVITY)
        }
    }

    override fun getFilter(): Filter {
        return object : Filter() {
            override fun performFiltering(charSequence : CharSequence): FilterResults {
                val charSequenceString = charSequence.toString()
                val filtered: MutableList<BusModel> = java.util.ArrayList()
                if (charSequenceString.isEmpty()) {
                    filtered.addAll(busList)
                } else {
                    for (bus in busList) {
                        if (bus.nama.lowercase(Locale.getDefault())
                                .contains(charSequenceString.lowercase(Locale.getDefault()))
                        ) filtered.add (bus)
                    }
                }
                val filterResults = FilterResults()
                filterResults.values = filtered
                return filterResults
            }

            override fun publishResults(charSequence: CharSequence, filterResults: FilterResults) {
                filteredBusList.clear()
                filteredBusList.addAll((filterResults.values as List<BusModel>))
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
        var cvBus : CardView

        init {
            tvNama = itemView.findViewById(R.id.tv_nama)
            tvKeberangkatan = itemView.findViewById(R.id.tv_keberangkatan)
            tvTujuan = itemView.findViewById(R.id.tv_tujuan)
            tvTanggal = itemView.findViewById(R.id.tv_tanggal)
            btnDelete = itemView.findViewById(R.id.btn_delete)
            cvBus = itemView.findViewById(R.id.cv_bus)
        }

    }

}