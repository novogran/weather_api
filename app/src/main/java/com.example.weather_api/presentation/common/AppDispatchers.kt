package com.example.weather_api.presentation.common

import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers

class AppDispatchers (
   val io: CoroutineDispatcher = Dispatchers.IO,
)