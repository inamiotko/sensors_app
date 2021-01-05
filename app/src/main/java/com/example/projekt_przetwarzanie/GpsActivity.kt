package com.example.projekt_przetwarzanie

import android.Manifest
import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.location.Location
import android.location.LocationListener
import android.location.LocationManager
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.Settings
import android.widget.TextView
import android.widget.Toast
import androidx.lifecycle.MutableLiveData
import retrofit2.Response

private const val PERMISSION_REQUEST = 10

class GpsActivity : AppCompatActivity() {
    //klasa odpowiedzialna za wykonywanie pomiarów GPS
    //wymaganie zgody użytkownika na dostęp do lokalizacji urządzenia
    //pobieranie długości i szerokości geograficznej (w zależności od dostępności,
    //z czujników urządzenia lub z informacji sieciowej)
    private var viewModel = AppViewModel()
    lateinit var locationManager: LocationManager
    private var hasGps = false
    private var hasNetwork = false
    private var locationGps: Location? = null
    private var locationNetwork: Location? = null
    private var mTextGPS: TextView? = null
    private var permissions = arrayOf(Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_gps)
        mTextGPS = findViewById(R.id.gps_text)
        //sprawdzenie czy jest ospowiednia wersja systemu
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (checkPermission(permissions)) {
                getLocation()
            } else {
                requestPermissions(permissions, PERMISSION_REQUEST)
            }
        } else {

        }
    }

    @SuppressLint("MissingPermission")
    private fun getLocation(){
        //funkcja od pobierania lokalizacji
        locationManager = getSystemService(Context.LOCATION_SERVICE) as LocationManager
        hasGps = locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)
        hasNetwork = locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER)
        if (hasGps || hasNetwork) {
            if (hasGps) {
                locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 3000, 0F, object : LocationListener {
                override fun onLocationChanged(location: Location) {
                    if (location != null) {
                        locationGps = location
                        val builder = StringBuilder()
                        val lat = location.latitude
                        val lng = location.longitude
                        builder.append("Szerokość geograficzna: ")
                        builder.append(lat)
                        builder.append(" stopni")
                        builder.append("\n")
                        builder.append("Długość geograficzna: ")
                        builder.append(lng)
                        builder.append(" stopni")
                        mTextGPS?.text = builder.toString()
                    }
                }
                override fun onStatusChanged(provider: String?, status: Int, extras: Bundle?) {
                }
                override fun onProviderEnabled(provider: String) {
                }
                override fun onProviderDisabled(provider: String) {
                }
                })
                val localGpsLocation = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER)
                if (localGpsLocation != null)
                    locationGps = localGpsLocation
            }
            if (hasNetwork) {
                locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 5000, 0F, object : LocationListener {
                override fun onLocationChanged(location: Location) {
                    if (location != null) {
                        locationGps = location
                        val builder = StringBuilder()
                        val lat = location.latitude
                        val lng = location.longitude
                        builder.append("Szerokość geograficzna: ")
                        builder.append(lat)
                        builder.append(" stopni")
                        builder.append("\n")
                        builder.append("Długość geograficzna: ")
                        builder.append(lng)
                        builder.append(" stopni")
                        mTextGPS?.text = builder.toString()
                    }
                }
                override fun onStatusChanged(provider: String?, status: Int, extras: Bundle?) {
                }
                override fun onProviderEnabled(provider: String) {
                }
                override fun onProviderDisabled(provider: String) {
                }
            })
                val localNetworkLocation = locationManager.getLastKnownLocation(LocationManager.NETWORK_PROVIDER)
                if (localNetworkLocation != null)
                    locationNetwork = localNetworkLocation
            }
            if (locationGps != null && locationNetwork != null) {
                if (locationGps!!.accuracy > locationNetwork!!.accuracy) {
                    val builder = StringBuilder()
                    val lat = locationGps?.latitude
                    val lng = locationGps?.longitude
                    builder.append("Szerokość geograficzna: ")
                    builder.append(lat)
                    builder.append(" stopni")
                    builder.append("\n")
                    builder.append("Długość geograficzna: ")
                    builder.append(lng)
                    builder.append(" stopni")
                    mTextGPS?.text = builder.toString()
                } else {
                    val builder = StringBuilder()
                    val lat = locationGps?.latitude
                    val lng = locationGps?.longitude
                    builder.append("Szerokość geograficzna: ")
                    builder.append(lat)
                    builder.append(" stopni")
                    builder.append("\n")
                    builder.append("Długość geograficzna: ")
                    builder.append(lng)
                    builder.append(" stopni")
                    mTextGPS?.text = builder.toString()
                }
            }
            val appDataDTO = AppDataDTO(latitude = locationGps?.latitude.toString(), longitude = locationGps?.longitude.toString())
            viewModel.createLocalization(appDataDTO)

        } else {
            startActivity(Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS))
        }
    }
    private fun checkPermission(permissionArray: Array<String>): Boolean {
        //funkcja do sprawdzenia czy uzytkownik wyraził zgodę
        var allSuccess = true
        for (i in permissionArray.indices) {
            if (checkCallingOrSelfPermission(permissionArray[i]) == PackageManager.PERMISSION_DENIED)
                allSuccess = false
        }
        return allSuccess
    }
    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray){
        //w przypadku braku zgody nalezy ją dać w ustawieniach
        //stosowne komunikaty o stanie dostepu do lokalizacji
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == PERMISSION_REQUEST) {
            var allSuccess = true
            for (i in permissions.indices) {
                if (grantResults[i] == PackageManager.PERMISSION_DENIED) {
                    allSuccess = false
                    val requestAgain = Build.VERSION.SDK_INT >= Build.VERSION_CODES.M && shouldShowRequestPermissionRationale(permissions[i])
                    if (requestAgain) {
                        Toast.makeText(this, "Permission denied", Toast.LENGTH_SHORT).show()
                    } else {
                        Toast.makeText(this, "Go to settings and enable the permission", Toast.LENGTH_SHORT).show()
                    }
                }
            }
        }
    }
}
