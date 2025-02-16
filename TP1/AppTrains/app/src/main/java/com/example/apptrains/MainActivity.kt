package com.example.apptrains

import android.app.DatePickerDialog
import android.app.TimePickerDialog
import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.activity.ComponentActivity
import java.util.Calendar

class MainActivity : ComponentActivity() {
    private lateinit var editTextDepart: EditText
    private lateinit var editTextArrivee: EditText
    private lateinit var buttonDate: Button
    private lateinit var buttonHeure: Button
    private lateinit var textViewDate: TextView
    private lateinit var textViewHeure: TextView
    private lateinit var buttonRechercher: Button

    private var selectedDate: String = ""
    private var selectedTime: String = ""
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        editTextDepart = findViewById(R.id.editTextDepart)
        editTextArrivee = findViewById(R.id.editTextArrivee)
        buttonDate = findViewById(R.id.buttonDate)
        buttonHeure = findViewById(R.id.buttonHeure)
        textViewDate = findViewById(R.id.textViewDate)
        textViewHeure = findViewById(R.id.textViewHeure)
        buttonRechercher = findViewById(R.id.buttonRechercher)

        // Sélection de la date
        buttonDate.setOnClickListener {
            val calendar = Calendar.getInstance()
            val year = calendar.get(Calendar.YEAR)
            val month = calendar.get(Calendar.MONTH)
            val day = calendar.get(Calendar.DAY_OF_MONTH)

            val datePickerDialog = DatePickerDialog(this, { _, y, m, d ->
                selectedDate = "$d/${m + 1}/$y"
                textViewDate.text = "Date : $selectedDate"
            }, year, month, day)
            datePickerDialog.show()
        }

        // Sélection de l'heure
        buttonHeure.setOnClickListener {
            val calendar = Calendar.getInstance()
            val hour = calendar.get(Calendar.HOUR_OF_DAY)
            val minute = calendar.get(Calendar.MINUTE)

            val timePickerDialog = TimePickerDialog(this, { _, h, m ->
                selectedTime = String.format("%02d:%02d", h, m)
                textViewHeure.text = getString(R.string.time_act)+ selectedTime
            }, hour, minute, true)
            timePickerDialog.show()
        }

        // Lancer la recherche et rediriger vers la page des résultats -> ResultActivity
        buttonRechercher.setOnClickListener {
            val depart = editTextDepart.text.toString().trim()
            val arrivee = editTextArrivee.text.toString().trim()

            if (depart.isNotEmpty() && arrivee.isNotEmpty() && selectedDate.isNotEmpty() && selectedTime.isNotEmpty()) {
                val intent = Intent(this, ResultActivity::class.java)
                intent.putExtra("DEPART", depart)
                intent.putExtra("ARRIVEE", arrivee)
                intent.putExtra("DATE", selectedDate)
                intent.putExtra("HEURE", selectedTime)
                startActivity(intent)
            } else {
                Toast.makeText(this, getString(R.string.toast_alert), Toast.LENGTH_SHORT).show()
            }
        }
    }
}