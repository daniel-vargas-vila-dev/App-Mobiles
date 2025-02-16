package com.example.appagenda

import android.app.AlertDialog
import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.activity.result.ActivityResultLauncher

class EventAdapter(
    private val context: Context,
    private val events: MutableList<Event>,
    private val onDelete: (Int) -> Unit,
    private val onEdit: (Int) -> Unit
) : BaseAdapter() {

    override fun getCount(): Int = events.size

    override fun getItem(position: Int): Any = events[position]

    override fun getItemId(position: Int): Long = position.toLong()

    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
        val view = convertView ?: LayoutInflater.from(context).inflate(R.layout.item_event, parent, false)

        val tvEventName = view.findViewById<TextView>(R.id.tvEventName)
        val tvEventDetails = view.findViewById<TextView>(R.id.tvEventDetails)
        val btnEdit = view.findViewById<ImageView>(R.id.btnEditEvent)
        val btnDelete = view.findViewById<ImageView>(R.id.btnDeleteEvent)

        val event = events[position]
        tvEventName.text = event.titre
        tvEventDetails.text = "🕒 ${event.heureDebut ?: "Non précisé"} 📍 ${event.localisation ?: "Non précisé"}"

        // Bouton Modifier
        btnEdit.setOnClickListener { onEdit(position) }

        // Bouton Supprimer
        btnDelete.setOnClickListener {
            AlertDialog.Builder(context)
                .setTitle(context.getString(R.string.supprimer_evenement))
                .setMessage(context.getString(R.string.confirmation_suppression))
                .setPositiveButton(context.getString(R.string.oui)) { _, _ -> onDelete(position) }
                .setNegativeButton(context.getString(R.string.annuler), null)
                .show()
        }

        return view
    }
}
