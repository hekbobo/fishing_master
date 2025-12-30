package com.fishing.master.repository

import com.fishing.master.model.FishCatch
import com.fishing.master.model.FishingSpot
import com.fishing.master.model.WeatherData
import kotlinx.coroutines.delay

class FishRepository {

    suspend fun getRecentCatches(): List<FishCatch> {
        // Simulate network delay
        delay(500)
        return listOf(
            FishCatch(
                id = "1",
                fishName = "大口黑鲈",
                imageResId = android.R.drawable.ic_menu_gallery
            ),
            FishCatch(
                id = "2",
                fishName = "虹鳟",
                imageResId = android.R.drawable.ic_menu_gallery
            ),
            FishCatch(
                id = "3",
                fishName = "蓝鳃太阳鱼",
                imageResId = android.R.drawable.ic_menu_gallery
            ),
            FishCatch(
                id = "4",
                fishName = "鲶鱼",
                imageResId = android.R.drawable.ic_menu_gallery
            )
        )
    }

    suspend fun getWeatherData(): WeatherData {
        // Simulate network delay
        delay(300)
        return WeatherData(
            location = "加州太浩湖",
            temperature = "24°C",
            condition = "晴间多云",
            windSpeed = "12km",
            waterTemp = "18°C",
            tideTime = "06:42 AM"
        )
    }

    suspend fun getFishingSpots(): List<FishingSpot> {
        delay(300)
        return listOf(
            FishingSpot(
                id = "1",
                name = "Emerald Bay",
                description = "North side, near the old dock.",
                rating = 4.8f,
                distance = "2.3 km",
                isPublic = true,
                tags = listOf("PUBLIC", "CARP", "BASS"),
                imageResId = android.R.drawable.ic_menu_gallery, // Placeholder
                lat = 0.5f, lng = 0.5f
            ),
            FishingSpot(
                id = "2",
                name = "Secret Spot #03",
                description = "Private marker. Rocky shore.",
                rating = 5.0f,
                distance = "5.1 km",
                isPublic = false,
                tags = listOf("PRIVATE", "BASS"),
                imageResId = null,
                isLocked = true,
                lat = 0.2f, lng = 0.8f
            ),
             FishingSpot(
                id = "3",
                name = "Mirror Lake",
                description = "Deep water, good for trout.",
                rating = 4.5f,
                distance = "8.4 km",
                isPublic = true,
                tags = listOf("PUBLIC", "TROUT"),
                imageResId = android.R.drawable.ic_menu_gallery,
                 lat = 0.8f, lng = 0.3f
            ),
             FishingSpot(
                id = "4",
                name = "River Bend",
                description = "Fast current, fly fishing.",
                rating = 4.7f,
                distance = "12 km",
                isPublic = true,
                tags = listOf("PUBLIC", "FLY"),
                 imageResId = android.R.drawable.ic_menu_gallery,
                 lat = 0.6f, lng = 0.6f
            )
        )
    }
}
