package com.example.appagenda

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.activity.ComponentActivity

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val btnAgenda = findViewById<Button>(R.id.btnAgenda)
        val btnListeEvenements = findViewById<Button>(R.id.btnListeEvenements)

        btnAgenda.setOnClickListener {
            startActivity(Intent(this, AgendaActivity::class.java))
        }

        btnListeEvenements.setOnClickListener {
            startActivity(Intent(this, ListeEvenementsActivity::class.java))
        }
    }
}
