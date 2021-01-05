package com.example.projekt_przetwarzanie

import android.annotation.SuppressLint
import android.content.Context
import android.hardware.Sensor
import android.hardware.SensorEvent
import android.hardware.SensorEventListener
import android.hardware.SensorManager
import android.os.Build
import android.os.Bundle
import android.view.View
import android.webkit.WebView
import android.webkit.WebViewClient
import android.widget.Button
import android.widget.TextView
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity

class AccelActivity : AppCompatActivity() {
    //klasa odpowiedzialna za pobrainie danych z czujnika - akcelerometru
    //deklaracja sensora i poźniej wykorzystywanych elementów GUI
    //deklaracja wartości stałych i zmiennych
    //pobieranie danych z czujnika
    private var mSensorManager : SensorManager?= null
    private var viewModel = AppViewModel()
    private var mTextAccelerometer: TextView? = null
    private var mAccelerometer : Sensor?= null
    //deklaracja listenera do sprawdzania wartości zmierzonych przez akcelerometr przy zmianie wartości
    private val accelerometerSensorEventListener = object : SensorEventListener {
        override fun onAccuracyChanged(sensor: Sensor, accuracy: Int) {
        }

        @RequiresApi(Build.VERSION_CODES.KITKAT)
        override fun onSensorChanged(event: SensorEvent) {
            //zmienne do przechowywania wartości z każdej osi akcelerometru
            val builder = StringBuilder()
            val accX = event.values[0]
            val accY = event.values[1]
            val accZ = event.values[2]
            builder.append(" Wartość przyśpieszenia w osi x: ")
            builder.append(accX)
            builder.append(" m/s2")
            builder.append("\n  Wartość przyśpieszenia w osi y: ")
            builder.append(accY)
            builder.append(" m/s2")
            builder.append("\n  Wartość przyśpieszenia w osi z: ")
            builder.append(accZ)
            builder.append(" m/s2")
            mTextAccelerometer?.text = builder
            val appDataDTO = AppDataDTO(accelerometerX = accX.toDouble(), accelerometerY = accY.toDouble(), accelerometerZ = accZ.toDouble())
            viewModel.createAccelerometer(appDataDTO)
        }

    }
    @SuppressLint("SetJavaScriptEnabled")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_accel)
        mTextAccelerometer = findViewById(R.id.accelerometer_text)
        mSensorManager = getSystemService(Context.SENSOR_SERVICE) as SensorManager
        mAccelerometer = mSensorManager!!.getDefaultSensor(Sensor.TYPE_ACCELEROMETER)
        val button = findViewById<Button>(R.id.button)
        title = "Wykresy dla danych z akcelometru"
        button.setEnabled(true)
        val webView = findViewById<WebView>(R.id.webView)
        webView.webViewClient = WebViewClient()
		// po kliknięciu przycisku następuje wyświetlanie wykresów - poprzez załadowanie odpowiedniej strony interentowej
        button.setOnClickListener {
            button.setEnabled(false)
			// ładowanie widoku z wykresem danych z akcelerometru
            webView.loadUrl(RetrofitInstance.BASE_URL + "chartAccelerometer")
            val webSettings = webView.settings
            webSettings.javaScriptEnabled = true
        }


    }
    override fun onResume() {
        super.onResume()
        //ustanowienie czestości probkowania co 3s
        mSensorManager!!.registerListener(accelerometerSensorEventListener,mAccelerometer,
            3000000)

    }

    override fun onPause() {
        super.onPause()
        mSensorManager!!.unregisterListener(accelerometerSensorEventListener)
    }
}