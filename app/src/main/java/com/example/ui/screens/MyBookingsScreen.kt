package com.example.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Luggage
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.FilterChip
import androidx.compose.material3.FilterChipDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.example.model.Booking
import com.example.model.BookingStatus
import com.example.ui.components.BookingItemCard
import com.example.ui.theme.ErrorRed
import com.example.ui.theme.OceanBluePrimary
import com.example.ui.theme.SuccessGreen

@Composable
fun MyBookingsScreen(
    bookings: List<Booking>,
    onCancelBooking: (String) -> Unit,
    onNavigateToBooking: () -> Unit,
    modifier: Modifier = Modifier
) {
    var selectedStatus by remember { mutableStateOf<BookingStatus?>(null) }

    val filteredBookings by remember(bookings, selectedStatus) {
        derivedStateOf {
            if (selectedStatus == null) {
                bookings
            } else {
                bookings.filter { it.status == selectedStatus }
            }
        }
    }

    val totalCount = bookings.size
    val confirmedCount = bookings.count { it.status == BookingStatus.CONFIRMED }
    val cancelledCount = bookings.count { it.status == BookingStatus.CANCELLED }

    Column(
        modifier = modifier
            .fillMaxSize()
            .testTag("my_bookings_screen")
    ) {
        // Header
        Column(modifier = Modifier.padding(horizontal = 16.dp, vertical = 8.dp)) {
            Text(
                text = "My Bookings",
                style = MaterialTheme.typography.titleLarge,
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.onSurface
            )
            Text(
                text = "Manage your confirmed and upcoming travel packages",
                style = MaterialTheme.typography.bodySmall,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
        }

        // Metrics Summary Row
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp, vertical = 4.dp),
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            MetricCard(
                title = "Total",
                count = "$totalCount",
                color = OceanBluePrimary,
                modifier = Modifier.weight(1f)
            )
            MetricCard(
                title = "Confirmed",
                count = "$confirmedCount",
                color = SuccessGreen,
                modifier = Modifier.weight(1f)
            )
            MetricCard(
                title = "Cancelled",
                count = "$cancelledCount",
                color = ErrorRed,
                modifier = Modifier.weight(1f)
            )
        }

        Spacer(modifier = Modifier.height(8.dp))

        // Status Filter Chips
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp),
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            FilterChip(
                selected = selectedStatus == null,
                onClick = { selectedStatus = null },
                label = { Text("All ($totalCount)") },
                colors = FilterChipDefaults.filterChipColors(
                    selectedContainerColor = OceanBluePrimary,
                    selectedLabelColor = MaterialTheme.colorScheme.surface
                ),
                modifier = Modifier.testTag("filter_status_all")
            )
            FilterChip(
                selected = selectedStatus == BookingStatus.CONFIRMED,
                onClick = {
                    selectedStatus = if (selectedStatus == BookingStatus.CONFIRMED) null else BookingStatus.CONFIRMED
                },
                label = { Text("Confirmed ($confirmedCount)") },
                colors = FilterChipDefaults.filterChipColors(
                    selectedContainerColor = SuccessGreen,
                    selectedLabelColor = MaterialTheme.colorScheme.surface
                ),
                modifier = Modifier.testTag("filter_status_confirmed")
            )
            FilterChip(
                selected = selectedStatus == BookingStatus.CANCELLED,
                onClick = {
                    selectedStatus = if (selectedStatus == BookingStatus.CANCELLED) null else BookingStatus.CANCELLED
                },
                label = { Text("Cancelled ($cancelledCount)") },
                colors = FilterChipDefaults.filterChipColors(
                    selectedContainerColor = ErrorRed,
                    selectedLabelColor = MaterialTheme.colorScheme.surface
                ),
                modifier = Modifier.testTag("filter_status_cancelled")
            )
        }

        Spacer(modifier = Modifier.height(12.dp))

        // Bookings List or Empty State
        if (filteredBookings.isEmpty()) {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(32.dp),
                contentAlignment = Alignment.Center
            ) {
                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                    Icon(
                        imageVector = Icons.Default.Luggage,
                        contentDescription = null,
                        tint = MaterialTheme.colorScheme.onSurfaceVariant.copy(alpha = 0.5f),
                        modifier = Modifier.size(72.dp)
                    )
                    Spacer(modifier = Modifier.height(16.dp))
                    Text(
                        text = "No bookings found",
                        style = MaterialTheme.typography.titleMedium,
                        fontWeight = FontWeight.Bold,
                        color = MaterialTheme.colorScheme.onSurface
                    )
                    Spacer(modifier = Modifier.height(4.dp))
                    Text(
                        text = "You don't have any bookings in this category.",
                        style = MaterialTheme.typography.bodyMedium,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                    Spacer(modifier = Modifier.height(16.dp))
                    Button(
                        onClick = onNavigateToBooking,
                        shape = RoundedCornerShape(12.dp),
                        colors = ButtonDefaults.buttonColors(containerColor = OceanBluePrimary),
                        modifier = Modifier.testTag("empty_state_book_btn")
                    ) {
                        Icon(Icons.Default.Add, contentDescription = null, modifier = Modifier.size(16.dp))
                        Spacer(modifier = Modifier.width(6.dp))
                        Text("Book a New Tour")
                    }
                }
            }
        } else {
            LazyColumn(
                contentPadding = PaddingValues(start = 16.dp, end = 16.dp, bottom = 24.dp),
                verticalArrangement = Arrangement.spacedBy(14.dp)
            ) {
                items(filteredBookings, key = { it.id }) { booking ->
                    BookingItemCard(
                        booking = booking,
                        onCancelClick = { onCancelBooking(booking.id) }
                    )
                }
            }
        }
    }
}

@Composable
private fun MetricCard(
    title: String,
    count: String,
    color: androidx.compose.ui.graphics.Color,
    modifier: Modifier = Modifier
) {
    Card(
        modifier = modifier,
        shape = RoundedCornerShape(12.dp),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
        elevation = CardDefaults.cardElevation(defaultElevation = 1.dp)
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(10.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = count,
                style = MaterialTheme.typography.titleLarge,
                fontWeight = FontWeight.ExtraBold,
                color = color
            )
            Text(
                text = title,
                style = MaterialTheme.typography.labelSmall,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
        }
    }
}
