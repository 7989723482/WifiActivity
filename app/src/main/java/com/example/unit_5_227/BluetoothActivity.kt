package com.example.unit_5_227

import android.Manifest
import android.bluetooth.BluetoothAdapter
import android.content.pm.PackageManager
import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.ListView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat

class BluetoothActivity : AppCompatActivity() {

    private lateinit var lstvw: ListView
    private var aAdapter: ArrayAdapter<*>? = null
    private val bAdapter = BluetoothAdapter.getDefaultAdapter()

    companion object {
        const val REQUEST_CODE_BLUETOOTH_CONNECT = 1
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_bluetooth)

        val btn: Button = findViewById(R.id.btnget)

        if (ContextCompat.checkSelfPermission(this, Manifest.permission.BLUETOOTH_CONNECT)
            != PackageManager.PERMISSION_GRANTED 
        ) {
            ActivityCompat.requestPermissions(
                this,
                arrayOf(Manifest.permission.BLUETOOTH_CONNECT), REQUEST_CODE_BLUETOOTH_CONNECT
            )
        }

        btn.setOnClickListener {
            if (bAdapter == null) {
                Toast.makeText(
                    applicationContext,
                    "Bluetooth is not available", Toast.LENGTH_SHORT).show()
            } else {
                if (ContextCompat.checkSelfPermission(this,Manifest.permission.BLUETOOTH_CONNECT)
                    == PackageManager.PERMISSION_GRANTED){
                    val pairedDevices = bAdapter.bondedDevices
                    val deviceList = ArrayList<String>()
                    if (pairedDevices.isNotEmpty()){
                        for (device in pairedDevices){
                            val deviceName = device.name
                            val macAddress = device.address
                            deviceList.add("Name: $deviceName, Address: $macAddress")

                        }
                        lstvw = findViewById(R.id.deviceList)
                        aAdapter = ArrayAdapter(this, android.R.layout.simple_list_item_1, deviceList)
                        lstvw.adapter = aAdapter
                    }
                    else{
                        Toast.makeText(this, "No paired devices found", Toast.LENGTH_SHORT).show()
                    }
                }
                else{
                    Toast.makeText(this, "Bluetooth Permission is Required", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == REQUEST_CODE_BLUETOOTH_CONNECT){
        }
    }
}