package com.example.komoke.bus

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.view.WindowManager
import android.widget.LinearLayout
import android.widget.SearchView
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.android.volley.AuthFailureError
import com.android.volley.RequestQueue
import com.android.volley.Response
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.example.komoke.R
import com.example.komoke.bus.AddEditBus
import com.example.komoke.bus.BusAdapter
import com.example.komoke.bus.BusApi
import com.example.komoke.bus.BusModel
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.gson.Gson
import org.json.JSONObject
import java.lang.Exception
import java.nio.charset.StandardCharsets

class BusActivity : AppCompatActivity() {
    private var srBus: SwipeRefreshLayout? = null
    private var adapter: BusAdapter? = null
    private var svBus: SearchView? = null
    private var layoutLoading: LinearLayout? = null
    private var queue: RequestQueue? = null

    companion object {
        const val LAUNCH_ADD_ACTIVITY = 123
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_bus)

        queue = Volley.newRequestQueue(this)
        layoutLoading = findViewById(R.id.layout_loading)
        srBus =  findViewById(R.id.sr_bus)
        svBus = findViewById(R.id.sv_bus)

        srBus?.setOnRefreshListener( { allBus() })
        svBus?.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextChange(p0: String?): Boolean {
                return false
            }

            override fun onQueryTextSubmit(s: String?): Boolean {
                adapter!!.filter.filter(s)
                return false
            }
        })

        val fabAdd = findViewById<FloatingActionButton>(R.id.fab_add)
        fabAdd.setOnClickListener {
            val  i = Intent( this@BusActivity, AddEditBus::class.java)
            startActivityForResult(i, LAUNCH_ADD_ACTIVITY)
        }

        val rvProduk = findViewById<RecyclerView>(R.id.rv_bus)
        adapter = BusAdapter(ArrayList(), this)
        rvProduk.layoutManager = LinearLayoutManager(this)
        rvProduk.adapter = adapter
        allBus()
    }

    private fun allBus() {
        srBus!!.isRefreshing = true
        val stringRequest: StringRequest = object :
            StringRequest(Method.GET, BusApi.GET_ALL_URL, Response.Listener { response ->
                val gson = Gson()
                var bus: Array<BusModel> =
                    gson.fromJson(response, Array<BusModel>::class.java)

                adapter!!.setBusList(bus)
                adapter!!.filter.filter(svBus!!.query)
                srBus!!.isRefreshing = false

                if (bus.isEmpty())
                    Toast.makeText(this@BusActivity, "Data Berhasil Diambil!!", Toast.LENGTH_SHORT)
                        .show()
                else
                    Toast.makeText(this@BusActivity, "Data Kosong", Toast.LENGTH_SHORT)
                        .show()
            }, Response.ErrorListener { error ->
                srBus!!.isRefreshing = false
                try {
                    val responseBody =
                        String(error.networkResponse.data, StandardCharsets.UTF_8)
                    val errors = JSONObject(responseBody)
                    Toast.makeText(
                        this@BusActivity,
                        errors.getString("message"),
                        Toast.LENGTH_SHORT
                    ).show()
                } catch (e: Exception) {
                    Toast.makeText(this@BusActivity, e.message, Toast.LENGTH_SHORT).show()
                }
            }) {
            @Throws(AuthFailureError::class)
            override fun getHeaders(): Map<String, String> {
                val headers = HashMap<String, String>()
                headers["Accept"] = "application/json"
                return  headers
            }
        }
        queue!!.add(stringRequest)
    }

    fun deleteBus(id: Long) {
        setLoading(true)
        val stringRequest: StringRequest = object :
            StringRequest(Method.DELETE, BusApi.DELETE_URL + id, Response.Listener { response ->
                setLoading(false)

                val gson = Gson()
                var bus = gson.fromJson(response, BusModel::class.java)
                if(bus != null)
                    Toast.makeText(this@BusActivity, "Data Berhasil Dihapus", Toast.LENGTH_SHORT).show()
                allBus()
            }, Response.ErrorListener { error ->
                setLoading(false)
                try{
                    val responseBody = String(error.networkResponse.data, StandardCharsets.UTF_8)
                    val errors = JSONObject(responseBody)
                    Toast.makeText(
                        this@BusActivity,
                        errors.getString("message"),
                        Toast.LENGTH_SHORT
                    ).show()
                } catch (e: java.lang.Exception) {
                    Toast.makeText(this@BusActivity, e.message, Toast.LENGTH_SHORT).show()
                }
            }) {
            @Throws(AuthFailureError::class)
            override fun getHeaders(): Map<String, String> {
                val headers = java.util.HashMap<String, String>()
                headers["Accept"] = "application/json"
                return headers
            }
        }
        queue!!.add(stringRequest)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == LAUNCH_ADD_ACTIVITY && resultCode == RESULT_OK) allBus()
    }

    private fun setLoading(isLoading: Boolean) {
        if (isLoading) {
            window.setFlags(
                WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE,
                WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE
            )
            layoutLoading!!.visibility = View.VISIBLE
        } else {
            window.clearFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE)
            layoutLoading!!.visibility = View.GONE
        }
    }
}