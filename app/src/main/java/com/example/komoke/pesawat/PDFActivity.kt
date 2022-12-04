package com.example.komoke.pesawat

import android.annotation.SuppressLint
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.drawable.BitmapDrawable
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Environment
import android.view.View
import android.widget.ArrayAdapter
import android.widget.AutoCompleteTextView
import android.widget.Toast
import androidx.annotation.RequiresApi
import com.android.volley.AuthFailureError
import com.android.volley.Response
import com.android.volley.toolbox.StringRequest
import com.example.komoke.R
import com.example.komoke.databinding.ActivityMainBinding
import com.example.komoke.databinding.ActivityPdfactivityBinding
import com.google.gson.Gson
import com.itextpdf.barcodes.BarcodeQRCode
import com.itextpdf.io.image.ImageDataFactory
import com.itextpdf.io.source.ByteArrayOutputStream
import com.itextpdf.kernel.colors.ColorConstants
import com.itextpdf.kernel.pdf.PdfDocument
import com.itextpdf.kernel.pdf.PdfWriter
import com.itextpdf.layout.Document
import com.itextpdf.layout.element.Cell
import com.itextpdf.layout.element.Image
import com.itextpdf.layout.element.Paragraph
import com.itextpdf.layout.element.Table
import com.itextpdf.layout.properties.HorizontalAlignment
import com.itextpdf.layout.properties.TextAlignment
import com.itextpdf.svg.converter.SvgConverter.createPdf
import com.lowagie.text.PageSize
import com.lowagie.text.PageSize.*
import kotlinx.android.synthetic.main.activity_add_edit.*
import kotlinx.android.synthetic.main.activity_pdfactivity.*
import org.json.JSONObject
import java.io.File
import java.io.FileNotFoundException
import java.io.FileOutputStream
import java.lang.Exception
import java.nio.charset.StandardCharsets
import java.time.LocalDate
import java.time.LocalTime
import java.time.format.DateTimeFormatter

class PDFActivity : AppCompatActivity() {
    private var binding: ActivityPdfactivityBinding? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityPdfactivityBinding.inflate(layoutInflater)
        val view: View = binding!!.root
        setContentView(view)


        binding!!.buttonSave.setOnClickListener {
            val pesawat = binding!!.etPesawat.text.toString()
            val berangkat = binding!!.etBerangkat.text.toString()
            val tujuan = binding!!.etTujuan.text.toString()
            val kelas = binding!!.etKelas.text.toString()
            val jumlah = binding!!.etJumlah.text.toString()

            try {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                    if (pesawat.isEmpty() && berangkat.isEmpty() && tujuan.isEmpty() && kelas.isEmpty() && jumlah.isEmpty()) {
                        Toast.makeText(
                            applicationContext,
                            "Data tidak boleh kosong",
                            Toast.LENGTH_SHORT
                        ).show()
                    } else {
                        createPdf(pesawat,berangkat, tujuan, kelas, jumlah)
                    }
                }
            } catch (e: FileNotFoundException) {
                e.printStackTrace()
            }
        }
    }


    @SuppressLint("ObsoleteSdkInt")
    @RequiresApi(api = Build.VERSION_CODES.O)
    @Throws(FileNotFoundException::class)
    private fun createPdf(pesawat: String, berangkat: String, tujuan: String, kelas: String, jumlah: String) {
        val pdfPath = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS).toString()
        val file = File(pdfPath, "pdf_10682.pdf")
        FileOutputStream(file)

        val writer = PdfWriter(file)
        val pdfDocument = PdfDocument(writer)
        val document = Document(pdfDocument)
        pdfDocument.defaultPageSize = com.itextpdf.kernel.geom.PageSize.A4
        document.setMargins(5f, 5f, 5f, 5f)
        @SuppressLint("UseCompatLoadingForDrawables") val d = getDrawable(R.drawable.pesawat)

        val bitmap = (d as BitmapDrawable)!!.bitmap
        val stream = ByteArrayOutputStream()
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, stream)
        val bitmapdata = stream.toByteArray()
        val imageData = ImageDataFactory.create(bitmapdata)
        val image = Image(imageData)
        val namapengguna = Paragraph("Identitas Pengguna").setBold().setFontSize(24f).setTextAlignment(
            TextAlignment.CENTER)
        val group = Paragraph (
            """
                Berikut adalah 
                Nama Pengguna UAJY 2022/2023
            """.trimIndent()).setTextAlignment(TextAlignment.CENTER).setFontSize(12f)

        val width = floatArrayOf(100f, 100f)
        val table = Table(width)

        table.setHorizontalAlignment(HorizontalAlignment.CENTER)
        table.addCell(Cell().add(Paragraph("Pesawat")))
        table.addCell(Cell().add(Paragraph(pesawat)))
        table.addCell(Cell().add(Paragraph("Keberangkatan")))
        table.addCell(Cell().add(Paragraph(berangkat)))
        table.addCell(Cell().add(Paragraph("Tujuan")))
        table.addCell(Cell().add(Paragraph(tujuan)))
        table.addCell(Cell().add(Paragraph("Kelas")))
        table.addCell(Cell().add(Paragraph(kelas)))
        table.addCell(Cell().add(Paragraph("Jumlah Tiket")))
        table.addCell(Cell().add(Paragraph(jumlah)))
        val dateTimeFormatter = DateTimeFormatter.ofPattern("dd/MM/yyyy")
        table.addCell(Cell().add(Paragraph("Tanggal Pembuatan PDF")))
        table.addCell(Cell().add(Paragraph(LocalDate.now().format(dateTimeFormatter))))
        val timeFormatter = DateTimeFormatter.ofPattern("HH:mm:ss a")
        table.addCell(Cell().add(Paragraph("Pukul Pembuatan PDF")))
        table.addCell(Cell().add(Paragraph(LocalTime.now().format(timeFormatter))))

        val barcodeQRCode = BarcodeQRCode("""
            Pesawat : $pesawat
            Keberangkatan : $berangkat
            Tujuan : $tujuan
            Kelas : $kelas
            Jumlah Tiket : $jumlah
            ${LocalDate.now().format(dateTimeFormatter)}
            ${LocalTime.now().format(timeFormatter)}
        """.trimIndent())

        val qrCodeObject = barcodeQRCode.createFormXObject(ColorConstants.BLACK, pdfDocument)
        val qrCodeImage = Image(qrCodeObject).setWidth(80f).setHorizontalAlignment(
            HorizontalAlignment.CENTER)
        document.add(image)
        document.add(namapengguna)
        document.add(group)
        document.add(table)
        document.add(qrCodeImage)
        document.close()
        Toast.makeText(applicationContext, "PDF Created", Toast.LENGTH_SHORT).show()
    }
}