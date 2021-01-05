package com.example.projekt_przetwarzanie


import android.annotation.SuppressLint
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.os.CountDownTimer
import android.view.Menu
import android.view.MenuItem
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import java.text.SimpleDateFormat
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.time.format.FormatStyle
import java.util.*


open class MainActivity : AppCompatActivity(){
    private var date_time: TextView? = null
    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        setSupportActionBar(findViewById(R.id.toolbar))
        date_time = findViewById(R.id.date_time_text)
        val timer = object: CountDownTimer(100000, 10) {
            override fun onTick(millisUntilFinished: Long) {
                date_time?.text = getDateTime()
            }
            override fun onFinish() {
                // do something
            }
        }
        timer.start()
    }
    @SuppressLint("SimpleDateFormat")
    fun getDateTime(): String{
       if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
           val currentDateTime = LocalDateTime.now()
           return buildString{
               append("Aktualna data: ")
               append(DateTimeFormatter.ofLocalizedDate(FormatStyle.MEDIUM).format(currentDateTime).toString())
               append("\n")
               append("Aktualny czas: ")
               append(DateTimeFormatter.ofLocalizedTime(FormatStyle.MEDIUM).format(currentDateTime).toString())
           }
        } else {
           return buildString {
               val dateFormat = SimpleDateFormat("yyyy-MM-dd HH:mm:ss")
               append( dateFormat.format(Date()))
           }
        }
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.menu_main, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        when (item.itemId) {
            R.id.action_accel -> {
                this.startActivity(Intent(this, AccelActivity::class.java))
                return true
            }
            R.id.action_gps -> {
                this.startActivity(Intent(this, GpsActivity::class.java))
                return true
            }
            R.id.action_gyro -> {
                this.startActivity(Intent(this, GyroActivity::class.java))
                return true
            }
            R.id.action_light -> {
                this.startActivity(Intent(this, LightActivity::class.java))
                return true
            }
            R.id.action_proximity -> {
                this.startActivity(Intent(this, ProxActivity::class.java))
                return true
            }
            else -> super.onOptionsItemSelected(item)
        }
        return true
    }

}

