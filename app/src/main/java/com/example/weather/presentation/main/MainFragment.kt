package com.example.weather.presentation.main

import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import android.widget.SearchView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.MenuHost
import androidx.core.view.MenuProvider
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.setFragmentResultListener
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.weather.MainActivity
import com.example.weather.R
import com.example.weather.databinding.FragmentMainBinding
import com.example.weather.domain.CurrentCity
import com.example.weather.presentation.main.Settings2Fragment.Companion.CHECK_BOX_SOURCE_NINJAS
import com.example.weather.presentation.main.Settings2Fragment.Companion.CHECK_BOX_SOURCE_OPEN_METEO
import com.example.weather.presentation.main.Settings2Fragment.Companion.CHECK_BOX_SOURCE_OPEN_WEATHER
import com.example.weather.presentation.main.recyclerViews.ForecastAdapter
import com.example.weather.presentation.main.recyclerViews.ForecastAdapter.Companion.ITEM_VIEW_TYPE_ROW
import com.example.weather.presentation.main.recyclerViews.ForecastAdapter.Companion.ITEM_VIEW_TYPE_TITLE
import com.example.weather.presentation.main.recyclerViews.ForecastAdapter.Companion.MAX_ITEM_POOL_SIZE
import com.example.weather.presentation.main.recyclerViews.RecyclerViewRow
import com.example.weather.retrofit.daData.CityRvAdapter
import com.github.mikephil.charting.data.BarDataSet
import com.github.mikephil.charting.data.BarEntry
import com.github.mikephil.charting.data.Entry
import com.github.mikephil.charting.data.LineData
import com.github.mikephil.charting.data.LineDataSet
import dagger.android.support.AndroidSupportInjection
import java.text.SimpleDateFormat
import java.util.*
import javax.inject.Inject


class MainFragment : Fragment() {
    private val menuHost: MenuHost
        get() = requireActivity()
    private lateinit var viewModel: MainViewModel
    private val sourceList = mutableListOf<String>()
    private var _binding: FragmentMainBinding? = null
    private var currentSourceName: String = SOURCE_OPEN_WEATHER
    private val binding: FragmentMainBinding
        get() = _binding ?: throw RuntimeException("FragmentMainBinding? == null")
    private val lineEntry = mutableListOf<Entry>(Entry(20f, 0), Entry(31f, 1), Entry(34f, 2))
    private val lineDataset = LineDataSet(lineEntry, "firstLine")
    private var barChartEntries = mutableListOf<BarEntry>()
    private var barChartDataSet = BarDataSet(barChartEntries, BAR_DATA_SET_NAME1)
    val adapter = ForecastAdapter()
    var myCity = CurrentCity()
    private val cityListAdapter by lazy { CityRvAdapter() }

    @Inject
    lateinit var viemodelFactory: ViewModelFactory

    override fun onCreate(savedInstanceState: Bundle?) {
        AndroidSupportInjection.inject(this)
        super.onCreate(savedInstanceState)
        sourceList.add(SOURCE_OPEN_WEATHER)
        Log.e("MENU", "onCreate  $savedInstanceState")

        viewModel = ViewModelProvider(this, viemodelFactory).get(MainViewModel::class.java)
        viewModel.setCurrentCity(
            lat = 55.75f,
            lon = 37.61f,
            city = "Москва",
            cityKladr = "7700000000000"
        )
        viewModel.setDataSourceType(currentSourceName)
        viewModel.setListDataSource(sourceList)
        setFragmentResultListener("requestDataSource") { requestKey, bundle ->
            currentSourceName = bundle.getString("source") ?: SOURCE_OPEN_WEATHER
            viewModel.setDataSourceType(currentSourceName)
            viewModel.getForecastData(currentSourceName)
        }
        setFragmentResultListener(SETTING_FRAGMENT_DATA) { requestKey, bundle ->
            sourceList.clear()
            if (bundle.getBoolean(CHECK_BOX_SOURCE_NINJAS)) sourceList.add(SOURCE_NINJAS)
            if (bundle.getBoolean(CHECK_BOX_SOURCE_OPEN_WEATHER)) sourceList.add(SOURCE_OPEN_WEATHER)
            if (bundle.getBoolean(CHECK_BOX_SOURCE_OPEN_METEO)) sourceList.add(SOURCE_OPEN_METEO)
            Log.e("SETTING", sourceList.toString())
            if (sourceList.isNotEmpty()) {
                viewModel.setListDataSource(sourceList)
            }
        }
        setFragmentResultListener(DADATA_FRAGMENT_DATA) { requestKey, bundle ->
            val latitude = bundle.getString(LATITUDE_KEY)
            val longitude = bundle.getString(LONGITUDE_KEY)
            val cityName = bundle.getString(CITY_NAME_KEY)
            val cityKladrId = bundle.getString(CITY_KLADR_ID_KEY)
            viewModel.setCurrentCity(
                lat = latitude?.toFloatOrNull() ?: 0f,
                lon = longitude?.toFloatOrNull() ?: 0f,
                city = cityName ?: "",
                cityKladr = cityKladrId ?: ""
            )
            viewModel.getForecastDataCombine()
        }
        setFragmentResultListener(INPUT_PLACE_FRAGMENT_DATA) { requestKey, bundle ->
            val latitude = bundle.getString(LATITUDE_KEY)
            val longitude = bundle.getString(LONGITUDE_KEY)
            val cityName = bundle.getString(CITY_NAME_KEY)
            viewModel.setCurrentCity(
                lat = latitude?.toFloatOrNull() ?: 0f,
                lon = longitude?.toFloatOrNull() ?: 0f,
                city = cityName ?: "",
                cityKladr = ""
            )
            viewModel.getForecastDataCombine()
        }
    }

