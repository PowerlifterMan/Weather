package com.example.weather

import android.app.Activity
import android.content.pm.PackageManager
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.fragment.app.Fragment
import com.example.weather.presentation.main.MainFragment
import dagger.android.AndroidInjection
import dagger.android.AndroidInjector
import dagger.android.HasAndroidInjector
import dagger.android.support.DaggerAppCompatActivity
import javax.inject.Inject


class MainActivity : AppCompatActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
//        val toolbar = fi
//        setSupportActionBar(findViewById(R.id.toolbar2))


        //        if (savedInstanceState == null) {
//            supportFragmentManager.beginTransaction()
//                .replace(R.id.main_container, MainFragment())
//                .commitNow()
//        }
    }

    private fun checkPermitions(): Boolean {
        var allPermissioGranted = false
        if (ActivityCompat.checkSelfPermission(
                this,
                android.Manifest.permission.INTERNET
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            ActivityCompat.requestPermissions(
                this, arrayOf(android.Manifest.permission.INTERNET),
                INTERNET_REQUEST_CODE
            )
        }

        return allPermissioGranted
    }

    companion object {
        const val INTERNET_REQUEST_CODE = 11
    }

}