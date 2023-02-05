package com.example.weather_api.presentation.common

interface Mapper<F, R> {
    fun map(from: F): R
}