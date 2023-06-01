package com.rhbekti.phonebook

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.content.Context
import android.widget.ArrayAdapter
import android.widget.EditText
import android.widget.ListView
import android.widget.Toast
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.android.material.snackbar.Snackbar
// import kotlinx.android.synthetic.main.activity_main.*
import java.io.DataInputStream
import java.io.DataOutputStream
import java.io.IOException
import java.util.ArrayList


class MainActivity : AppCompatActivity() {
    lateinit var adapter: ArrayAdapter<String>
    lateinit var arrayKontak: ArrayList<String>
    lateinit var listView:ListView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val nama: EditText = findViewById(R.id.nama);
        val telepon:EditText = findViewById(R.id.telepon);
        val fab:FloatingActionButton = findViewById(R.id.fab);
        listView = findViewById(R.id.listView);

        arrayKontak = ArrayList()

        fab.setOnClickListener { view ->
            val bufferNama = ByteArray(30)
            val bufferTelepon = ByteArray(15)

            salinData(bufferNama, nama.text.toString())
            salinData(bufferTelepon, telepon.text.toString())

            try {
                // TODO 1: menulis - proses mengenali file untuk menyimpan
                val dataFile = openFileOutput(
                    "telepon.dat",
                    MODE_APPEND
                )
                val output = DataOutputStream(dataFile)

                // TODO 2: menulis - buffer ditulis ke dalam file
                output.write(bufferNama)
                output.write(bufferTelepon)

                // TODO 3: menulis - menyelesaikan proses menulis ke file
                dataFile.close()

                Snackbar.make(view, "Data telah disimpan", Snackbar.LENGTH_LONG)
                    .setAction("Action", null).show()

                nama.setText("")
                telepon.setText("")
            } catch (e: IOException) {
                Toast.makeText(baseContext, "Kesalahan: " + e.message, Toast.LENGTH_LONG).show()
            }
            tampilkanData()
        }
        tampilkanData()
    }

    fun salinData(buffer: ByteArray, data: String) {
        for (i in 0..buffer.size-1) buffer[i] = 0
        for (i in 0..data.length-1) buffer[i] = data[i].toByte()
    }

    fun tampilkanData() {
        try {
            // TODO 1: membaca - proses mengenali file untuk dibaca
            val dataFile = openFileInput("telepon.dat")
            val input = DataInputStream(dataFile)

            // TODO 2: membaca - ukuran buffer
            val bufNama = ByteArray(30)
            val bufTelepon = ByteArray(15)
            var infoData = ""
            var no = 1
            arrayKontak.clear()

            while (input.available() > 0) {
                // TODO 3: membaca - proses membaca file
                input.read(bufNama)
                input.read(bufTelepon)

                // TODO 4: membaca - proses mengubah/ mengkonversi ke string
                var dataNama = ""
                for (i in 0..bufNama.size-1)
                    dataNama = dataNama + bufNama[i].toChar()
                var dataTelepon = ""
                for (i in 0..bufTelepon.size-1)
                    dataTelepon = dataTelepon + bufTelepon[i].toChar()

                infoData = "$no. $dataNama - $dataTelepon"
                arrayKontak.add(infoData)
                no++
            }
            // TODO 5: membaca - mengakhiri proses membaca file
            dataFile.close()
        } catch (e: IOException) {
            Toast.makeText(
                baseContext, "Kesalahan: " + e.message,
                Toast.LENGTH_LONG
            ).show()
        }

        adapter = ArrayAdapter(
            this,
            android.R.layout.simple_list_item_1, android.R.id.text1, arrayKontak
        )
        listView.adapter = adapter
    }
}