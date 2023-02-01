package com.example.weather_api

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
import kotlinx.coroutines.launch
import com.example.weather_api.presentaion.model.WeatherViewState
import kotlinx.coroutines.plus

class MainActivity : AppCompatActivity() {

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
                    locationTextView.setText(R.string.LOCATION_TEXT)
                    countryTextView.setText(R.string.COUNTRY_TEXT)
                    temperatureTextView.setText(R.string.TEMPERATURE_TEXT)
                    if (it is WeatherViewState.Failed) {
                        showFailToast(it.failureText)
                    } else {
                        it as WeatherViewState.Success

                        locationTextView.append(it.locationName)
                        countryTextView.append(it.countryName)
                        temperatureTextView.append(it.locationTemperature.toString().plus("\t°C"))
                    }
                }
            }
        }

        button.setOnClickListener {
            vm.load(weatherLocationToSearch = editText.text.toString())
            Log.d("EDIT_TEXT_VALUE", editText.text.toString())
        }
    }

    private fun showFailToast(errorText: String) {
        Toast.makeText(this@MainActivity, errorText, Toast.LENGTH_SHORT)
            .show()
    }
}


