package com.example.internal_sensor

import android.content.Context
import android.hardware.Sensor
import android.hardware.SensorEvent
import android.hardware.SensorEventListener
import android.hardware.SensorManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Html
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity(), SensorEventListener {
    private lateinit var sm: SensorManager
    private var mySensor: Sensor? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        sm = getSystemService(Context.SENSOR_SERVICE) as SensorManager
        mySensor = sm.getDefaultSensor(Sensor.TYPE_GRAVITY)
        txt.text = "Loading.."
    }

    override fun onAccuracyChanged(p0: Sensor?, p1: Int) {
    }

    override fun onSensorChanged(p0: SensorEvent?) {
        if (p0?.sensor == mySensor) {
            // use fromhtml to get superscripted 2 for m/s2
            txt.text = Html.fromHtml("Gravity: ${p0?.values?.get(2) ?: "Unknown"} m/s<sup>2</sup>")
        }
    }

    override fun onResume() {
         super.onResume()
         mySensor?.also {
             sm.registerListener(this, it, SensorManager.SENSOR_DELAY_NORMAL)
         }
    }

     override fun onPause() {
         super.onPause()
         sm.unregisterListener(this)
     }

}