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

class ProxActivity : AppCompatActivity() {
    //klasa odpowiedzialna za pobrainie danych z czujnika bliży
    //deklaracja sensora i poźniej wykorzystywanych elementów GUI
    //deklaracja wartości stałych i zmiennych
    //pobieranie danych z czujnika
    private var mSensorManager : SensorManager?= null
    private var viewModel = AppViewModel()
    private var mTextProximity: TextView? = null
    private var mProximity : Sensor?= null
    //deklaracja listenera do sprawdzania wartości zmierzonych przez czujnik bliży
    private val proximitySensorEventListener = object : SensorEventListener {

        override fun onAccuracyChanged(sensor: Sensor, accuracy: Int) {
        }

        @RequiresApi(Build.VERSION_CODES.KITKAT)
        override fun onSensorChanged(event: SensorEvent) {
            val builder = StringBuilder()
            //zmienna do przechowywania wartości z czujnika
            val proxval = event.values[0]
            builder.append("Wartość odległości: ")
            builder.append(proxval)
            builder.append(" cm")
            mTextProximity?.text = builder.toString()

            val appDataDTO = AppDataDTO(proxy_value = proxval.toDouble())
            viewModel.createProxy(appDataDTO)
        }

    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_prox)
        mTextProximity = findViewById(R.id.proximity_text)
        mSensorManager = getSystemService(Context.SENSOR_SERVICE) as SensorManager
        mProximity = mSensorManager!!.getDefaultSensor(Sensor.TYPE_PROXIMITY)
    }
    override fun onResume() {
        super.onResume()
        //ustanowienie czestości probkowania co 3s
        mSensorManager!!.registerListener(proximitySensorEventListener,mProximity,
                3000000)

    }
    override fun onPause() {
        super.onPause()
        mSensorManager!!.unregisterListener(proximitySensorEventListener)
    }
}