    @SuppressLint("SetTextI18n")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        Log.e("MENU", "onViewCreated  $savedInstanceState")

        setupMenu()

        lineDataset.color = resources.getColor(R.color.grey_blue)
//    val chartData = LineData(barChartLabels,lineDataset )
        val forecastList = viewModel.getForecast()
        val cityRecyclerView = binding.rvCitySelection
        cityRecyclerView.apply {
            adapter = cityListAdapter
            layoutManager = LinearLayoutManager(requireContext(), RecyclerView.VERTICAL, false)
        }
        val city = viewModel.myCityName
        val currentWeather = viewModel.getCurrentWeather()
        val dataSourceTypeLD = viewModel.dataSourceType
//        viewModel.getForecastData(currentSourceName)
        viewModel.getForecastDataCombine()
        val lineDataLD = viewModel.chartLineData
        val barDataLD = viewModel.chartBarData
//    menuHost.apply
//    { setSupportActionBar(binding.frMainToolbar)
//        supportActionBar?.setDisplayHomeAsUpEnabled(true)
////            onCreateOptionsMenu()
//    }

        binding.fragMainBarChart.data = viewModel.chartBarData.value
        with(binding)
        {
            frMainToolbar.apply {
                title = city.value
//                inflateMenu(R.menu.fr_main_toolbar_menu)
            }

            cardView.setBackgroundResource(R.drawable.low_cloud_cover)
            tvLocation.setOnClickListener {
                myCity = getNewCity()
            }
            btnChangeSettings.setOnClickListener {
                changeSettings()
            }
            forecastRV.layoutManager =
                LinearLayoutManager(requireActivity(), RecyclerView.VERTICAL, false)
            forecastRV.recycledViewPool.setMaxRecycledViews(
                ITEM_VIEW_TYPE_TITLE,
                MAX_ITEM_POOL_SIZE
            )
            forecastRV.recycledViewPool.setMaxRecycledViews(ITEM_VIEW_TYPE_ROW, MAX_ITEM_POOL_SIZE)
            forecastRV.adapter = adapter
        }
        adapter.onItemClickListener =
            object : ForecastAdapter.OnItemClickListener {
                override fun itemClick(item: RecyclerViewRow) {
                    super.itemClick(item)
                    changeCurrentDayInfo(item)
                }


            }
        forecastList.observe(viewLifecycleOwner)
        {
            adapter.submitList(it)
            binding.forecastRV.invalidate()
            viewModel.setLineChartData()
        }
        viewModel.cityListLD.observe(viewLifecycleOwner) {
            cityListAdapter.submitList(it)
        }
        city.observe(viewLifecycleOwner)
        {
            binding.tvLocation.text = it
            binding.frMainToolbar.title = it
        }

        lineDataLD.observe(viewLifecycleOwner)
        { it ->
//            binding.fragMainBarChart.data = it
        }
        barDataLD.observe(viewLifecycleOwner)
        {
            binding.fragMainBarChart.data = it
            binding.fragMainBarChart.invalidate()

        }

