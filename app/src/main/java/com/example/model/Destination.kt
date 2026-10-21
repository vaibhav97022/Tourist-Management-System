package com.example.model

data class Destination(
    val id: String,
    val name: String,
    val state: String,
    val tagline: String,
    val description: String,
    val category: String,
    val topAttractions: List<String>,
    val bestTimeToVisit: String,
    val idealDuration: String,
    val rating: Double,
    val reviewsCount: Int,
    val startingPrice: Int,
    val emoji: String,
    val gradientColors: Pair<Long, Long>
)
