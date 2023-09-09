package com.example.broadcastreceiver

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.os.BatteryManager
import android.os.Bundle
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {

    private lateinit var batteryPercentageTextView: TextView
    private lateinit var batteryStatusTextView: TextView
    private lateinit var batteryTemperatureTextView: TextView
    private lateinit var batteryVoltageTextView: TextView
    private lateinit var batteryHealthTextView: TextView

    private val batteryReceiver = object : BroadcastReceiver() {
        override fun onReceive(context: Context?, intent: Intent?) {
            if (intent?.action == Intent.ACTION_BATTERY_CHANGED) {
                val level = intent.getIntExtra(BatteryManager.EXTRA_LEVEL, -1)
                val scale = intent.getIntExtra(BatteryManager.EXTRA_SCALE, -1)
                val batteryPercentage = (level.toFloat() / scale.toFloat()) * 100
                val batteryStatus = when (intent.getIntExtra(BatteryManager.EXTRA_STATUS, -1)) {
                    BatteryManager.BATTERY_STATUS_CHARGING -> "Charging"
                    BatteryManager.BATTERY_STATUS_DISCHARGING -> "Discharging"
                    BatteryManager.BATTERY_STATUS_NOT_CHARGING -> "Not Charging"
                    BatteryManager.BATTERY_STATUS_FULL -> "Full"
                    else -> "Unknown"
                }

                val temperature = intent.getIntExtra(BatteryManager.EXTRA_TEMPERATURE, -1)
                val temperatureCelsius = temperature / 10f //
                val voltage = intent.getIntExtra(
                    BatteryManager.EXTRA_VOLTAGE,
                    -1
                ) / 1000f

                val batteryHealth = when (intent.getIntExtra(BatteryManager.EXTRA_HEALTH, -1)) {
                    BatteryManager.BATTERY_HEALTH_GOOD -> "Good"
                    BatteryManager.BATTERY_HEALTH_OVERHEAT -> "Overheat"
                    BatteryManager.BATTERY_HEALTH_DEAD -> "Dead"
                    BatteryManager.BATTERY_HEALTH_OVER_VOLTAGE -> "Over Voltage"
                    BatteryManager.BATTERY_HEALTH_UNSPECIFIED_FAILURE -> "Unspecified Failure"
                    BatteryManager.BATTERY_HEALTH_COLD -> "Cold"
                    else -> "Unknown"
                }
                val batteryPercentageText =
                    getString(R.string.battery_percentage, batteryPercentage.toInt())
                val batteryStatusText = getString(R.string.battery_status, batteryStatus)
                val batteryTemperatureText =
                    getString(R.string.battery_temperature, temperatureCelsius)
                val batteryVoltageText = getString(R.string.battery_voltage, voltage)
                val batteryHealthText = getString(R.string.battery_health, batteryHealth)

                batteryPercentageTextView.text = batteryPercentageText
                batteryStatusTextView.text = batteryStatusText
                batteryTemperatureTextView.text = batteryTemperatureText
                batteryVoltageTextView.text = batteryVoltageText
                batteryHealthTextView.text = batteryHealthText
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        batteryPercentageTextView = findViewById(R.id.batteryPercentageTextView)
        batteryStatusTextView = findViewById(R.id.batteryStatusTextView)
        batteryTemperatureTextView = findViewById(R.id.batteryTemperatureTextView)
        batteryVoltageTextView = findViewById(R.id.batteryVoltageTextView)
        batteryHealthTextView = findViewById(R.id.batteryHealthTextView)
    }

    override fun onResume() {
        super.onResume()
        val filter = IntentFilter(Intent.ACTION_BATTERY_CHANGED)
        registerReceiver(batteryReceiver, filter)
    }

    override fun onPause() {
        super.onPause()
        unregisterReceiver(batteryReceiver)
    }
}