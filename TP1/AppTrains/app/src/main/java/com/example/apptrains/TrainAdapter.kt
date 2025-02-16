package com.example.apptrains

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.core.content.ContextCompat

class TrainAdapter(
    private val context: Context,
    private val trainList: List<Train>
) : ArrayAdapter<Train>(context, 0, trainList) {

    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        val view = convertView ?: LayoutInflater.from(context).inflate(R.layout.item_train, parent, false)

        val train = trainList[position]

        val tvHoraire = view.findViewById<TextView>(R.id.tvHoraire)
        val tvPrix = view.findViewById<TextView>(R.id.tvPrix)
        val tvDispo = view.findViewById<TextView>(R.id.tvDispo)
        val imageTrain = view.findViewById<ImageView>(R.id.imageTrain)
        val imgFleche = view.findViewById<ImageView>(R.id.imgFleche)


        tvHoraire.text = train.horaire
        tvPrix.text = context.getString(R.string.prix_adapt) + train.prix
        tvDispo.text = train.dispo


        if(train.dispo == context.getString(R.string.complet)){
            tvDispo.setTextColor(ContextCompat.getColor(context, android.R.color.holo_red_dark))
            imgFleche.visibility = View.GONE
        }else {
            tvDispo.setTextColor(ContextCompat.getColor(context, android.R.color.holo_green_dark))
            imgFleche.visibility = View.VISIBLE
            imgFleche.setOnClickListener {
                val intent = Intent(context, DetailActivity::class.java)
                intent.putExtra("HORAIRE", train.horaire)
                intent.putExtra("PRIX", train.prix)
                intent.putExtra("DISPO", train.dispo)
                context.startActivity(intent)
            }
        }

        return view
    }
}