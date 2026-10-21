package com.example.viewmodel

import androidx.lifecycle.ViewModel
import com.example.data.SampleData
import com.example.model.Booking
import com.example.model.BookingStatus
import com.example.model.Destination
import com.example.model.TourPackage
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale
import kotlin.random.Random

data class BookingFormState(
    val touristName: String = "",
    val phoneNumber: String = "",
    val email: String = "",
    val selectedDestinationId: String = "goa",
    val selectedPackageId: String = "pkg_goa_01",
    val travelDate: String = "2026-11-20",
    val numberOfTravellers: Int = 2,
    val specialNotes: String = "",
    val errors: Map<String, String> = emptyMap()
)

class TouristViewModel : ViewModel() {

    private val _destinations = MutableStateFlow<List<Destination>>(SampleData.destinations)
    val destinations: StateFlow<List<Destination>> = _destinations.asStateFlow()

    private val _packages = MutableStateFlow<List<TourPackage>>(SampleData.tourPackages)
    val packages: StateFlow<List<TourPackage>> = _packages.asStateFlow()

    private val _bookings = MutableStateFlow<List<Booking>>(SampleData.initialBookings)
    val bookings: StateFlow<List<Booking>> = _bookings.asStateFlow()

    private val _searchQuery = MutableStateFlow("")
    val searchQuery: StateFlow<String> = _searchQuery.asStateFlow()

    private val _selectedDestinationFilter = MutableStateFlow<String?>(null)
    val selectedDestinationFilter: StateFlow<String?> = _selectedDestinationFilter.asStateFlow()

    private val _bookingFilterStatus = MutableStateFlow<BookingStatus?>(null)
    val bookingFilterStatus: StateFlow<BookingStatus?> = _bookingFilterStatus.asStateFlow()

    private val _formState = MutableStateFlow(BookingFormState())
    val formState: StateFlow<BookingFormState> = _formState.asStateFlow()

    private val _confirmedBooking = MutableStateFlow<Booking?>(null)
    val confirmedBooking: StateFlow<Booking?> = _confirmedBooking.asStateFlow()

    fun onSearchQueryChanged(query: String) {
        _searchQuery.value = query
    }

    fun onDestinationFilterChanged(filter: String?) {
        _selectedDestinationFilter.value = filter
    }

    fun onBookingFilterStatusChanged(status: BookingStatus?) {
        _bookingFilterStatus.value = status
    }

    fun prefillBookingForDestination(destination: Destination) {
        val matchingPkg = _packages.value.firstOrNull { it.destinationId == destination.id }
        _formState.update { current ->
            current.copy(
                selectedDestinationId = destination.id,
                selectedPackageId = matchingPkg?.id ?: current.selectedPackageId,
                errors = emptyMap()
            )
        }
    }

    fun prefillBookingForPackage(tourPackage: TourPackage) {
        _formState.update { current ->
            current.copy(
                selectedDestinationId = tourPackage.destinationId,
                selectedPackageId = tourPackage.id,
                errors = emptyMap()
            )
        }
    }

    fun onTouristNameChange(name: String) {
        _formState.update { it.copy(touristName = name, errors = it.errors - "touristName") }
    }

    fun onPhoneNumberChange(phone: String) {
        val filtered = phone.filter { it.isDigit() }.take(10)
        _formState.update { it.copy(phoneNumber = filtered, errors = it.errors - "phoneNumber") }
    }

    fun onEmailChange(email: String) {
        _formState.update { it.copy(email = email, errors = it.errors - "email") }
    }

    fun onDestinationSelected(destId: String) {
        val firstPkg = _packages.value.firstOrNull { it.destinationId == destId }
        _formState.update { current ->
            current.copy(
                selectedDestinationId = destId,
                selectedPackageId = firstPkg?.id ?: "",
                errors = current.errors - "destination"
            )
        }
    }

