package com.example.exo3

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.activity.ComponentActivity

class DisplayActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.display_activity)

        val nom = intent.getStringExtra("EXTRA_NOM")
        val prenom = intent.getStringExtra("EXTRA_PRENOM")
        val age = intent.getStringExtra("EXTRA_AGE")
        val domaine = intent.getStringExtra("EXTRA_DOMAINE")
        val telephone = intent.getStringExtra("EXTRA_TELEPHONE")

        val textView = findViewById<TextView>(R.id.textViewInfo)
        textView.text = getString(R.string.nom) + " $nom\n" +
                getString(R.string.prenom) + " $prenom\n" +
                getString(R.string.age) + " $age\n" +
                getString(R.string.domain) + " $domaine\n" +
                getString(R.string.tel) + " $telephone"

        val btnOk = findViewById<Button>(R.id.btnOk)
        val btnRetour = findViewById<Button>(R.id.btnRetour)

        btnOk.setOnClickListener {
            val intent = Intent(this, ThirdActivity::class.java).apply {
                putExtra("cell",telephone)
            }
            startActivity(intent)
        }

        btnRetour.setOnClickListener {
            finish()
        }
    }
}
