package com.fishing.master.repository

import com.fishing.master.model.FishCatch
import com.fishing.master.model.FishingSpot
import com.fishing.master.model.WeatherData
import kotlinx.coroutines.delay
import kotlin.math.roundToInt

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
    
    /**
     * 根据当前位置获取附近的钓鱼点
     * @param latitude 纬度
     * @param longitude 经度
     */
    suspend fun getFishingSpotsNearby(latitude: Double, longitude: Double): List<FishingSpot> {
        delay(300)
        // 在实际应用中，这里会调用API根据实际位置计算附近的钓鱼点
        // 现在我们返回一个模拟的列表，但可以根据位置调整距离等信息
        return listOf(
            FishingSpot(
                id = "1",
                name = "太浩湖畔垂钓点",
                description = "风景优美的湖畔，适合鲈鱼垂钓",
                rating = 4.8f,
                distance = "${String.format("%.1f", calculateDistance(latitude, longitude, 39.0968, -120.0324))} km", // 实际距离计算
                isPublic = true,
                tags = listOf("PUBLIC", "BASS", "TROUT"),
                imageResId = android.R.drawable.ic_menu_gallery,
                lat = 39.0968f, lng = -120.0324f
            ),
            FishingSpot(
                id = "2",
                name = "山间溪流钓场",
                description = "清澈的山间溪流，适合鳟鱼垂钓",
                rating = 4.6f,
                distance = "${String.format("%.1f", calculateDistance(latitude, longitude, 39.1, -120.1))} km",
                isPublic = true,
                tags = listOf("PUBLIC", "TROUT", "FLY"),
                imageResId = android.R.drawable.ic_menu_gallery,
                lat = 39.1f, lng = -120.1f
            ),
            FishingSpot(
                id = "3",
                name = "私人鱼塘",
                description = "设备齐全的私人鱼塘，需预约",
                rating = 4.9f,
                distance = "${String.format("%.1f", calculateDistance(latitude, longitude, 39.08, -120.05))} km",
                isPublic = false,
                tags = listOf("PRIVATE", "CATFISH", "BASS"),
                imageResId = android.R.drawable.ic_menu_gallery,
                isLocked = true,
                lat = 39.08f, lng = -120.05f
            )
        )
    }
    
    /**
     * 计算两点之间的距离（公里）
     * @param lat1 第一个点的纬度
     * @param lon1 第一个点的经度
     * @param lat2 第二个点的纬度
     * @param lon2 第二个点的经度
     */
    private fun calculateDistance(lat1: Double, lon1: Double, lat2: Double, lon2: Double): Double {
        val R = 6371e3 // 地球半径（米）
        val φ1 = Math.toRadians(lat1)
        val φ2 = Math.toRadians(lat2)
        val Δφ = Math.toRadians(lat2 - lat1)
        val Δλ = Math.toRadians(lon2 - lon1)

        val a = (kotlin.math.sin(Δφ / 2) * kotlin.math.sin(Δφ / 2) +
                kotlin.math.cos(φ1) * kotlin.math.cos(φ2) *
                kotlin.math.sin(Δλ / 2) * kotlin.math.sin(Δλ / 2))
        val c = 2 * kotlin.math.atan2(kotlin.math.sqrt(a), kotlin.math.sqrt(1 - a))

        return (R * c / 1000).roundToInt() / 10.0 // 返回公里数，保留一位小数
    }
}
