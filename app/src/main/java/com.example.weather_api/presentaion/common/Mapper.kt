package com.example.weather_api.presentaion.common

interface Mapper<F, R> {
    fun map(from: F): R
}