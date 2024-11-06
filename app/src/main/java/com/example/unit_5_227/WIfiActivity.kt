package com.example.unit_5_227


import android.Manifest
import android.annotation.SuppressLint
import android.content.Context
import android.content.pm.PackageManager
import android.net.wifi.ScanResult
import android.net.wifi.WifiManager
import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.ListView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat

class WIfiActivity : AppCompatActivity() {

    private lateinit var lstvw: ListView
    private var aAdapter: ArrayAdapter<*>? = null
    private lateinit var wifiManager: WifiManager

    companion object {
        const val REQUEST_CODE_WIFI = 2
    }

    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_wifi)

        val btn: Button = findViewById(R.id.btnget)

        wifiManager = applicationContext.getSystemService(Context.WIFI_SERVICE) as WifiManager

        
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)
            != PackageManager.PERMISSION_GRANTED
        ) {
            ActivityCompat.requestPermissions(
                this,
                arrayOf(Manifest.permission.ACCESS_FINE_LOCATION),
                REQUEST_CODE_WIFI
            )
        }

        btn.setOnClickListener {
            if (!wifiManager.isWifiEnabled) {
                Toast.makeText(
                    applicationContext,
                    "Wi-Fi is not enabled. Please enable it to scan networks.",
                    Toast.LENGTH_SHORT
                ).show()
            } else {
                if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)
                    == PackageManager.PERMISSION_GRANTED
                ) {
                    scanWifiNetworks()
                } else {
                    Toast.makeText(this, "Location Permission is Required", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }

    @SuppressLint("MissingPermission")
    private fun scanWifiNetworks() {
        val wifiScanResults: List<ScanResult> = wifiManager.scanResults
        val wifiList = ArrayList<String>()

        if (wifiScanResults.isNotEmpty()) {
            for (scanResult in wifiScanResults) {
                val ssid = scanResult.SSID
                val bssid = scanResult.BSSID
                wifiList.add("SSID: $ssid, BSSID: $bssid")
            }

            lstvw = findViewById(R.id.deviceList)
            aAdapter = ArrayAdapter(this, android.R.layout.simple_list_item_1, wifiList)
            lstvw.adapter = aAdapter
        } else {
            Toast.makeText(this, "No Wi-Fi networks found", Toast.LENGTH_SHORT).show()
        }
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == REQUEST_CODE_WIFI && grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
            Toast.makeText(this, "Permission granted", Toast.LENGTH_SHORT).show()
        } else {
            Toast.makeText(this, "Permission denied", Toast.LENGTH_SHORT).show()
        }
    }
}