package com.example.exo3

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.activity.ComponentActivity

class ThirdActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.third_activity)

        val telephone = intent.getStringExtra("cell")

        val textViewPhone = findViewById<TextView>(R.id.textViewPhone)
        val btnCall = findViewById<Button>(R.id.btnCall)
        val phoneIcon = findViewById<ImageView>(R.id.imagePhone)

        textViewPhone.text = telephone

        btnCall.setOnClickListener {
            val callIntent = Intent(Intent.ACTION_DIAL).apply {
                data = Uri.parse("tel:$telephone")
            }
            startActivity(callIntent)
        }
    }
}