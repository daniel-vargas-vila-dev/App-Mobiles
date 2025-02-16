package com.example.appagenda

import android.app.Activity
import android.app.AlertDialog
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.CalendarView
import android.widget.ListView
import android.widget.TextView
import androidx.activity.ComponentActivity
import androidx.activity.result.contract.ActivityResultContracts


class AgendaActivity : ComponentActivity() {
    private val eventList = mutableListOf<Event>()
    private lateinit var eventAdapter: ArrayAdapter<String>
    private lateinit var selectedDate: String


    private val addEventLauncher = registerForActivityResult(
        ActivityResultContracts.StartActivityForResult()
    ) { result ->
        if (result.resultCode == Activity.RESULT_OK) {
            val data = result.data
            if (data != null) {
                val titre = data.getStringExtra("EXTRA_TITRE") ?: return@registerForActivityResult
                val description = data.getStringExtra("EXTRA_DESCRIPTION")
                val heureDebut = data.getStringExtra("EXTRA_HEURE_DEBUT")
                val heureFin = data.getStringExtra("EXTRA_HEURE_FIN")
                val localisation = data.getStringExtra("EXTRA_LOCALISATION")
                val marqueur = data.getStringExtra("EXTRA_MARQUEUR") ?: "Autre"

                val event = Event(selectedDate, titre, description, heureDebut, heureFin, localisation, marqueur)
                eventList.add(event)
                updateEventsList()
            }
        }
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_agenda)

        loadEvents() // Charge les événements sauvegardés au démarrage


        val calendarView = findViewById<CalendarView>(R.id.calendarView)
        val tvSelectedDate = findViewById<TextView>(R.id.tvSelectedDate)
        val listView = findViewById<ListView>(R.id.lvEvents)
        val btnAddEvent = findViewById<Button>(R.id.btnAddEvent)
        val btnRetourAccueil = findViewById<Button>(R.id.btnRetourAccueil)


        eventAdapter = ArrayAdapter(this, android.R.layout.simple_list_item_1, mutableListOf())
        listView.adapter = eventAdapter


        calendarView.setOnDateChangeListener { _, year, month, dayOfMonth ->
            selectedDate = "$dayOfMonth/${month + 1}/$year"
            tvSelectedDate.text = getString(R.string.evenements_pour, selectedDate)
            updateEventsList()
        }

        btnAddEvent.setOnClickListener {
            //showAddEventDialog()
            val intent = Intent(this, AddEventActivity::class.java)
            addEventLauncher.launch(intent)
        }

        listView.setOnItemClickListener { _, _, position, _ ->
            val event = eventList.filter { it.date == selectedDate }[position]

            val message = buildString {
                append("📌 *${event.titre}*\n")
                event.description?.let { append("\n" + getString(R.string.description, it)) }
                event.heureDebut?.let { append("\n" + getString(R.string.heure_debut, it)) }
                event.heureFin?.let { append("\n" + getString(R.string.heure_fin, it)) }
                event.localisation?.let { append("\n" + getString(R.string.localisation, it)) }
                append("\n" + getString(R.string.marqueur, event.marqueur))
            }

            AlertDialog.Builder(this)
                .setTitle(getString(R.string.details_evenement))
                .setMessage(message)
                .setPositiveButton(getString(R.string.ok)) { dialog, _ -> dialog.dismiss() }
                .show()
        }

        // Bouton retour vers l'accueil
        btnRetourAccueil.setOnClickListener {
            saveEvents() // Sauvegarde les événements avant de quitter

            val intent = Intent(this, MainActivity::class.java)
            intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_NEW_TASK
            startActivity(intent)
            finish()
        }


    }




    private fun updateEventsList() {
        val filteredEvents = eventList.filter { it.date == selectedDate }

        val eventStrings = filteredEvents.map { event ->
            val heureDebut = event.heureDebut ?: "Heure non précisée"
            val localisation = event.localisation?.let { "📍 $it" } ?: ""
            "${event.titre}\n🕒 $heureDebut  $localisation"
        }

        eventAdapter.clear()
        eventAdapter.addAll(eventStrings)
        eventAdapter.notifyDataSetChanged()
    }


    private fun saveEvents() {
        val sharedPreferences = getSharedPreferences("AgendaPrefs", Context.MODE_PRIVATE)
        val editor = sharedPreferences.edit()

        val eventsJsonList = eventList.map { it.toJson() }.toSet()
        editor.putStringSet("events", eventsJsonList)
        editor.apply()
    }

    private fun loadEvents() {
        val sharedPreferences = getSharedPreferences("AgendaPrefs", Context.MODE_PRIVATE)
        val eventsJsonList = sharedPreferences.getStringSet("events", emptySet()) ?: emptySet()

        eventList.clear()
        eventList.addAll(eventsJsonList.map { Event.fromJson(it) })
    }



}