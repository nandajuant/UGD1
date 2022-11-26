package com.example.komoke.event


import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import  androidx.cardview.widget.CardView
import android.widget.Filter
import android.widget.Filterable
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.komoke.event.AddEditEvent
import com.example.komoke.event.EventActivity
import com.example.komoke.R
import com.example.komoke.event.EventModel
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import java.util.*
import kotlin.collections.ArrayList

class EventAdapter (private var eventList: List<EventModel>, context: Context) :
    RecyclerView.Adapter<EventAdapter.ViewHolder>() , Filterable {

    private var filteredEventList : MutableList<EventModel>
    private val context : Context

    init {
        filteredEventList = ArrayList(eventList)
        this.context = context
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val view = inflater.inflate(R.layout.item_event, parent, false)

        return ViewHolder(view)
    }

    override fun getItemCount(): Int {
        return  filteredEventList.size
    }

    fun setEventList(eventList: Array<EventModel>) {
        this.eventList = eventList.toList()
        filteredEventList = eventList.toMutableList()
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val event = filteredEventList[position]
        holder.tvNama.text = event.nama
        holder.tvTanggal.text = event.tanggal
        holder.tvidEvent.text = event.idEvent

        holder.btnDelete.setOnClickListener {
            val materialAlertDialogBuilder = MaterialAlertDialogBuilder(context)
            materialAlertDialogBuilder.setTitle("Konfirmasi")
                .setMessage("Apakah anda yakin ingin menghapus data event ini?")
                .setNegativeButton("batal", null)
                .setPositiveButton("Hapus") { _, _ ->
                    if (context is EventActivity) event.id?.let { it1 ->
                        context.deleteEvent(
                            it1
                        )
                    }
                }
                .show()
        }
        holder.cvEvent.setOnClickListener {
            val i = Intent(context, AddEditEvent::class.java)
            i.putExtra("id", event.id)
            if (context is EventActivity)
                context.startActivityForResult(i, EventActivity.LAUNCH_ADD_ACTIVITY)
        }
    }

    override fun getFilter(): Filter {
        return object : Filter() {
            override fun performFiltering(charSequence : CharSequence): FilterResults {
                val charSequenceString = charSequence.toString()
                val filtered: MutableList<EventModel> = java.util.ArrayList()
                if (charSequenceString.isEmpty()) {
                    filtered.addAll(eventList)
                } else {
                    for (event in eventList) {
                        if (event.nama.lowercase(Locale.getDefault())
                                .contains(charSequenceString.lowercase(Locale.getDefault()))
                        ) filtered.add (event)
                    }
                }
                val filterResults = FilterResults()
                filterResults.values = filtered
                return filterResults
            }

            override fun publishResults(charSequence: CharSequence, filterResults: FilterResults) {
                filteredEventList.clear()
                filteredEventList.addAll((filterResults.values as List<EventModel>))
                notifyDataSetChanged()
            }
        }
    }

    inner class  ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var tvNama : TextView
        var tvTanggal : TextView
        var tvidEvent : TextView
        var btnDelete : ImageButton
        var cvEvent : CardView

        init {
            tvNama = itemView.findViewById(R.id.tv_nama)
            tvTanggal = itemView.findViewById(R.id.tv_tanggal)
            tvidEvent = itemView.findViewById(R.id.tv_idEvent)
            btnDelete = itemView.findViewById(R.id.btn_delete)
            cvEvent = itemView.findViewById(R.id.cv_mahasiswa)
        }

    }

}