package com.example.model

enum class BookingStatus(val label: String) {
    CONFIRMED("Confirmed"),
    PENDING("Pending"),
    CANCELLED("Cancelled")
}

data class Booking(
    val id: String,
    val bookingRef: String,
    val touristName: String,
    val phoneNumber: String,
    val email: String,
    val destinationName: String,
    val packageName: String,
    val travelDate: String,
    val numberOfTravellers: Int,
    val pricePerPerson: Int,
    val basePrice: Int,
    val gstAmount: Int,
    val totalPrice: Int,
    val specialNotes: String,
    val status: BookingStatus = BookingStatus.CONFIRMED,
    val bookingTimestamp: String
)