        currentWeather.observe(viewLifecycleOwner)
        {
            binding.tvCurrentLocation.text = currentSourceName
            binding.tvCurrentTemp.text = "${(Math.round(it.temp * 10) / 10).toString()} °C"
            binding.tvCaption.text =
                "ощущается как ${(Math.round(it.tempFeelsLike * 10) / 10).toString()} °С"
        }
    }

    private fun setupMenu() {
        Log.e("MENU", "fun setupMenu")
        (menuHost as MainActivity).setSupportActionBar(binding.frMainToolbar)
        menuHost.addMenuProvider(object : MenuProvider {
            // Добавляем MenuProvider

            override fun onPrepareMenu(menu: Menu) // Вызывается перед отрисовкой меню
            {
                Log.e("MENU", "onPrepareMenu  $menu")
            }

            override fun onCreateMenu(menu: Menu, menuInflater: MenuInflater) {
                // Надуваем fragment_menu и мержим с прошлым menu
                menuInflater.inflate(R.menu.fr_main_toolbar_menu, menu)
                val searchItem = menu.findItem(R.id.app_bar_search)
                Log.e("MENU", "ssearchItem    $searchItem")
                // getting search view of our item.
                // getting search view of our item.
                val searchView = searchItem.actionView as SearchView
                Log.e("MENU", "searchView    $searchView")
                searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
                    override fun onQueryTextSubmit(query: String?): Boolean {
                        viewModel.onSearchTextChanged(query ?: "")
                        Log.e("MENU", "setOnQueryTextListener   onQueryTextSubmit $query ")
                        return true
                    }

                    override fun onQueryTextChange(newText: String?): Boolean {
                        Log.e(
                            "MENU",
                            newText ?: "setOnQueryTextListener   onQueryTextChange $newText"
                        )
                        if (newText != null && newText.length > 3) {
                            binding.rvCitySelection.isVisible = true
                            viewModel.onSearchTextChanged(newText)

                        }
                        return true
                    }
                })
            }

            override fun onMenuItemSelected(menuItem: MenuItem): Boolean {
                Log.e("MENU", "MENU ITEM TAP")
                when (menuItem.itemId) {
                    R.id.app_bar_search -> {
                        Log.e("MENU", "MENU onMenuItemSelected $menuItem")
                    }

                    R.id.menu_settings -> {
                        Log.e("MENU", "MENU onMenuItemSelected  $menuItem")
                    }
                }
                // Пользователь кликнул на элемент меню
                // return true — не нужно передавать нажатие другому провайдеру
                // return false — передаем нажатие следующему провайдеру
                return true
            }

            override fun onMenuClosed(menu: Menu) // Меню закрыто
            {
                Log.e("MENU", "MENU onMenuClosed")

            }
        }, viewLifecycleOwner, Lifecycle.State.CREATED)
    }


    private fun changeSettings() {
        /* старое**
        findNavController().navigate(R.id.action_mainFragment_to_settingsFragment)
        */
        findNavController().navigate(R.id.action_mainFragment_to_settings2Fragment)
    }

    private fun getNewCity(): CurrentCity {
        findNavController().navigate(R.id.action_mainFragment_to_dadataFragment)
//        findNavController().navigate(R.id.action_mainFragment_to_inputPlaceFragment)
        return myCity
    }

//    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
//        super.onCreateOptionsMenu(menu, inflater)
//        menu.clear()
//        inflater.inflate(R.menu.fr_main_toolbar_menu, menu)
//        val item = menu.findItem(R.id.app_bar_search)
//        val searchView = SearchView(requireContext())
//        item.actionView = searchView
//        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
//            override fun onQueryTextSubmit(query: String): Boolean {
//                return true
//            }
//
//            override fun onQueryTextChange(newText: String): Boolean {
//                viewModel.onSearchTextChanged(newText)
//                return true
//            }
//        })
//    }

    private fun changeCurrentDayInfo(item: RecyclerViewRow) {
        Toast.makeText(requireActivity(), "PRESSED", Toast.LENGTH_SHORT).show()
        binding.tvCurrentLocation.text = "CLICKED"
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentMainBinding.inflate(inflater, container, false)

        return binding.root
    }

    private fun getDateTime(s: Long): String? {
        try {
            val sdf = SimpleDateFormat("yyyy/MM/dd")
            val netDate = Date(s * 1000)
            return sdf.format(netDate)
        } catch (e: Exception) {
            return e.toString()
        }

    }

    companion object {
        fun newInstance() = MainFragment()
        const val BAR_DATA_SET_NAME1 = "AVERAGE TEMP"
        const val SETTING_FRAGMENT_DATA = "data_from_settings_fragment"
        const val DADATA_FRAGMENT_DATA = "data_from_dadata_fragment"
        const val INPUT_PLACE_FRAGMENT_DATA = "data_from_input_place_fragment"
        const val LONGITUDE_KEY = "lon"
        const val LATITUDE_KEY = "lat"
        const val CITY_NAME_KEY = "city"
        const val CITY_KLADR_ID_KEY = "cityKladrId"
        const val OPEN_WEATHER_API_KEY = "5d1db59ebde74404687cd4a218b50859"
    }
}
