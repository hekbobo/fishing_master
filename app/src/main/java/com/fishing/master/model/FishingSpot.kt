package com.fishing.master.model

data class FishingSpot(
    val id: String,
    val name: String,
    val description: String,
    val rating: Float,
    val distance: String,
    val isPublic: Boolean,
    val tags: List<String>,
    val imageResId: Int? = null,
    val isLocked: Boolean = false,
    val lat: Float = 0f, // Mock coordinates
    val lng: Float = 0f
)
