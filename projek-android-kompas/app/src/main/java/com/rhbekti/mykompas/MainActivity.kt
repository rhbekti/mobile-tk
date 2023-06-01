package com.rhbekti.mykompas

import android.content.Context
import android.hardware.Sensor
import android.hardware.SensorEvent
import android.hardware.SensorEventListener
import android.hardware.SensorManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.animation.Animation
import android.view.animation.RotateAnimation
import android.widget.ImageView
import android.widget.TextView

@Suppress("DEPRECATION")
class MainActivity : AppCompatActivity(), SensorEventListener {
    private var image: ImageView? = null
    private var currentDegree = 90f
    private var mSensorManager: SensorManager? = null
    internal lateinit var tvHeading: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)


        image = findViewById(R.id.imageView) as ImageView
        tvHeading = findViewById(R.id.textView) as TextView
        mSensorManager = getSystemService(Context.SENSOR_SERVICE) as SensorManager
    }

    override fun onResume() {
        super.onResume()
        mSensorManager!!.registerListener(this,
            mSensorManager!!.getDefaultSensor(Sensor.TYPE_ORIENTATION),
            SensorManager.SENSOR_DELAY_GAME)
    }

    override fun onPause() {
        super.onPause()
        mSensorManager!!.unregisterListener(this)
    }

    override fun onSensorChanged(event: SensorEvent) {
        val degree = Math.round(event.values[0]).toFloat()

        tvHeading.text = "Heading: " + java.lang.Float.toString(degree) + " degrees"
        val ra = RotateAnimation(
            currentDegree, -degree,
            Animation.RELATIVE_TO_SELF, 0.5f,
            Animation.RELATIVE_TO_SELF,
            0.5f)

        ra.duration = 210
        ra.fillAfter = true

        image!!.startAnimation(ra)
        currentDegree = -degree
    }

    override fun onAccuracyChanged(sensor: Sensor, accuracy: Int) {}

}