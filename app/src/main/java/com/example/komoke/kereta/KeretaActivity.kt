package com.example.komoke.kereta

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
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.gson.Gson
import org.json.JSONObject
import java.lang.Exception
import java.nio.charset.StandardCharsets

class KeretaActivity : AppCompatActivity() {

    private var srKereta: SwipeRefreshLayout? = null
    private var adapter: KeretaAdapter? = null
    private var svKereta: SearchView? = null
    private var layoutLoading: LinearLayout? = null
    private var queue: RequestQueue? = null

    companion object {
        const val LAUNCH_ADD_ACTIVITY = 123
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_kereta)

        queue = Volley.newRequestQueue(this)
        layoutLoading = findViewById(R.id.layout_loading)
        srKereta =  findViewById(R.id.sr_kereta)
        svKereta = findViewById(R.id.sv_kereta)

        srKereta?.setOnRefreshListener( { allKereta() })
        svKereta?.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
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
            val  i = Intent( this@KeretaActivity, AddEditKereta::class.java)
            startActivityForResult(i, LAUNCH_ADD_ACTIVITY)
        }

        val rvProduk = findViewById<RecyclerView>(R.id.rv_kereta)
        adapter = KeretaAdapter(ArrayList(), this)
        rvProduk.layoutManager = LinearLayoutManager(this)
        rvProduk.adapter = adapter
        allKereta()
    }

    private fun allKereta() {
        srKereta!!.isRefreshing = true
        val stringRequest: StringRequest = object :
            StringRequest(Method.GET, KeretaApi.GET_ALL_URL, Response.Listener { response ->
                val gson = Gson()
                var kereta: Array<KeretaModel> =
                    gson.fromJson(response, Array<KeretaModel>::class.java)

                adapter!!.setKeretaList(kereta)
                adapter!!.filter.filter(svKereta!!.query)
                srKereta!!.isRefreshing = false

                if (kereta.isEmpty())
                    Toast.makeText(this@KeretaActivity, "Data Berhasil Diambil!!", Toast.LENGTH_SHORT)
                        .show()
                else
                    Toast.makeText(this@KeretaActivity, "Data Kosong", Toast.LENGTH_SHORT)
                        .show()
            }, Response.ErrorListener { error ->
                srKereta!!.isRefreshing = false
                try {
                    val responseBody =
                        String(error.networkResponse.data, StandardCharsets.UTF_8)
                    val errors = JSONObject(responseBody)
                    Toast.makeText(
                        this@KeretaActivity,
                        errors.getString("message"),
                        Toast.LENGTH_SHORT
                    ).show()
                } catch (e: Exception) {
                    Toast.makeText(this@KeretaActivity, e.message, Toast.LENGTH_SHORT).show()
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

    fun deleteKereta(id: Long) {
        setLoading(true)
        val stringRequest: StringRequest = object :
            StringRequest(Method.DELETE, KeretaApi.DELETE_URL + id, Response.Listener { response ->
                setLoading(false)

                val gson = Gson()
                var kereta = gson.fromJson(response, KeretaModel::class.java)
                if(kereta != null)
                    Toast.makeText(this@KeretaActivity, "Data Berhasil Dihapus", Toast.LENGTH_SHORT).show()
                allKereta()
            }, Response.ErrorListener { error ->
                setLoading(false)
                try{
                    val responseBody = String(error.networkResponse.data, StandardCharsets.UTF_8)
                    val errors = JSONObject(responseBody)
                    Toast.makeText(
                        this@KeretaActivity,
                        errors.getString("message"),
                        Toast.LENGTH_SHORT
                    ).show()
                } catch (e: java.lang.Exception) {
                    Toast.makeText(this@KeretaActivity, e.message, Toast.LENGTH_SHORT).show()
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
        if (requestCode == LAUNCH_ADD_ACTIVITY && resultCode == RESULT_OK) allKereta()
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