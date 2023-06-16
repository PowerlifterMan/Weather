package com.example.weather

import android.app.Activity
import android.content.pm.PackageManager
import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
import androidx.core.app.ActivityCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.example.weather.presentation.main.MainFragment
import com.example.weather.presentation.main.MainViewModel
import com.example.weather.presentation.main.ViewModelFactory
import com.example.weather.presentation.main.composeUI.MainScreenCompose
import dagger.android.AndroidInjection
import dagger.android.AndroidInjector
import dagger.android.HasAndroidInjector
import dagger.android.support.AndroidSupportInjection
import dagger.android.support.DaggerAppCompatActivity
import javax.inject.Inject


class MainActivity : AppCompatActivity() {
    @Inject
    lateinit var viemodelFactory: ViewModelFactory
    override fun onCreate(savedInstanceState: Bundle?) {
     super.onCreate(savedInstanceState)
        val viewModelMain =ViewModelProvider(this, viemodelFactory).get(MainViewModel::class.java)
//        viewModel = ViewModelProvider(this, viemodelFactory).get(MainViewModel::class.java)
//        setContentView(R.layout.activity_main)
        setContent {
            Surface(
                modifier = Modifier.fillMaxSize(),
                color = MaterialTheme.colorScheme.background
            ) {
                MainScreenCompose(viewModelMain)
            }
        }
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