package com.example.komoke.bus

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.view.WindowManager
import android.widget.*
import com.android.volley.AuthFailureError
import com.android.volley.RequestQueue
import com.android.volley.Response
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.example.komoke.R
import com.example.komoke.bus.BusApi
import com.example.komoke.bus.BusModel
import com.google.gson.Gson
import org.json.JSONObject
import java.lang.Exception
import java.nio.charset.StandardCharsets

class AddEditBus : AppCompatActivity() {
    private var etNama: EditText? = null
    private var etKeberangkatan: EditText? = null
    private var etTujuan: EditText? = null
    private var etTanggal: EditText? = null
    private var layoutLoading: LinearLayout? = null
    private var queue: RequestQueue? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_edit_bus)

        queue = Volley.newRequestQueue(this)
        etNama = findViewById(R.id.et_nama)
        etKeberangkatan = findViewById(R.id.et_keberangkatan)
        etTujuan = findViewById(R.id.et_tujuan)
        etTanggal = findViewById(R.id.et_tanggal)
        layoutLoading = findViewById(R.id.layout_loading)

        val btnCancel = findViewById<Button>(R.id.btn_cancel)
        btnCancel.setOnClickListener { finish() }
        val btnSave = findViewById<Button>(R.id.btn_save)
        val tvTitle = findViewById<TextView>(R.id.tv_title)
        val id = intent.getLongExtra("id", -1)
        if (id == -1L) {
            tvTitle.setText("Tambah Bus")
            btnSave.setOnClickListener { createBus() }
        } else {
            tvTitle.setText("Edit Bus")
            getBusById(id)

            btnSave.setOnClickListener { updateBus(id) }
        }
    }

    private fun getBusById(id: Long) {
        setLoading(true)
        val stringRequest: StringRequest = object :
            StringRequest(
                Method.GET,
                BusApi.GET_BY_ID_URL + id,
                Response.Listener { response ->
                    val gson = Gson()
                    val bus = gson.fromJson(response, BusModel::class.java)

                    etNama!!.setText(bus.nama)
                    etKeberangkatan!!.setText(bus.keberangkatan)
                    etKeberangkatan!!.setText(bus.tujuan)
                    etTanggal!!.setText(bus.tanggal)

                    Toast.makeText(
                        this@AddEditBus,
                        "Data berhasil diambil!!",
                        Toast.LENGTH_SHORT
                    ).show()
                    setLoading(false)
                },
                Response.ErrorListener { error ->
                    setLoading(false)

                    try {
                        val responseBody =
                            String(error.networkResponse.data, StandardCharsets.UTF_8)
                        val errors = JSONObject(responseBody)
                        Toast.makeText(
                            this@AddEditBus,
                            errors.getString("message"),
                            Toast.LENGTH_SHORT
                        ).show()
                    } catch (e: Exception) {
                        Toast.makeText(this@AddEditBus, e.message, Toast.LENGTH_SHORT).show()
                    }
                }) {
            @Throws(AuthFailureError::class)
            override fun getHeaders(): Map<String, String> {
                val headers = HashMap<String, String>()
                headers["Accept"] = "application/json"
                return headers
            }
        }
        queue!!.add(stringRequest)
    }

    private fun createBus() {
        setLoading(true)

        if(etNama!!.text.toString().isEmpty()){
            Toast.makeText(this@AddEditBus, "Nama tidak boleh kosong!", Toast.LENGTH_SHORT).show()
        }
        else if(etKeberangkatan!!.text.toString().isEmpty()){
            Toast.makeText(this@AddEditBus, "Terminal Keberangkatan tidak boleh kosong!", Toast.LENGTH_SHORT).show()
        }
        else if(etTujuan!!.text.toString().isEmpty()){
            Toast.makeText(this@AddEditBus, "Terminal Tujuan tidak boleh kosong!", Toast.LENGTH_SHORT).show()
        }
        else if(etTanggal!!.text.toString().isEmpty()){
            Toast.makeText(this@AddEditBus, "Tanggal tidak boleh kosong!", Toast.LENGTH_SHORT).show()
        }
        else{

            val bus = BusModel(
                etNama!!.text.toString(),
                etKeberangkatan!!.text.toString(),
                etTujuan!!.text.toString(),
                etTanggal!!.text.toString()
            )

            val stringRequest: StringRequest =
                object :
                    StringRequest(Method.POST, BusApi.ADD_URL, Response.Listener { response ->
                        val gson = Gson()
                        var bus = gson.fromJson(response, BusModel::class.java)

                        if (bus != null)
                            Toast.makeText(
                                this@AddEditBus, "Data berhasil ditambahkan", Toast.LENGTH_SHORT
                            ).show()

                        val returnIntent = Intent()
                        setResult(RESULT_OK, returnIntent)
                        finish()

                        setLoading(false)
                    }, Response.ErrorListener { error ->
                        setLoading(false)
                        try {
                            val responseBody =
                                String(error.networkResponse.data, StandardCharsets.UTF_8)
                            val errors = JSONObject(responseBody)
                            Toast.makeText(
                                this@AddEditBus,
                                errors.getString("message"),
                                Toast.LENGTH_SHORT
                            ).show()
                        } catch (e: Exception) {
                            Toast.makeText(this@AddEditBus, e.message, Toast.LENGTH_SHORT).show()
                        }
                    }) {
                    @Throws(AuthFailureError::class)
                    override fun getHeaders(): Map<String, String> {
                        val headers = HashMap<String, String>()
                        headers["Accept"] = "aplication/json"
                        return headers
                    }

                    @Throws(AuthFailureError::class)
                    override fun getBody(): ByteArray {
                        val gson = Gson()
                        val requestBody = gson.toJson(bus)
                        return requestBody.toByteArray(StandardCharsets.UTF_8)
                    }

                    override fun getBodyContentType(): String {
                        return "application/json"
                    }
                }
            queue!!.add(stringRequest)

        }
        setLoading(false)
    }


    private fun updateBus(id: Long) {
        setLoading(true)

        val bus = BusModel(
            etNama!!.text.toString(),
            etKeberangkatan!!.text.toString(),
            etTujuan!!.text.toString(),
            etTanggal!!.text.toString()
        )

        val stringRequest: StringRequest = object :
            StringRequest(Method.PUT, BusApi.UPDATE_URL + id, Response.Listener { response ->
                val gson = Gson()

                var bus = gson.fromJson(response, BusModel::class.java)

                if (bus != null)
                    Toast.makeText(
                        this@AddEditBus,
                        "Data berhasil diupdate",
                        Toast.LENGTH_SHORT
                    ).show()

                val returnIntent = Intent()
                setResult(RESULT_OK, returnIntent)
                finish()

                setLoading(false)
            }, Response.ErrorListener { error ->
                setLoading(false)
                try {
                    val responseBody = String(error.networkResponse.data, StandardCharsets.UTF_8)
                    val errors = JSONObject(responseBody)
                    Toast.makeText(
                        this@AddEditBus,
                        errors.getString("message"),
                        Toast.LENGTH_SHORT
                    ).show()
                } catch (e: Exception) {
                    Toast.makeText(this@AddEditBus, e.message, Toast.LENGTH_SHORT).show()
                }
            }) {
            @Throws(AuthFailureError::class)
            override fun getHeaders(): Map<String, String> {
                val headers = HashMap<String, String>()
                headers["Accept"] = "application/json"
                return headers
            }

            @Throws(AuthFailureError::class)
            override fun getBody(): ByteArray {
                val gson = Gson()
                val requestBody = gson.toJson(bus)
                return requestBody.toByteArray(StandardCharsets.UTF_8)
            }

            override fun getBodyContentType(): String {
                return "application/json"
            }
        }
        queue!!.add(stringRequest)
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
            layoutLoading!!.visibility = View.INVISIBLE
        }
    }
}