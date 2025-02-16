package com.example.appagenda

import android.app.Activity
import android.app.AlertDialog
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.ListView
import androidx.activity.ComponentActivity
import androidx.activity.result.contract.ActivityResultContracts

class ListeEvenementsActivity : ComponentActivity() {
    private val eventList = mutableListOf<Event>()
    private lateinit var eventAdapter: EventAdapter//ArrayAdapter<String>

    private val editEventLauncher = registerForActivityResult(
        ActivityResultContracts.StartActivityForResult()
    ) { result ->
        if (result.resultCode == Activity.RESULT_OK) {
            val data = result.data
            if (data != null) {
                val position = data.getIntExtra("EXTRA_POSITION", -1)
                val date = data.getStringExtra("EXTRA_DATE") ?: return@registerForActivityResult
                val titre = data.getStringExtra("EXTRA_TITRE") ?: return@registerForActivityResult
                val description = data.getStringExtra("EXTRA_DESCRIPTION")
                val heureDebut = data.getStringExtra("EXTRA_HEURE_DEBUT")
                val heureFin = data.getStringExtra("EXTRA_HEURE_FIN")
                val localisation = data.getStringExtra("EXTRA_LOCALISATION")
                val marqueur = data.getStringExtra("EXTRA_MARQUEUR") ?: "Autre"

                if (position == -1) {
                    // Ajout d'un nouvel événement
                    val newEvent = Event(date, titre, description, heureDebut, heureFin, localisation, marqueur)
                    eventList.add(newEvent)
                } else {
                    // Mise à jour d'un événement existant
                    eventList[position] = Event(date, titre, description, heureDebut, heureFin, localisation, marqueur)
                }

                saveEvents()
                eventAdapter.notifyDataSetChanged()
            }
        }
    }



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_liste_evenements)

        val listView = findViewById<ListView>(R.id.lvTousEvenements)
        val btnRetourAccueil = findViewById<Button>(R.id.btnRetourAccueil)

        loadEvents() // Charge les événements enregistrés

        eventAdapter = EventAdapter(this, eventList, ::deleteEvent, ::editEvent)
        listView.adapter = eventAdapter

        listView.setOnItemClickListener { _, _, position, _ ->
            val event = eventList[position]

            AlertDialog.Builder(this)
                .setTitle(getString(R.string.details_evenement))
                .setMessage("📌 ${event.titre}\n📝 ${event.description ?: getString(R.string.no_desc)}\n🕒 ${event.heureDebut ?: "Non précisée"} - ${event.heureFin ?: "Non précisée"}\n📍 ${event.localisation ?: "Pas de localisation"}\n🏷 ${event.marqueur}")
                .setPositiveButton("OK") { dialog, _ -> dialog.dismiss() }
                .show()
        }

        // Bouton retour vers l'accueil
        btnRetourAccueil.setOnClickListener {
            val intent = Intent(this, MainActivity::class.java)
            intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_NEW_TASK
            startActivity(intent)
            finish()
        }
    }


    private fun deleteEvent(position: Int) {
        eventList.removeAt(position)
        saveEvents()
        eventAdapter.notifyDataSetChanged()
    }

    private fun editEvent(position: Int) {
        val event = eventList[position]
        val intent = Intent(this, AddEventActivity::class.java).apply {
            putExtra("EXTRA_POSITION", position)
            putExtra("EXTRA_DATE", event.date)
            putExtra("EXTRA_TITRE", event.titre)
            putExtra("EXTRA_DESCRIPTION", event.description)
            putExtra("EXTRA_HEURE_DEBUT", event.heureDebut)
            putExtra("EXTRA_HEURE_FIN", event.heureFin)
            putExtra("EXTRA_LOCALISATION", event.localisation)
            putExtra("EXTRA_MARQUEUR", event.marqueur)
        }
        editEventLauncher.launch(intent)
    }

    private fun loadEvents() {
        val sharedPreferences = getSharedPreferences("AgendaPrefs", Context.MODE_PRIVATE)
        val eventsJsonList = sharedPreferences.getStringSet("events", emptySet()) ?: emptySet()

        eventList.clear()
        eventList.addAll(eventsJsonList.map { Event.fromJson(it) })
    }

    private fun saveEvents() {
        val sharedPreferences = getSharedPreferences("AgendaPrefs", Context.MODE_PRIVATE)
        val editor = sharedPreferences.edit()
        val eventsJsonList = eventList.map { it.toJson() }.toSet()
        editor.putStringSet("events", eventsJsonList)
        editor.apply()
    }


}
