package com.example.projekt_przetwarzanie

import android.annotation.SuppressLint
import android.content.Context
import android.hardware.Sensor
import android.hardware.SensorEvent
import android.hardware.SensorEventListener
import android.hardware.SensorManager
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.webkit.WebView
import android.webkit.WebViewClient
import android.widget.Button
import android.widget.TextView
import androidx.annotation.RequiresApi

class GyroActivity : AppCompatActivity() {
    //klasa odpowiedzialna za pobrainie danych z czujnika - żyroskopu
    //deklaracja sensora i poźniej wykorzystywanych elementów GUI
    //deklaracja wartości stałych i zmiennych
    //pobieranie danych z czujnika
    private var mSensorManager : SensorManager?= null
    private var viewModel = AppViewModel()
    private var mTextGyroscope: TextView? = null
    private var mGyroscope : Sensor?= null
    //deklaracja listenera do sprawdzania wartości zmierzonych przez żyroskop
    private val gyroscopeSensorEventListener = object : SensorEventListener {
        override fun onAccuracyChanged(sensor: Sensor, accuracy: Int) {
        }

        @RequiresApi(Build.VERSION_CODES.KITKAT)
        override fun onSensorChanged(event: SensorEvent) {
            val builder = StringBuilder()
            //zmienne do przechowywania wartości z każdej osi żyroskopu
            val gyroX = event.values[0]
            val gyroY = event.values[1]
            val gyroZ = event.values[2]
            builder.append(" Prędkość kątowa wokół osi x: ")
            builder.append(gyroX)
            builder.append("rad/s")
            builder.append("\n Prędkość kątowa wokół osi y: ")
            builder.append(gyroY)
            builder.append(" rad/s")
            builder.append("\n Prędkość kątowa wokół osi z: ")
            builder.append(gyroZ)
            builder.append(" rad/s")
            mTextGyroscope?.text = builder

            val appDataDTO = AppDataDTO(gyroscopeX = gyroX.toDouble(), gyroscopeY = gyroY.toDouble(), gyroscopeZ = gyroZ.toDouble())
            viewModel.createGyroscope(appDataDTO)
        }


    }
    @SuppressLint("SetJavaScriptEnabled")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_gyro)
        mTextGyroscope = findViewById(R.id.gyroscope_text)
        mSensorManager = getSystemService(Context.SENSOR_SERVICE) as SensorManager
        mGyroscope = mSensorManager!!.getDefaultSensor(Sensor.TYPE_GYROSCOPE)
        val button = findViewById<Button>(R.id.button)
        title = "Wykresy dla danych z żyroskopu"
        val webView = findViewById<WebView>(R.id.webView2)
        webView.webViewClient = WebViewClient()
        button.setEnabled(true)
        // po kliknięciu przycisku następuje wyświetlanie wykresów - poprzez załadowanie odpowiedniej strony interentowej
		button.setOnClickListener {
            button.setEnabled(false)
			// przekierowanie na odpowiedni URL z wykresem żyroskopu
            webView.loadUrl(RetrofitInstance.BASE_URL + "chartGyroscope")
            val webSettings = webView.settings
            webSettings.javaScriptEnabled = true
        }
    }
    override fun onResume() {
        super.onResume()
        //ustanowienie czestości probkowania co 3s
        mSensorManager!!.registerListener(gyroscopeSensorEventListener,mGyroscope,
                3000000)

    }
    override fun onPause() {
        super.onPause()
        mSensorManager!!.unregisterListener(gyroscopeSensorEventListener)
    }
}