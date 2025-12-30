package com.fishing.master.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.fishing.master.model.FishingSpot
import com.fishing.master.repository.FishRepository
import kotlinx.coroutines.launch

class MapViewModel : ViewModel() {

    private val repository = FishRepository()
    private val _fishingSpots = MutableLiveData<List<FishingSpot>>()
    val fishingSpots: LiveData<List<FishingSpot>> = _fishingSpots

    init {
        loadSpots()
    }

    private fun loadSpots() {
        viewModelScope.launch {
            val spots = repository.getFishingSpots()
            _fishingSpots.value = spots
        }
    }
}
