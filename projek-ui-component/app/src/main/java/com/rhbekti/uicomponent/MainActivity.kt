package com.rhbekti.uicomponent

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageButton
import android.widget.Toast

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val imgBtnAdd = findViewById<ImageButton>(R.id.imgButtonAdd)
        imgBtnAdd.setOnClickListener{
            Toast.makeText(this@MainActivity,"Berhasil",Toast.LENGTH_LONG).show()
        }
    }
}