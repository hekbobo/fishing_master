package com.fishing.master.model

data class FishCatch(
    val id: String,
    val fishName: String,
    val imageUrl: String? = null,
    val imageResId: Int? = null
)

