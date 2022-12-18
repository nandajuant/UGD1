package com.example.komoke.kereta

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
import com.example.komoke.kereta.KeretaApi
import com.example.komoke.kereta.KeretaModel
import com.google.gson.Gson
import org.json.JSONObject
import java.lang.Exception
import java.nio.charset.StandardCharsets

class AddEditKereta : AppCompatActivity() {
    private var etNama: EditText? = null
    private var etKeberangkatan: EditText? = null
    private var etTujuan: EditText? = null
    private var etTanggal: EditText? = null
    private var layoutLoading: LinearLayout? = null
    private var queue: RequestQueue? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_edit_kereta)

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
            tvTitle.setText("Tambah Kereta")
            btnSave.setOnClickListener { createKereta() }
        } else {
            tvTitle.setText("Edit Kereta")
            getKeretaById(id)

            btnSave.setOnClickListener { updateKereta(id) }
        }
    }

    private fun getKeretaById(id: Long) {
        setLoading(true)
        val stringRequest: StringRequest = object :
            StringRequest(
                Method.GET,
                KeretaApi.GET_BY_ID_URL + id,
                Response.Listener { response ->
                    val gson = Gson()
                    val kereta = gson.fromJson(response, KeretaModel::class.java)

                    etNama!!.setText(kereta.nama)
                    etKeberangkatan!!.setText(kereta.keberangkatan)
                    etKeberangkatan!!.setText(kereta.tujuan)
                    etTanggal!!.setText(kereta.tanggal)

                    Toast.makeText(
                        this@AddEditKereta,
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
                            this@AddEditKereta,
                            errors.getString("message"),
                            Toast.LENGTH_SHORT
                        ).show()
                    } catch (e: Exception) {
                        Toast.makeText(this@AddEditKereta, e.message, Toast.LENGTH_SHORT).show()
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

    private fun createKereta() {
        setLoading(true)

        if(etNama!!.text.toString().isEmpty()){
            Toast.makeText(this@AddEditKereta, "Nama tidak boleh kosong!", Toast.LENGTH_SHORT).show()
        }
        else if(etKeberangkatan!!.text.toString().isEmpty()){
            Toast.makeText(this@AddEditKereta, "Stasiun Keberangkatan tidak boleh kosong!", Toast.LENGTH_SHORT).show()
        }
        else if(etTujuan!!.text.toString().isEmpty()){
            Toast.makeText(this@AddEditKereta, "Stasiun Tujuan tidak boleh kosong!", Toast.LENGTH_SHORT).show()
        }
        else if(etTanggal!!.text.toString().isEmpty()){
            Toast.makeText(this@AddEditKereta, "Tanggal tidak boleh kosong!", Toast.LENGTH_SHORT).show()
        }
        else{

            val kereta = KeretaModel(
                etNama!!.text.toString(),
                etKeberangkatan!!.text.toString(),
                etTujuan!!.text.toString(),
                etTanggal!!.text.toString()
            )

            val stringRequest: StringRequest =
                object :
                    StringRequest(Method.POST, KeretaApi.ADD_URL, Response.Listener { response ->
                        val gson = Gson()
                        var kereta = gson.fromJson(response, KeretaModel::class.java)

                        if (kereta != null)
                            Toast.makeText(
                                this@AddEditKereta, "Data berhasil ditambahkan", Toast.LENGTH_SHORT
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
                                this@AddEditKereta,
                                errors.getString("message"),
                                Toast.LENGTH_SHORT
                            ).show()
                        } catch (e: Exception) {
                            Toast.makeText(this@AddEditKereta, e.message, Toast.LENGTH_SHORT).show()
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
                        val requestBody = gson.toJson(kereta)
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


    private fun updateKereta(id: Long) {
        setLoading(true)

        val kereta = KeretaModel(
            etNama!!.text.toString(),
            etKeberangkatan!!.text.toString(),
            etTujuan!!.text.toString(),
            etTanggal!!.text.toString()
        )

        val stringRequest: StringRequest = object :
            StringRequest(Method.PUT, KeretaApi.UPDATE_URL + id, Response.Listener { response ->
                val gson = Gson()

                var kereta = gson.fromJson(response, KeretaModel::class.java)

                if (kereta != null)
                    Toast.makeText(
                        this@AddEditKereta,
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
                        this@AddEditKereta,
                        errors.getString("message"),
                        Toast.LENGTH_SHORT
                    ).show()
                } catch (e: Exception) {
                    Toast.makeText(this@AddEditKereta, e.message, Toast.LENGTH_SHORT).show()
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
                val requestBody = gson.toJson(kereta)
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