package com.rhbekti.scanwifi1

import android.Manifest
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
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
import java.util.ArrayList

class MainActivity : AppCompatActivity() {

    private var wifiManager: WifiManager? = null
    private var listView: ListView? = null
    private var buttonScan: Button? = null
    private val size = 0
    private var results: List<ScanResult>? = null
    private val arrayList = ArrayList<String>()
    private var adapter: ArrayAdapter<*>? = null

    internal var wifiReceiver: BroadcastReceiver = object : BroadcastReceiver() {
        override fun onReceive(context: Context, intent: Intent) {
            if (ActivityCompat.checkSelfPermission(
                    this@MainActivity,
                    Manifest.permission.ACCESS_FINE_LOCATION
                ) != PackageManager.PERMISSION_GRANTED
            ) {
                // TODO: Consider calling
                //    ActivityCompat#requestPermissions
                // here to request the missing permissions, and then overriding
                //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
                //                                          int[] grantResults)
                // to handle the case where the user grants the permission. See the documentation
                // for ActivityCompat#requestPermissions for more details.
                return
            }
            results = wifiManager!!.scanResults
            unregisterReceiver(this)

            for (scanResult in results!!) {
                arrayList.add(scanResult.SSID + " - " + scanResult.capabilities)
                adapter!!.notifyDataSetChanged()
            }
        }
    }

    public override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        buttonScan = findViewById(R.id.scanBtn)
        buttonScan!!.setOnClickListener { scanWifi() }

        listView = findViewById(R.id.wifiList)
        wifiManager = applicationContext.getSystemService(Context.WIFI_SERVICE) as WifiManager

        if (!wifiManager!!.isWifiEnabled) {
            Toast.makeText(this, "WiFi is disabled ... We need to enable it", Toast.LENGTH_LONG).show()
            wifiManager!!.isWifiEnabled = true
        }

        adapter = ArrayAdapter(this, android.R.layout.simple_list_item_1, arrayList)
        listView!!.adapter = adapter
        scanWifi()
    }

    private fun scanWifi() {
        arrayList.clear()
        registerReceiver(wifiReceiver, IntentFilter(WifiManager.SCAN_RESULTS_AVAILABLE_ACTION))
        wifiManager!!.startScan()
        Toast.makeText(this, "Scanning WiFi ...", Toast.LENGTH_SHORT).show()
    }
}