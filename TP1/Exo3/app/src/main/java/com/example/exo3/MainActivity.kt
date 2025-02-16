package com.example.exo3

import android.animation.ArgbEvaluator
import android.animation.ObjectAnimator
import android.animation.ValueAnimator
import android.app.AlertDialog
import android.content.Intent
import android.content.res.ColorStateList
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.core.content.ContextCompat
import androidx.core.view.ViewCompat

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        /* EXO 3 Question 3 sans XML

        val layout = LinearLayout(this).apply {
            orientation = LinearLayout.VERTICAL
            setPadding(32, 32, 32, 32)
        }

        fun createField(label: String): EditText {
            layout.addView(TextView(this).apply { text = label })
            return EditText(this).apply { hint = "Entrez votre $label" }.also { layout.addView(it) }
        }

        val etNom = createField("Nom")
        val etPrenom = createField("Prénom")
        val etAge = createField("Âge").apply { inputType = android.text.InputType.TYPE_CLASS_NUMBER }
        val etDomaine = createField("Domaine de compétences")
        val etTelephone = createField("Numéro de téléphone").apply { inputType = android.text.InputType.TYPE_CLASS_PHONE }

        val btnValider = Button(this).apply {
            text = "Valider"
            setOnClickListener {
                Toast.makeText(
                    this@MainActivity,
                    "Données enregistrées : ${etNom.text}, ${etPrenom.text}, ${etAge.text}, ${etDomaine.text}, ${etTelephone.text}",
                    Toast.LENGTH_LONG
                ).show()
            }
        }

        layout.addView(btnValider)
        setContentView(layout)
*/



        setContentView(R.layout.activity)

        val nom = findViewById<EditText>(R.id.etNom)
        val prenom = findViewById<EditText>(R.id.etPrenom)
        val age = findViewById<EditText>(R.id.etAge)
        val domaine = findViewById<EditText>(R.id.etDomaine)
        val telephone = findViewById<EditText>(R.id.etTelephone)
        val btnValider = findViewById<Button>(R.id.btnValider)

        btnValider.setOnClickListener {
            val nomText = nom.text.toString().trim()
            val prenomText = prenom.text.toString().trim()
            val ageText = age.text.toString().trim()
            val domaineText = domaine.text.toString().trim()
            val telephoneText = telephone.text.toString().trim()

            Log.i("Test infos","Nom: $nomText, Prenom: $prenomText," +
                    "Age: $ageText, Domaine: $domaineText et Telephone: $telephoneText")

            val editTexts = listOf(nom, prenom, age, domaine, telephone)
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
                        /* it.backgroundTintList = ContextCompat.getColorStateList(this, android.R.color.holo_green_light) */
                        animateColorChange(it, android.R.color.holo_green_light, R.color.transparent, 2000)
                    }

                    Toast.makeText(this, getString(R.string.validation_success), Toast.LENGTH_SHORT).show()

                    val intent = Intent(this, DisplayActivity::class.java).apply {
                        putExtra("EXTRA_NOM", nomText)
                        putExtra("EXTRA_PRENOM", prenomText)
                        putExtra("EXTRA_AGE", ageText)
                        putExtra("EXTRA_DOMAINE", domaineText)
                        putExtra("EXTRA_TELEPHONE", telephoneText)
                    }
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
