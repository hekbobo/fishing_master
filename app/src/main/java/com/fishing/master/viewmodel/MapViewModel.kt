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
    
    /**
     * 根据当前位置更新附近的钓鱼点
     * @param latitude 纬度
     * @param longitude 经度
     */
    fun updateFishingSpotsNearby(latitude: Double, longitude: Double) {
        viewModelScope.launch {
            // 在实际应用中，这里会调用API根据位置获取附近的钓鱼点
            // 现在我们只是模拟这个功能
            val spots = repository.getFishingSpotsNearby(latitude, longitude)
            _fishingSpots.value = spots
        }
    }
}
