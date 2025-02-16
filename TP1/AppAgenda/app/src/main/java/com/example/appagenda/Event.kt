package com.example.appagenda

import org.json.JSONObject


data class Event(
    val date: String,
    val titre: String,
    val description: String? = null,
    val heureDebut: String? = null,
    val heureFin: String? = null,
    val localisation: String? = null,
    val marqueur: String
){
    fun toJson(): String {
        val jsonObject = JSONObject()
        jsonObject.put("date", date)
        jsonObject.put("titre", titre)
        jsonObject.put("description", description ?: "")
        jsonObject.put("heureDebut", heureDebut ?: "")
        jsonObject.put("heureFin", heureFin ?: "")
        jsonObject.put("localisation", localisation ?: "")
        jsonObject.put("marqueur", marqueur)
        return jsonObject.toString()
    }

    companion object {
        fun fromJson(jsonString: String): Event {
            val jsonObject = JSONObject(jsonString)
            return Event(
                date = jsonObject.getString("date"),
                titre = jsonObject.getString("titre"),
                description = jsonObject.optString("description", null),
                heureDebut = jsonObject.optString("heureDebut", null),
                heureFin = jsonObject.optString("heureFin", null),
                localisation = jsonObject.optString("localisation", null),
                marqueur = jsonObject.getString("marqueur")
            )
        }
    }
}
