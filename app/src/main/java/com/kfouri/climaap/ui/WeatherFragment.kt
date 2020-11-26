package com.kfouri.climaap.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.kfouri.climaap.GlideApp
import com.kfouri.climaap.R
import com.kfouri.climaap.adapter.WeatherDaysAdapter
import com.kfouri.climaap.databinding.ActivityWeatherBinding
import com.kfouri.climaap.model.WeatherResponse
import com.kfouri.climaap.network.weather.ApiWeatherBuilder
import com.kfouri.climaap.network.weather.ApiWeatherHelper
import com.kfouri.climaap.utils.Status
import com.kfouri.climaap.viewmodel.ViewModelFactory
import com.kfouri.climaap.viewmodel.WeatherActivityViewModel
import java.text.SimpleDateFormat
import java.util.*

class WeatherFragment : Fragment() {

    private val args: WeatherFragmentArgs by navArgs()
    private lateinit var viewModel: WeatherActivityViewModel
    private lateinit var binding: ActivityWeatherBinding
    private lateinit var adapter: WeatherDaysAdapter
    private var cityLat: Double = 0.0
    private var cityLon: Double = 0.0
    private var cityName: String = "No Name"
        
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        viewModel = ViewModelProvider(this, ViewModelFactory(ApiWeatherHelper(ApiWeatherBuilder.apiWeatherService))).get(WeatherActivityViewModel::class.java)
        binding = DataBindingUtil.inflate(inflater, R.layout.activity_weather, container, false)

        cityLat = args.LAT.toDouble()
        cityLon = args.LON.toDouble()
        cityName = args.NAME

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        activity?.title = getString(R.string.weather_title, cityName)
        activity?.actionBar?.setDisplayHomeAsUpEnabled(true)

        activity?.applicationContext?.let { adapter = WeatherDaysAdapter(it) }

        setRecyclerView()

        viewModel.getWeatherByLatLon(cityLat, cityLon).observe(viewLifecycleOwner, Observer {
            it?.let { resource ->
                when (resource.status) {
                    Status.SUCCESS -> {
                        binding.recyclerViewWeatherDays.visibility = View.VISIBLE
                        binding.progressBar.visibility = View.GONE

                        resource.data?.let { weather -> showWeather(weather) }
                    }
                    Status.ERROR -> {
                        binding.recyclerViewWeatherDays.visibility = View.VISIBLE
                        binding.progressBar.visibility = View.GONE
                        Toast.makeText(activity, it.message, Toast.LENGTH_LONG).show()
                    }
                    Status.LOADING -> {
                        binding.progressBar.visibility = View.VISIBLE
                        binding.recyclerViewWeatherDays.visibility = View.GONE
                    }
                }
            }
        })
    }

    private fun setRecyclerView() {
        binding.recyclerViewWeatherDays.setHasFixedSize(true)
        binding.recyclerViewWeatherDays.addItemDecoration(DividerItemDecoration(activity, LinearLayoutManager.VERTICAL))
        binding.recyclerViewWeatherDays.adapter = adapter
    }

    private fun showWeather(weather: WeatherResponse) {
        binding.weather = weather

        binding.textViewTimezone.text = cityName
        GlideApp.with(this)
                .load("http://openweathermap.org/img/wn/" + weather.current.weather[0].icon + "@4x.png")
                .placeholder(R.drawable.loading_image)
                .error(R.drawable.damaged_image)
                .fitCenter()
                .into(binding.imageViewIcon)

        binding.textViewDescription.text = weather.current.weather[0].description

        adapter.setData(weather.daily.subList(1,6))

        val simpleDateFormat = SimpleDateFormat("MM-dd-yyyy HH:mm:ss", Locale.US)
        binding.textViewDate.text = simpleDateFormat.format(weather.current.dt * 1000)
        binding.textViewTemp.text = getString(R.string.weather_temp, roundDecimal(weather.current.temp))
        binding.textViewFeelsLike.text = getString(R.string.weather_feels_like, roundDecimal(weather.current.feelsLike))
        binding.textViewHumidity.text = getString(R.string.weather_humidity, weather.current.humidity.toString())
        binding.textViewWindSpeed.text = getString(R.string.weather_wind_speed, roundDecimal(weather.current.windSpeed), getWindOrientation(weather.current.windDeg))
    }

    private fun roundDecimal(value: Float): String {
        return String.format("%.1f", value)
    }

    private fun getWindOrientation(deg: Int): String {
        return when (deg) {
            in 0..22 -> {
                "N"
            }
            in 23..67 -> {
                "NE"
            }
            in 68..112 -> {
                "E"
            }
            in 113..157 -> {
                "SE"
            }
            in 158..202 -> {
                "S"
            }
            in 203..247 -> {
                "SO"
            }
            in 248..292 -> {
                "O"
            }
            in 293..338 -> {
                "NO"
            }
            in 339..360 -> {
                "N"
            }
            else -> "-"
        }
    }
}