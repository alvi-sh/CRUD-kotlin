package com.example.myapplication.ui.activity

import android.hardware.Sensor
import android.hardware.SensorManager
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.myapplication.R
import com.example.myapplication.databinding.ActivitySensorListBinding

class SensorListActivity : AppCompatActivity() {

    lateinit var sensorListBinding: ActivitySensorListBinding
    lateinit var sensorManager: SensorManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        sensorListBinding = ActivitySensorListBinding.inflate(layoutInflater)
        setContentView(sensorListBinding.root)

        sensorManager = getSystemService(SENSOR_SERVICE) as SensorManager

        val lstSensor = sensorManager.getSensorList(Sensor.TYPE_ALL)
        for (sensor in lstSensor) {
            sensorListBinding.sensorList.append(sensor.name + "\n")
        }

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
    }
}