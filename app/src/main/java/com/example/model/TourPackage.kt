package com.example.model

data class TourPackage(
    val id: String,
    val title: String,
    val destinationId: String,
    val destinationName: String,
    val durationDays: Int,
    val durationNights: Int,
    val pricePerPerson: Int,
    val rating: Double,
    val reviewsCount: Int,
    val badge: String? = null,
    val inclusions: List<String>,
    val highlights: List<String>,
    val itineraryHighlights: List<String>
)
