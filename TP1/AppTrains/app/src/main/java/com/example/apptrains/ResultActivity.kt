package com.example.apptrains

import android.os.Bundle
import android.widget.*
import androidx.activity.ComponentActivity

class ResultActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_result)

        val textViewItineraire = findViewById<TextView>(R.id.textViewItin)
        val listViewResult = findViewById<ListView>(R.id.listViewResult)

        val depart = intent.getStringExtra("DEPART")
        val arrivee = intent.getStringExtra("ARRIVEE")
        val date = intent.getStringExtra("DATE")
        val heure = intent.getStringExtra("HEURE")

        textViewItineraire.text = getString(R.string.result_text, depart, arrivee, date, heure)


        val heureDepart = heure?.split(":")?.get(0)?.toInt() ?: 0
        val heure1 = ajusterHeure(heureDepart + 2)
        val heure2 = ajusterHeure(heureDepart + 3)
        val heure3 = ajusterHeure(heureDepart + 5)

        val horairesFiltres = listOf(
            Train("$heure ➝ $heure1:00", "45€", getString(R.string.places_dispo), R.drawable.icon2_train),
            Train("${ajusterHeure(heureDepart + 1)}:30 ➝ $heure2:30", "39€", getString(R.string.complet),  R.drawable.icon2_train),
            Train("$heure2:00 ➝ $heure3:00", "55€", getString(R.string.places_dispo),  R.drawable.icon2_train),
            Train("${ajusterHeure(heureDepart + 5)}:15 ➝ ${ajusterHeure(heureDepart + 7)}:45", "49€", getString(R.string.complet), R.drawable.icon2_train),
            Train("${ajusterHeure(heureDepart + 6)}:00 ➝ ${ajusterHeure(heureDepart + 8)}:30", "60€", getString(R.string.places_dispo), R.drawable.icon2_train),
            Train("${ajusterHeure(heureDepart + 7)}:20 ➝ ${ajusterHeure(heureDepart + 9)}:50", "35€", getString(R.string.complet), R.drawable.icon2_train),
            Train("${ajusterHeure(heureDepart + 9)}:00 ➝ ${ajusterHeure(heureDepart + 11)}:30", "29€", getString(R.string.places_dispo), R.drawable.icon2_train),
            Train("${ajusterHeure(heureDepart + 10)}:10 ➝ ${ajusterHeure(heureDepart + 12)}:40", "50€", getString(R.string.places_dispo), R.drawable.icon2_train)
        )

        val adapter = TrainAdapter(this,horairesFiltres)
        listViewResult.adapter = adapter
    }

    fun ajusterHeure(heure: Int): String {
        val nouvelleHeure = (heure % 24) // Si on dépasse 23, on revient à 00
        return String.format("%02d", nouvelleHeure)
    }
}