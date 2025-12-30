package com.fishing.master.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.fishing.master.model.FishCatch
import com.fishing.master.model.WeatherData
import com.fishing.master.repository.FishRepository
import kotlinx.coroutines.launch

class MainViewModel : ViewModel() {

    private val repository = FishRepository()

    private val _weatherData = MutableLiveData<WeatherData>()
    val weatherData: LiveData<WeatherData> = _weatherData

    private val _recentCatches = MutableLiveData<List<FishCatch>>()
    val recentCatches: LiveData<List<FishCatch>> = _recentCatches

    init {
        fetchWeatherData()
        fetchRecentCatches()
    }

    private fun fetchWeatherData() {
        viewModelScope.launch {
            val data = repository.getWeatherData()
            _weatherData.value = data
        }
    }

    private fun fetchRecentCatches() {
        viewModelScope.launch {
            val data = repository.getRecentCatches()
            _recentCatches.value = data
        }
    }
}