    fun onPackageSelected(pkgId: String) {
        val pkg = _packages.value.firstOrNull { it.id == pkgId }
        _formState.update { current ->
            current.copy(
                selectedPackageId = pkgId,
                selectedDestinationId = pkg?.destinationId ?: current.selectedDestinationId,
                errors = current.errors - "package"
            )
        }
    }

    fun onTravelDateChange(date: String) {
        _formState.update { it.copy(travelDate = date, errors = it.errors - "travelDate") }
    }

    fun onTravellersCountChange(count: Int) {
        val clamped = count.coerceIn(1, 20)
        _formState.update { it.copy(numberOfTravellers = clamped, errors = it.errors - "travellers") }
    }

    fun onSpecialNotesChange(notes: String) {
        _formState.update { it.copy(specialNotes = notes) }
    }

    fun validateForm(): Boolean {
        val state = _formState.value
        val newErrors = mutableMapOf<String, String>()

        if (state.touristName.trim().length < 3) {
            newErrors["touristName"] = "Please enter a valid tourist name (at least 3 characters)"
        }

        if (state.phoneNumber.trim().length != 10) {
            newErrors["phoneNumber"] = "Please enter a valid 10-digit mobile number"
        }

        if (state.travelDate.isBlank()) {
            newErrors["travelDate"] = "Please select a travel date"
        }

        if (state.numberOfTravellers < 1) {
            newErrors["travellers"] = "Number of travellers must be at least 1"
        }

        _formState.update { it.copy(errors = newErrors) }
        return newErrors.isEmpty()
    }

    fun submitBooking(): Boolean {
        if (!validateForm()) {
            return false
        }

        val state = _formState.value
        val dest = _destinations.value.firstOrNull { it.id == state.selectedDestinationId }
        val pkg = _packages.value.firstOrNull { it.id == state.selectedPackageId }

        val destName = dest?.name ?: "Incredible India"
        val pkgName = pkg?.title ?: "Custom Tour Package"
        val pricePerPerson = pkg?.pricePerPerson ?: 9999

        val basePrice = pricePerPerson * state.numberOfTravellers
        val gstAmount = (basePrice * 0.05).toInt()
        val totalPrice = basePrice + gstAmount

        val destCode = destName.take(3).uppercase(Locale.ROOT)
        val randomSuffix = Random.nextInt(1000, 9999)
        val bookingRef = "TMS-2026-$destCode-$randomSuffix"

        val sdf = SimpleDateFormat("yyyy-MM-dd hh:mm a", Locale.getDefault())
        val currentTimestamp = sdf.format(Date())

        val newBooking = Booking(
            id = "booking_${System.currentTimeMillis()}",
            bookingRef = bookingRef,
            touristName = state.touristName.trim(),
            phoneNumber = state.phoneNumber.trim(),
            email = if (state.email.isBlank()) "tourist@example.com" else state.email.trim(),
            destinationName = destName,
            packageName = pkgName,
            travelDate = state.travelDate,
            numberOfTravellers = state.numberOfTravellers,
            pricePerPerson = pricePerPerson,
            basePrice = basePrice,
            gstAmount = gstAmount,
            totalPrice = totalPrice,
            specialNotes = state.specialNotes.trim(),
            status = BookingStatus.CONFIRMED,
            bookingTimestamp = currentTimestamp
        )

        // Prepend new booking so it shows up at the top
        _bookings.update { listOf(newBooking) + it }
        _confirmedBooking.value = newBooking

        // Reset inputs to clean state
        _formState.value = BookingFormState(
            selectedDestinationId = state.selectedDestinationId,
            selectedPackageId = state.selectedPackageId
        )

        return true
    }

    fun dismissConfirmationDialog() {
        _confirmedBooking.value = null
    }

    fun cancelBooking(bookingId: String) {
        _bookings.update { currentList ->
            currentList.map { booking ->
                if (booking.id == bookingId) {
                    booking.copy(status = BookingStatus.CANCELLED)
                } else {
                    booking
                }
            }
        }
    }
}
