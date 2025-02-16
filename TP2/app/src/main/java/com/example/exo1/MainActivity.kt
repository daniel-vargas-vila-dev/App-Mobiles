package com.example.exo1

import android.hardware.Sensor
import android.hardware.SensorManager
import android.os.Bundle
import android.widget.TextView
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.exo1.ui.theme.Exo1Theme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.layout)

        // Récupérer le TextView de l'interface
        val textView = findViewById<TextView>(R.id.textViewSensors)

        // Obtenir le gestionnaire de capteurs
        val sensorManager = getSystemService(SENSOR_SERVICE) as SensorManager
        val sensorList: List<Sensor> = sensorManager.getSensorList(Sensor.TYPE_ALL)

        // Construire la liste des capteurs sous forme de texte
        val sensorInfo = StringBuilder("Capteurs disponibles:\n")
        for (sensor in sensorList) {
            sensorInfo.append("${sensor.name} - ${sensor.vendor}\n")
        }

        // Afficher la liste des capteurs dans le TextView
        textView.text = sensorInfo.toString()
    }
}


@Composable
fun SensorList(sensors: List<Sensor>, modifier: Modifier = Modifier) {
    Column(modifier = modifier.padding(16.dp)) {
        Text(text = "Capteurs disponibles :", style = MaterialTheme.typography.headlineSmall)
        Spacer(modifier = Modifier.height(8.dp))

        LazyColumn {
            items(sensors) { sensor ->
                SensorItem(sensor)
            }
        }
    }
}

@Composable
fun SensorItem(sensor: Sensor) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 4.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Text(text = sensor.name, style = MaterialTheme.typography.bodyLarge)
            Text(text = "Fabricant : ${sensor.vendor}", style = MaterialTheme.typography.bodyMedium)
        }
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewSensorList() {
    Exo1Theme {
        SensorList(sensors = listOf()) // Affichage d'exemple
    }
}