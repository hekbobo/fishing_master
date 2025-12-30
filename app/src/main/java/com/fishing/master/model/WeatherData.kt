package com.fishing.master.model

data class WeatherData(
    val location: String,
    val temperature: String,
    val condition: String,
    val windSpeed: String,
    val waterTemp: String,
    val tideTime: String
)

