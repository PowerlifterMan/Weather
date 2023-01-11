package com.example.weather

import android.content.pm.PackageManager
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import com.example.weather.presentation.main.MainFragment


class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        if (savedInstanceState == null) {
            supportFragmentManager.beginTransaction()
                .replace(R.id.container, MainFragment.newInstance())
                .commitNow()
        }
    }

    private fun checkPermitions(): Boolean {
        var allPermissioGranted = false
        if (ActivityCompat.checkSelfPermission(
                this,
                android.Manifest.permission.INTERNET
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            ActivityCompat.requestPermissions(this, arrayOf(android.Manifest.permission.INTERNET),
                INTERNET_REQUEST_CODE)
        }

        return allPermissioGranted
    }

    companion object {
        const val INTERNET_REQUEST_CODE = 11
    }
}