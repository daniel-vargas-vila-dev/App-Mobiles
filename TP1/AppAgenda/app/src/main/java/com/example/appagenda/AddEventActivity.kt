package com.example.appagenda

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.widget.*
import androidx.activity.ComponentActivity

class AddEventActivity : ComponentActivity() {

    private var position: Int = -1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_event)

        val etTitre = findViewById<EditText>(R.id.etTitre)
        val etDescription = findViewById<EditText>(R.id.etDescription)
        val timePickerDebut = findViewById<TimePicker>(R.id.timePickerDebut)
        val timePickerFin = findViewById<TimePicker>(R.id.timePickerFin)
        val etLocalisation = findViewById<EditText>(R.id.etLocalisation)
        val spinnerMarqueur = findViewById<Spinner>(R.id.spinnerMarqueur)
        val btnAjouter = findViewById<Button>(R.id.btnAjouter)
        val btnRetourAccueil = findViewById<Button>(R.id.buttonRetour)

        val marqueurs = arrayOf(
            getString(R.string.marqueur_urgent),
            getString(R.string.marqueur_tres_urgent),
            getString(R.string.marqueur_ecole),
            getString(R.string.marqueur_sport),
            getString(R.string.marqueur_travail),
            getString(R.string.marqueur_famille),
            getString(R.string.marqueur_amis)
        )
        val adapter = ArrayAdapter(this, android.R.layout.simple_spinner_dropdown_item, marqueurs)
        spinnerMarqueur.adapter = adapter



        // Vérifier si on est en mode modification
        position = intent.getIntExtra("EXTRA_POSITION", -1)
        if (position != -1) {
            etTitre.setText(intent.getStringExtra("EXTRA_TITRE"))
            etDescription.setText(intent.getStringExtra("EXTRA_DESCRIPTION"))
            etLocalisation.setText(intent.getStringExtra("EXTRA_LOCALISATION"))

            val heureDebut = intent.getStringExtra("EXTRA_HEURE_DEBUT")?.split(":")
            if (heureDebut != null && heureDebut.size == 2) {
                timePickerDebut.hour = heureDebut[0].toInt()
                timePickerDebut.minute = heureDebut[1].toInt()
            }

            val heureFin = intent.getStringExtra("EXTRA_HEURE_FIN")?.split(":")
            if (heureFin != null && heureFin.size == 2) {
                timePickerFin.hour = heureFin[0].toInt()
                timePickerFin.minute = heureFin[1].toInt()
            }

            val marqueur = intent.getStringExtra("EXTRA_MARQUEUR") ?: "Autre"
            val marqueurIndex = marqueurs.indexOf(marqueur)
            if (marqueurIndex != -1) {
                spinnerMarqueur.setSelection(marqueurIndex)
            }
        }



        btnAjouter.setOnClickListener {
            val titre = etTitre.text.toString()
            val description = etDescription.text.toString()
            val localisation = etLocalisation.text.toString()
            val heureDebut = "${timePickerDebut.hour}:${timePickerDebut.minute}"
            val heureFin = "${timePickerFin.hour}:${timePickerFin.minute}"
            val marqueur = spinnerMarqueur.selectedItem.toString()

            if (titre.isNotEmpty()) {
                val resultIntent = Intent().apply {
                    putExtra("EXTRA_POSITION", position)
                    putExtra("EXTRA_DATE", intent.getStringExtra("EXTRA_DATE"))
                    putExtra("EXTRA_TITRE", titre)
                    putExtra("EXTRA_DESCRIPTION", description)
                    putExtra("EXTRA_HEURE_DEBUT", heureDebut)
                    putExtra("EXTRA_HEURE_FIN", heureFin)
                    putExtra("EXTRA_LOCALISATION", localisation)
                    putExtra("EXTRA_MARQUEUR", marqueur)
                }
                setResult(Activity.RESULT_OK, resultIntent)
                finish()
            } else {
                Toast.makeText(this, getString(R.string.obl), Toast.LENGTH_SHORT).show()
            }

        }

        // Bouton retour vers l'accueil
        btnRetourAccueil.setOnClickListener {
            //val intent = Intent(this, AgendaActivity::class.java)
            //startActivity(intent)
            finish()
        }
    }
}
