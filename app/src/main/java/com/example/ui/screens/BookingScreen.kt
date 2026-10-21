package com.example.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
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
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.CalendarMonth
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Error
import androidx.compose.material.icons.filled.Groups
import androidx.compose.material.icons.filled.Luggage
import androidx.compose.material.icons.filled.Notes
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Phone
import androidx.compose.material.icons.filled.Place
import androidx.compose.material.icons.filled.Remove
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.model.Destination
import com.example.model.TourPackage
import com.example.ui.theme.ErrorRed
import com.example.ui.theme.OceanBluePrimary
import com.example.ui.theme.TealSecondary
import com.example.viewmodel.BookingFormState

@OptIn(ExperimentalMaterial3Api::class, ExperimentalLayoutApi::class)
@Composable
fun BookingScreen(
    destinations: List<Destination>,
    packages: List<TourPackage>,
    formState: BookingFormState,
    onTouristNameChange: (String) -> Unit,
    onPhoneNumberChange: (String) -> Unit,
    onEmailChange: (String) -> Unit,
    onDestinationSelected: (String) -> Unit,
    onPackageSelected: (String) -> Unit,
    onTravelDateChange: (String) -> Unit,
    onTravellersCountChange: (Int) -> Unit,
    onSpecialNotesChange: (String) -> Unit,
    onSubmitBooking: () -> Unit,
    modifier: Modifier = Modifier
) {
    var destinationDropdownExpanded by remember { mutableStateOf(false) }
    var packageDropdownExpanded by remember { mutableStateOf(false) }

    val currentDestination = destinations.firstOrNull { it.id == formState.selectedDestinationId }
    val availablePackages = packages.filter { it.destinationId == formState.selectedDestinationId }
    val currentPackage = availablePackages.firstOrNull { it.id == formState.selectedPackageId }
        ?: availablePackages.firstOrNull()

    val pricePerPerson = currentPackage?.pricePerPerson ?: 8999
    val basePrice = pricePerPerson * formState.numberOfTravellers
    val gstAmount = (basePrice * 0.05).toInt()
    val totalPrice = basePrice + gstAmount

    val presetDates = listOf(
        "2026-11-15",
        "2026-11-25",
        "2026-12-05",
        "2026-12-20",
        "2027-01-10"
    )

    LazyColumn(
        modifier = modifier
            .fillMaxSize()
            .testTag("booking_screen"),
        contentPadding = PaddingValues(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        // Form Title
        item {
            Column {
                Text(
                    text = "Book Your Tour",
                    style = MaterialTheme.typography.headlineSmall,
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colorScheme.onSurface
                )
                Text(
                    text = "Fill in the details below to reserve your holiday package",
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
            }
        }

        // Section 1: Tourist Personal Details Card
        item {
            Card(
                shape = RoundedCornerShape(16.dp),
                colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
                elevation = CardDefaults.cardElevation(defaultElevation = 2.dp),
                modifier = Modifier.fillMaxWidth()
            ) {
                Column(
                    modifier = Modifier.padding(18.dp),
                    verticalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    Text(
                        text = "Tourist Information",
                        style = MaterialTheme.typography.titleMedium,
                        fontWeight = FontWeight.Bold,
                        color = OceanBluePrimary
                    )

                    // Tourist Name Field
                    OutlinedTextField(
                        value = formState.touristName,
                        onValueChange = onTouristNameChange,
                        label = { Text("Tourist Full Name *") },
                        placeholder = { Text("e.g. Ramesh Kumar") },
                        leadingIcon = {
                            Icon(Icons.Default.Person, contentDescription = null, tint = OceanBluePrimary)
                        },
                        isError = formState.errors.containsKey("touristName"),
                        supportingText = {
                            formState.errors["touristName"]?.let {
                                Text(text = it, color = ErrorRed)
                            }
                        },
                        singleLine = true,
                        shape = RoundedCornerShape(12.dp),
                        modifier = Modifier
                            .fillMaxWidth()
                            .testTag("input_tourist_name")
                    )

                    // Phone Number Field
                    OutlinedTextField(
                        value = formState.phoneNumber,
                        onValueChange = onPhoneNumberChange,
                        label = { Text("Mobile Number (10 Digits) *") },
                        placeholder = { Text("9876543210") },
                        leadingIcon = {
                            Icon(Icons.Default.Phone, contentDescription = null, tint = OceanBluePrimary)
                        },
                        prefix = { Text("+91 ") },
                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Phone),
                        isError = formState.errors.containsKey("phoneNumber"),
                        supportingText = {
                            formState.errors["phoneNumber"]?.let {
                                Text(text = it, color = ErrorRed)
                            }
                        },
                        singleLine = true,
                        shape = RoundedCornerShape(12.dp),
                        modifier = Modifier
                            .fillMaxWidth()
                            .testTag("input_phone_number")
                    )

                    // Email Field
                    OutlinedTextField(
                        value = formState.email,
                        onValueChange = onEmailChange,
                        label = { Text("Email Address (Optional)") },
                        placeholder = { Text("tourist@gmail.com") },
                        leadingIcon = {
                            Icon(Icons.Default.Email, contentDescription = null, tint = OceanBluePrimary)
                        },
                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email),
                        singleLine = true,
                        shape = RoundedCornerShape(12.dp),
                        modifier = Modifier
                            .fillMaxWidth()
                            .testTag("input_email")
                    )
                }
            }
        }

        // Section 2: Destination & Package Selection Card
        item {
            Card(
                shape = RoundedCornerShape(16.dp),
                colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
                elevation = CardDefaults.cardElevation(defaultElevation = 2.dp),
                modifier = Modifier.fillMaxWidth()
            ) {
                Column(
                    modifier = Modifier.padding(18.dp),
                    verticalArrangement = Arrangement.spacedBy(14.dp)
                ) {
                    Text(
                        text = "Destination & Tour Package",
                        style = MaterialTheme.typography.titleMedium,
                        fontWeight = FontWeight.Bold,
                        color = OceanBluePrimary
                    )

                    // Destination Dropdown
                    ExposedDropdownMenuBox(
                        expanded = destinationDropdownExpanded,
                        onExpandedChange = { destinationDropdownExpanded = !destinationDropdownExpanded },
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        OutlinedTextField(
                            value = currentDestination?.let { "${it.emoji} ${it.name} (${it.state})" } ?: "Select Destination",
                            onValueChange = {},
                            readOnly = true,
                            label = { Text("Destination *") },
                            leadingIcon = {
                                Icon(Icons.Default.Place, contentDescription = null, tint = OceanBluePrimary)
                            },
                            trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded = destinationDropdownExpanded) },
                            shape = RoundedCornerShape(12.dp),
                            modifier = Modifier
                                .menuAnchor()
                                .fillMaxWidth()
                                .testTag("select_destination_dropdown")
                        )

                        ExposedDropdownMenu(
                            expanded = destinationDropdownExpanded,
                            onDismissRequest = { destinationDropdownExpanded = false }
                        ) {
                            destinations.forEach { dest ->
                                DropdownMenuItem(
                                    text = { Text("${dest.emoji} ${dest.name} - ${dest.state}") },
                                    onClick = {
                                        onDestinationSelected(dest.id)
                                        destinationDropdownExpanded = false
                                    },
                                    modifier = Modifier.testTag("dropdown_item_${dest.id}")
                                )
                            }
                        }
                    }

                    // Package Dropdown
                    ExposedDropdownMenuBox(
                        expanded = packageDropdownExpanded,
                        onExpandedChange = { packageDropdownExpanded = !packageDropdownExpanded },
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        OutlinedTextField(
                            value = currentPackage?.let { "${it.title} (₹${"%,d".format(it.pricePerPerson)})" } ?: "Select Package",
                            onValueChange = {},
                            readOnly = true,
                            label = { Text("Tour Package *") },
                            leadingIcon = {
                                Icon(Icons.Default.Luggage, contentDescription = null, tint = OceanBluePrimary)
                            },
                            trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded = packageDropdownExpanded) },
                            shape = RoundedCornerShape(12.dp),
                            modifier = Modifier
                                .menuAnchor()
                                .fillMaxWidth()
                                .testTag("select_package_dropdown")
                        )

                        ExposedDropdownMenu(
                            expanded = packageDropdownExpanded,
                            onDismissRequest = { packageDropdownExpanded = false }
                        ) {
                            availablePackages.forEach { pkg ->
                                DropdownMenuItem(
                                    text = {
                                        Column {
                                            Text(pkg.title, fontWeight = FontWeight.Bold)
                                            Text(
                                                "${pkg.durationDays}D/${pkg.durationNights}N • ₹${"%,d".format(pkg.pricePerPerson)}/person",
                                                style = MaterialTheme.typography.bodySmall,
                                                color = OceanBluePrimary
                                            )
                                        }
                                    },
                                    onClick = {
                                        onPackageSelected(pkg.id)
                                        packageDropdownExpanded = false
                                    },
                                    modifier = Modifier.testTag("pkg_item_${pkg.id}")
                                )
                            }
                        }
                    }
                }
            }
        }

        // Section 3: Travel Date & Number of Travellers Card
        item {
            Card(
                shape = RoundedCornerShape(16.dp),
                colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
                elevation = CardDefaults.cardElevation(defaultElevation = 2.dp),
                modifier = Modifier.fillMaxWidth()
            ) {
                Column(
                    modifier = Modifier.padding(18.dp),
                    verticalArrangement = Arrangement.spacedBy(14.dp)
                ) {
                    Text(
                        text = "Travel Schedule & Guests",
                        style = MaterialTheme.typography.titleMedium,
                        fontWeight = FontWeight.Bold,
                        color = OceanBluePrimary
                    )

                    // Date Input
                    OutlinedTextField(
                        value = formState.travelDate,
                        onValueChange = onTravelDateChange,
                        label = { Text("Travel Date (YYYY-MM-DD) *") },
                        placeholder = { Text("2026-11-20") },
                        leadingIcon = {
                            Icon(Icons.Default.CalendarMonth, contentDescription = null, tint = OceanBluePrimary)
                        },
                        isError = formState.errors.containsKey("travelDate"),
                        supportingText = {
                            formState.errors["travelDate"]?.let {
                                Text(text = it, color = ErrorRed)
                            }
                        },
                        singleLine = true,
                        shape = RoundedCornerShape(12.dp),
                        modifier = Modifier
                            .fillMaxWidth()
                            .testTag("input_travel_date")
                    )

                    // Quick Dates Chips
                    Text(
                        text = "Or choose a popular departure date:",
                        style = MaterialTheme.typography.labelSmall,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )

                    FlowRow(
                        horizontalArrangement = Arrangement.spacedBy(8.dp),
                        verticalArrangement = Arrangement.spacedBy(6.dp)
                    ) {
                        presetDates.forEach { dateStr ->
                            val isSelected = formState.travelDate == dateStr
                            Box(
                                modifier = Modifier
                                    .background(
                                        if (isSelected) OceanBluePrimary else MaterialTheme.colorScheme.surfaceVariant,
                                        RoundedCornerShape(8.dp)
                                    )
                                    .clickable { onTravelDateChange(dateStr) }
                                    .padding(horizontal = 10.dp, vertical = 6.dp)
                                    .testTag("preset_date_$dateStr")
                            ) {
                                Text(
                                    text = dateStr,
                                    style = MaterialTheme.typography.labelSmall,
                                    fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Normal,
                                    color = if (isSelected) Color.White else MaterialTheme.colorScheme.onSurface
                                )
                            }
                        }
                    }

                    Spacer(modifier = Modifier.height(4.dp))

                    // Number of Travellers Stepper
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Column {
                            Row(verticalAlignment = Alignment.CenterVertically) {
                                Icon(
                                    Icons.Default.Groups,
                                    contentDescription = null,
                                    tint = OceanBluePrimary,
                                    modifier = Modifier.size(20.dp)
                                )
                                Spacer(modifier = Modifier.width(6.dp))
                                Text(
                                    text = "Number of Travellers",
                                    style = MaterialTheme.typography.bodyMedium,
                                    fontWeight = FontWeight.Bold
                                )
                            }
                            Text(
                                text = "Adults & Children",
                                style = MaterialTheme.typography.labelSmall,
                                color = MaterialTheme.colorScheme.onSurfaceVariant
                            )
                        }

                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.spacedBy(8.dp)
                        ) {
                            IconButton(
                                onClick = { onTravellersCountChange(formState.numberOfTravellers - 1) },
                                enabled = formState.numberOfTravellers > 1,
                                modifier = Modifier
                                    .size(36.dp)
                                    .background(MaterialTheme.colorScheme.surfaceVariant, CircleShape)
                                    .testTag("stepper_decrement")
                            ) {
                                Icon(Icons.Default.Remove, contentDescription = "Decrease", modifier = Modifier.size(16.dp))
                            }

                            Text(
                                text = "${formState.numberOfTravellers}",
                                style = MaterialTheme.typography.titleMedium,
                                fontWeight = FontWeight.ExtraBold,
                                modifier = Modifier.padding(horizontal = 8.dp)
                            )

                            IconButton(
                                onClick = { onTravellersCountChange(formState.numberOfTravellers + 1) },
                                enabled = formState.numberOfTravellers < 20,
                                modifier = Modifier
                                    .size(36.dp)
                                    .background(OceanBluePrimary.copy(alpha = 0.15f), CircleShape)
                                    .testTag("stepper_increment")
                            ) {
                                Icon(Icons.Default.Add, contentDescription = "Increase", tint = OceanBluePrimary, modifier = Modifier.size(16.dp))
                            }
                        }
                    }

                    // Special Notes Field
                    OutlinedTextField(
                        value = formState.specialNotes,
                        onValueChange = onSpecialNotesChange,
                        label = { Text("Special Requests / Notes") },
                        placeholder = { Text("e.g. Vegetarian food, Sea view room, Senior citizen aid") },
                        leadingIcon = {
                            Icon(Icons.Default.Notes, contentDescription = null, tint = OceanBluePrimary)
                        },
                        maxLines = 3,
                        shape = RoundedCornerShape(12.dp),
                        modifier = Modifier
                            .fillMaxWidth()
                            .testTag("input_special_notes")
                    )
                }
            }
        }

        // Section 4: Live Fare Calculation Card
        item {
            Card(
                shape = RoundedCornerShape(16.dp),
                colors = CardDefaults.cardColors(
                    containerColor = MaterialTheme.colorScheme.primaryContainer.copy(alpha = 0.4f)
                ),
                modifier = Modifier.fillMaxWidth()
            ) {
                Column(modifier = Modifier.padding(18.dp)) {
                    Text(
                        text = "Price Calculation Breakdown",
                        style = MaterialTheme.typography.titleSmall,
                        fontWeight = FontWeight.Bold,
                        color = OceanBluePrimary
                    )

                    Spacer(modifier = Modifier.height(10.dp))

                    FareRow(
                        label = "Package rate per person",
                        value = "₹${"%,d".format(pricePerPerson)}"
                    )
                    FareRow(
                        label = "Travellers count",
                        value = "× ${formState.numberOfTravellers}"
                    )
                    FareRow(
                        label = "Base Fare",
                        value = "₹${"%,d".format(basePrice)}"
                    )
                    FareRow(
                        label = "Govt. GST (5%)",
                        value = "₹${"%,d".format(gstAmount)}"
                    )

                    HorizontalDivider(modifier = Modifier.padding(vertical = 8.dp))

                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            text = "Estimated Total",
                            style = MaterialTheme.typography.titleMedium,
                            fontWeight = FontWeight.Bold,
                            color = MaterialTheme.colorScheme.onSurface
                        )
                        Text(
                            text = "₹${"%,d".format(totalPrice)}",
                            style = MaterialTheme.typography.headlineSmall,
                            fontWeight = FontWeight.ExtraBold,
                            color = OceanBluePrimary
                        )
                    }
                }
            }
        }

        // Section 5: Submit Button
        item {
            Button(
                onClick = onSubmitBooking,
                shape = RoundedCornerShape(14.dp),
                colors = ButtonDefaults.buttonColors(containerColor = OceanBluePrimary),
                modifier = Modifier
                    .fillMaxWidth()
                    .height(54.dp)
                    .testTag("submit_booking_btn")
            ) {
                Icon(
                    imageVector = Icons.Default.CheckCircle,
                    contentDescription = null,
                    modifier = Modifier.size(20.dp)
                )
                Spacer(modifier = Modifier.width(8.dp))
                Text(
                    text = "Confirm & Register Booking",
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.Bold
                )
            }
        }
    }
}

@Composable
private fun FareRow(label: String, value: String) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 3.dp),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Text(
            text = label,
            style = MaterialTheme.typography.bodySmall,
            color = MaterialTheme.colorScheme.onSurfaceVariant
        )
        Text(
            text = value,
            style = MaterialTheme.typography.bodySmall,
            fontWeight = FontWeight.Medium,
            color = MaterialTheme.colorScheme.onSurface
        )
    }
}
