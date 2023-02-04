package com.example.weather_api.presentation.ui

import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.weather_api.R
import com.example.weather_api.presentation.model.WeatherViewState
import com.example.weather_api.presentation.view_model.MainViewModel
import com.example.weather_api.ui.theme.Weather_apiTheme

class MainActivityCompose : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            Weather_apiTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colors.background
                ) {
                    LocationInfo()
                }
            }
        }
    }
}


@Composable
fun LocationInfo() {

    val state = remember {
        MainViewModel()
    }

    val uiState by state.weatherStateFlow.collectAsStateWithLifecycle(Lifecycle.State.CREATED)

    if (uiState is WeatherViewState.Failed) {
        Toast.makeText(LocalContext.current, (uiState as WeatherViewState.Failed).failureText, Toast.LENGTH_SHORT)
            .show()
    } else {

        Column {
            val data = uiState as WeatherViewState.Success
            Text(text = "${R.string.LOCATION_TEXT} ${data.locationName}")
            Text(text = "${R.string.COUNTRY_TEXT} ${data.countryName}")
            Text(text = "${R.string.TEMPERATURE_TEXT} ${data.locationTemperature} °C")

            var text by remember {
                mutableStateOf("")
            }

            TextField(
                value = text,
                onValueChange = { text = it },
                label = { Text(text = "Введите координаты") })

            Button(onClick = {
                state.load(weatherLocationToSearch = text)
            }, shape = RoundedCornerShape(20.dp)) {
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    Weather_apiTheme {
        LocationInfo()
    }
}