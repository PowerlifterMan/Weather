package com.example.weather.presentation.main.composeUI

import android.annotation.SuppressLint
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.bumptech.glide.Glide
import com.example.weather.R
import com.example.weather.domain.CurrentCity
import com.example.weather.domain.DEFAULT_KLADR_ID
import com.example.weather.domain.DEFAULT_LOCATION_NAME
import com.example.weather.domain.TempOnTime
import com.example.weather.presentation.main.MainViewModel
import com.example.weather.presentation.main.SOURCE_OPEN_WEATHER
import com.example.weather.presentation.main.recyclerViews.RecyclerViewItem
import com.example.weather.presentation.main.recyclerViews.RecyclerViewItemTitle
import com.example.weather.presentation.main.recyclerViews.RecyclerViewRow
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Date

@OptIn(ExperimentalMaterial3Api::class)
@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun MainScreenCompose(
    viewModel: MainViewModel
) {
    val sdf = SimpleDateFormat("dd MMM yyyy")
    val currentDate = sdf.format(Date())
    val liist = viewModel.rvRow.observeAsState(listOf<RecyclerViewRow>())
    val cityCurrentWeather = viewModel.getCurrentWeather()
        .observeAsState(
            TempOnTime(
                timestamp = Calendar.getInstance().timeInMillis,
                temp = 20f,
                tempFeelsLike = 20f
            )
        )
    val currentCity = viewModel.currentCityLD.observeAsState(CurrentCity())
    viewModel.listDataSourceIsChanged(listOf(SOURCE_OPEN_WEATHER))
    viewModel.currentCityIsChanged(
        lat = 55.75f,
        lon = 37.61f,
        city = DEFAULT_LOCATION_NAME,
        cityKladr = DEFAULT_KLADR_ID
    )
    val scrollState = rememberScrollState()
    Scaffold(
        modifier = Modifier,
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
//        bottomBar = {
//            BottomNavigation() {
//                val navItems = listOf(
//                    NavigationItem.Home,
//                    NavigationItem.Favourite,
//                    NavigationItem.Profile
//                )
//                navItems.forEach { item ->
//                    BottomNavigationItem(
//                        selected = false,
//                        onClick = { },
//                        icon = {
//                            Icon(item.icon, contentDescription = null)
//                        },
//                        label = {
//                            androidx.compose.material.Text(text = stringResource(id = item.titleResId))
//                        },
//                        selectedContentColor = MaterialTheme.colors.onPrimary,
//                        unselectedContentColor = MaterialTheme.colors.onSecondary
//                    )
//
//                }
//
//            }
//        }
    ) { paddingValues ->
        ShowCityCard(
            cityName = currentCity.value.name ?: " ",
            temp = cityCurrentWeather.value.temp.toString()
        )

        LazyColumn(
            modifier = Modifier.padding(
                PaddingValues(
                    top = 250.dp,
                    start = 8.dp,
                    end = 8.dp,
                    bottom = 10.dp
                )
            ),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.SpaceEvenly,
        ) {
            items(liist.value) { rowItem ->
                when (rowItem) {
                    is RecyclerViewItem -> {
                        ShowCard(rowItem)
                    }

                    is RecyclerViewItemTitle -> {
                        Spacer(modifier = Modifier.height(16.dp))
                        Text(text = rowItem.title)
                    }

                    else -> {
                        Text(text = "rowItem.toString()")
//                        throw RuntimeException("ОШИБОЧКА ВЫШЛА")
                    }
                }

//                Text(text = (rowItem as RecyclerViewItem).description)
            }

        }
    }
}

@Composable
fun ShowCityCard(cityName: String, temp: String) {
    /*
      Spacer(
            modifier = Modifier
                .height(8.dp)
                .background(Color.LightGray)
        )
     */
    Card(
        shape = RoundedCornerShape(12.dp),
        border = BorderStroke(width = 1.dp, color = Color.Gray),
        modifier = Modifier
            .padding(start = 8.dp, top = 70.dp, end = 8.dp)
            .fillMaxWidth()
            .height(170.dp)
//            .background()


    ) {
        Box() {

            Image(
                modifier = Modifier.fillMaxSize(),
                painter = painterResource(id = R.drawable.low_cloud_cover),
                contentDescription = " CONTENT DESCRIPTION ",
                contentScale = ContentScale.FillBounds
            )
            Text(
                fontSize = 32.sp,
                text = "$cityName",
                modifier = Modifier
                    .padding(vertical = 30.dp)
                    .fillMaxWidth(),
                textAlign = TextAlign.Center,
                color = Color.Black
            )
            Spacer(modifier = Modifier.height(30.dp))
            Text(
                fontSize = 32.sp,
                text = "$temp",
                modifier = Modifier
//                .padding(30.dp)
                    .fillMaxWidth(),
                textAlign = TextAlign.Center,
                color = Color.Black
            )

        }

    }
}


@Preview
@Composable
fun ShowCard(item: RecyclerViewItem = RecyclerViewItem()) {
    val name = "SIMPLE EXAMPLE"
    Spacer(
        Modifier
            .height(8.dp)
            .background(Color.LightGray)
    )
    Card(
        modifier = Modifier
            .height(60.dp)
//            .width(300.dp)
            .fillMaxWidth(),

//            .padding(8.dp)
        elevation = CardDefaults.cardElevation(10.dp),
        shape = RoundedCornerShape(12.dp),
        border = BorderStroke(1.dp, color = Color.DarkGray),
    ) {
        if (item.pictureUrl?.isNotEmpty() == true){

            GlideImage("https://openweathermap.org/img/wn/${item.pictureUrl}@2x.png")
        }
        Text(
            fontSize = 16.sp,
            text = "${item.dayNumber}  ${item.temperature} ${item.description}",
            textAlign = TextAlign.Center,
            overflow = TextOverflow.Ellipsis,
            modifier = Modifier.padding(16.dp)
        )
    }
}
