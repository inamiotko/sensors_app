package com.example.projekt_przetwarzanie

import android.content.Context
import android.hardware.Sensor
import android.hardware.SensorEvent
import android.hardware.SensorEventListener
import android.hardware.SensorManager
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView
import androidx.annotation.RequiresApi

class LightActivity : AppCompatActivity() {
    //klasa odpowiedzialna za pobrainie danych z czujnika światła
    //deklaracja sensora i poźniej wykorzystywanych elementów GUI
    //deklaracja wartości stałych i zmiennych
    //pobieranie danych z czujnika
    private var mSensorManager : SensorManager?= null
    private var viewModel = AppViewModel()
    private var mTextLight: TextView? = null
    private var mLight : Sensor?= null
    //deklaracja listenera do sprawdzania wartości zmierzonych przez czujnik
    private val lightSensorEventListener = object : SensorEventListener {
        override fun onAccuracyChanged(sensor: Sensor, accuracy: Int) {
        }

        @RequiresApi(Build.VERSION_CODES.KITKAT)
        override fun onSensorChanged(event: SensorEvent) {
            val builder = StringBuilder()
            //zmienna do przechowywania wartości z czujnika
            val lightval = event.values[0]
            builder.append("Wartość natężenia światła: ")
            builder.append(lightval)
            builder.append(" lx")
            mTextLight?.text = builder

            val appDataDTO = AppDataDTO(lightValue = lightval.toDouble())
            viewModel.createLight(appDataDTO)
        }

    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_light)
        mTextLight = findViewById(R.id.light_text)
        mSensorManager = getSystemService(Context.SENSOR_SERVICE) as SensorManager
        mLight = mSensorManager!!.getDefaultSensor(Sensor.TYPE_LIGHT)
    }
    override fun onResume() {
        super.onResume()
        //ustanowienie czestości probkowania co 3s
        mSensorManager!!.registerListener(lightSensorEventListener,mLight,
                3000000)

    }
    override fun onPause() {
        super.onPause()
        mSensorManager!!.unregisterListener(lightSensorEventListener)
    }
}