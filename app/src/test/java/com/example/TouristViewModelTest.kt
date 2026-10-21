package com.example

import com.example.model.BookingStatus
import com.example.viewmodel.TouristViewModel
import org.junit.Assert.assertEquals
import org.junit.Assert.assertFalse
import org.junit.Assert.assertNotNull
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test

class TouristViewModelTest {

    private lateinit var viewModel: TouristViewModel

    @Before
    fun setUp() {
        viewModel = TouristViewModel()
    }

    @Test
    fun testInitialDestinationsLoaded() {
        val destinations = viewModel.destinations.value
        assertEquals(5, destinations.size)
        val names = destinations.map { it.name }
        assertTrue(names.contains("Goa"))
        assertTrue(names.contains("Manali"))
        assertTrue(names.contains("Kerala"))
        assertTrue(names.contains("Jaipur"))
        assertTrue(names.contains("Taj Mahal"))
    }

    @Test
    fun testFormValidationFailsOnShortNameAndBadPhone() {
        viewModel.onTouristNameChange("A")
        viewModel.onPhoneNumberChange("123")
        val isValid = viewModel.validateForm()
        assertFalse(isValid)
        assertTrue(viewModel.formState.value.errors.containsKey("touristName"))
        assertTrue(viewModel.formState.value.errors.containsKey("phoneNumber"))
    }

    @Test
    fun testFormSubmissionSuccess() {
        viewModel.onTouristNameChange("Rohan Verma")
        viewModel.onPhoneNumberChange("9876543210")
        viewModel.onDestinationSelected("goa")
        viewModel.onTravellersCountChange(2)
        viewModel.onTravelDateChange("2026-11-20")

        val submitted = viewModel.submitBooking()
        assertTrue(submitted)

        val confirmed = viewModel.confirmedBooking.value
        assertNotNull(confirmed)
        assertEquals("Rohan Verma", confirmed?.touristName)
        assertEquals("Goa", confirmed?.destinationName)
        assertEquals(2, confirmed?.numberOfTravellers)
        assertEquals(BookingStatus.CONFIRMED, confirmed?.status)
        assertTrue(confirmed?.bookingRef?.startsWith("TMS-2026-GOA-") == true)
    }

    @Test
    fun testCancelBooking() {
        val initialBooking = viewModel.bookings.value.first()
        viewModel.cancelBooking(initialBooking.id)

        val updated = viewModel.bookings.value.first { it.id == initialBooking.id }
        assertEquals(BookingStatus.CANCELLED, updated.status)
    }
}
