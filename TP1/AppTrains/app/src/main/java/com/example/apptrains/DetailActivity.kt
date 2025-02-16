package com.example.apptrains

import android.animation.ValueAnimator
import android.app.AlertDialog
import android.content.Intent
import android.content.res.ColorStateList
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.core.content.ContextCompat
import androidx.core.view.ViewCompat

class DetailActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail)

        val tvDetailHoraire = findViewById<TextView>(R.id.tvDetailHoraire)
        val tvDetailPrix = findViewById<TextView>(R.id.tvDetailPrix)

        val horaire = intent.getStringExtra("HORAIRE")
        val prix = intent.getStringExtra("PRIX")

        tvDetailHoraire.text = getString(R.string.time_act)+ horaire
        tvDetailPrix.text = getString(R.string.prix_adapt) + prix

        val nom = findViewById<EditText>(R.id.etNom)
        val prenom = findViewById<EditText>(R.id.etPrenom)
        val age = findViewById<EditText>(R.id.etAge)
        val telephone = findViewById<EditText>(R.id.etTelephone)
        val btnValider = findViewById<Button>(R.id.btnValider)

        btnValider.setOnClickListener {
            val nomText = nom.text.toString().trim()
            val prenomText = prenom.text.toString().trim()
            val ageText = age.text.toString().trim()
            val telephoneText = telephone.text.toString().trim()

            Log.i("Test infos","Nom: $nomText, Prenom: $prenomText," +
                    "Age: $ageText et Telephone: $telephoneText")

            val editTexts = listOf(nom, prenom, age, telephone)
            var tousChampsRemplis = true

            editTexts.forEach {
                if (it.text.toString().trim().isEmpty()) {
                    it.backgroundTintList = ContextCompat.getColorStateList(this, android.R.color.holo_red_light)
                    tousChampsRemplis = false
                }
            }

            if (tousChampsRemplis) {
                val builder = AlertDialog.Builder(this)
                builder.setTitle(getString(R.string.confirm_title))
                builder.setMessage(getString(R.string.confirm_message))
                builder.setPositiveButton(getString(R.string.yes)) { _, _ ->
                    editTexts.forEach {
                        animateColorChange(it, android.R.color.holo_green_light, R.color.transparent, 2000)
                    }

                    Toast.makeText(this, getString(R.string.validation_success), Toast.LENGTH_SHORT).show()

                    val intent = Intent(this, ConfirmationActivity::class.java)
                    startActivity(intent)
                }
                builder.setNegativeButton(getString(R.string.cancel)) { dialog, _ ->
                    dialog.dismiss()
                }
                val dialog = builder.create()
                dialog.show()
            }else {
                Toast.makeText(this, getString(R.string.validation_error), Toast.LENGTH_SHORT).show()

            }
        }
    }


    private fun animateColorChange(editText: EditText, startColor: Int, endColor: Int, duration: Long = 1000) {
        val colorAnimator = ValueAnimator.ofArgb(
            ContextCompat.getColor(editText.context, startColor),
            ContextCompat.getColor(editText.context, endColor)
        )
        colorAnimator.duration = duration
        colorAnimator.addUpdateListener { animator ->
            val animatedColor = animator.animatedValue as Int
            ViewCompat.setBackgroundTintList(editText, ColorStateList.valueOf(animatedColor))
        }
        colorAnimator.start()
    }
}