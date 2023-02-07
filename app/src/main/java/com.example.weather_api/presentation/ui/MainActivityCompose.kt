package com.example.weather_api.presentation.ui

import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.weather_api.R
import com.example.weather_api.presentation.model.WeatherViewState
import com.example.weather_api.presentation.view_model.MainViewModel
import com.example.weather_api.presentation.ui.theme.Weather_apiTheme
import com.example.weather_api.presentation.view_model.BaseViewModel

class MainActivityCompose : ComponentActivity() {

    private val vm: MainViewModel by viewModels()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            Weather_apiTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colors.background
                ) {
                    LocationInfo(vm)
                }
            }
        }
    }
}

@Composable
fun LocationInfo(viewModel: BaseViewModel) {

    val uiState: WeatherViewState by (viewModel as MainViewModel).weatherStateFlow.collectAsStateWithLifecycle()

    if (uiState is WeatherViewState.Failed) {
        Toast.makeText(
            LocalContext.current,
            (uiState as WeatherViewState.Failed).failureText,
            Toast.LENGTH_SHORT
        )
            .show()
    } else {
        Column(
            modifier = Modifier
                .padding(16.dp)
        ) {
            val weatherInfo = uiState as WeatherViewState.Success
            Text(
                text = stringResource(id = R.string.LOCATION_TEXT) + weatherInfo.locationName,
            )
            Text(text = stringResource(id = R.string.COUNTRY_TEXT) + weatherInfo.countryName)
            Text(text = stringResource(id = R.string.TEMPERATURE_TEXT) + weatherInfo.locationTemperature + " °C")

            var text by remember {
                mutableStateOf("")
            }

            TextField(
                value = text,
                onValueChange = { text = it },
                label = { Text(text = "Введите координаты") },
            )

            Button(
                onClick = {
                    viewModel.load(text)
                },
                shape = RoundedCornerShape(20.dp),
            ) {
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    Weather_apiTheme {
//        LocationInfo(MockViewModel(WeatherViewState.Success()))
    }
}