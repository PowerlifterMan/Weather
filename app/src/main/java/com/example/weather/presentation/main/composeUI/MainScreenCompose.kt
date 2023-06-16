package com.example.weather.presentation.main.composeUI

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.example.weather.domain.DEFAULT_KLADR_ID
import com.example.weather.domain.DEFAULT_LOCATION_NAME
import com.example.weather.presentation.main.MainViewModel
import com.example.weather.presentation.main.SOURCE_OPEN_WEATHER
import com.example.weather.presentation.main.recyclerViews.RecyclerViewItem
import com.example.weather.presentation.main.recyclerViews.RecyclerViewItemTitle
import java.lang.RuntimeException

@OptIn(ExperimentalMaterial3Api::class)
@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun MainScreenCompose(
    viewModel: MainViewModel
) {
    val liist = viewModel.rvRow.observeAsState(listOf())
    viewModel.listDataSourceIsChanged(listOf(SOURCE_OPEN_WEATHER))
    viewModel.currentCityIsChanged(
        lat = 55.75f,
        lon = 37.61f,
        city = DEFAULT_LOCATION_NAME,
        cityKladr = DEFAULT_KLADR_ID
    )
    val scrollState = rememberScrollState()
    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(text = "Top App Bar")
                },
                navigationIcon = {
                    IconButton(onClick = {}) {
                        Icon(Icons.Filled.ArrowBack, "backIcon")
                    }
                },
            )
        },
    ) {
        LazyColumn(
            modifier = Modifier
                .verticalScroll(scrollState)
                .fillMaxWidth(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.SpaceEvenly,
        ) {
            items(liist.value) { rowItem ->

                when (rowItem) {
                    is RecyclerViewItem -> {
                        Text(text = rowItem.toString())
                    }

                    is RecyclerViewItemTitle -> {
                        Text(text = rowItem.toString())
                    }

                    else -> {
                        throw RuntimeException("ОШИБОЧКА ВЫШЛА")
                    }
                }

                Text(text = (rowItem as RecyclerViewItem).description)
            }

        }
    }
}

