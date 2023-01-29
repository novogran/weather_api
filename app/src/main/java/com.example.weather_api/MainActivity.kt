package com.example.weather_api

import android.annotation.SuppressLint
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.example.weather_api.presentaion.view_model.MainViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import presentaion.mapper.WeatherViewDataMapper
import presentaion.model.WeatherViewState

class MainActivity : AppCompatActivity() {

    private companion object {
        private const val LOCATION_TEXT = "Место: "
        private const val COUNTRY_TEXT = "Страна: "
        private const val TEMPERATURE_ICON = " °C"
        private const val TEMPERATURE_TEXT = "Температура: "
    }

    @SuppressLint("ShowToast")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val vm = ViewModelProvider(this)[MainViewModel::class.java]

        val locationTextView = findViewById<TextView>(R.id.location_name_text_view)
        val countryTextView = findViewById<TextView>(R.id.country_name_text_view)
        val temperatureTextView = findViewById<TextView>(R.id.temperature_text_view)
        val editText = findViewById<EditText>(R.id.location_edit_text)
        val button = findViewById<Button>(R.id.get_weather_button)

        lifecycleScope.launch {
            lifecycle.repeatOnLifecycle(Lifecycle.State.CREATED) {
                vm.weatherStateFlow.collect {
                    when (it) {
                        is WeatherViewState.Success -> {
                            locationTextView.text = LOCATION_TEXT + it.locationName
                            countryTextView.text = COUNTRY_TEXT + it.countryName
                            temperatureTextView.text = TEMPERATURE_TEXT +
                                    it.locationTemperature.toString() + TEMPERATURE_ICON
                        }
                        is WeatherViewState.Failed -> {
                            Toast.makeText(this@MainActivity, it.failureText, Toast.LENGTH_SHORT).show()
                        }
                    }

                }
            }
        }

        button.setOnClickListener {
            vm.load(weatherLocationToSearch = editText.text.toString())
            Log.d("EDIT_TEXT_VALUE", editText.text.toString())
        }
    }
}


