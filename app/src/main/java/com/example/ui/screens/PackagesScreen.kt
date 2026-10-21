package com.example.ui.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.FilterChip
import androidx.compose.material3.FilterChipDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.example.model.TourPackage
import com.example.ui.components.PackageCard
import com.example.ui.theme.OceanBluePrimary
import com.example.ui.theme.TealSecondary

@Composable
fun PackagesScreen(
    packages: List<TourPackage>,
    onBookPackage: (TourPackage) -> Unit,
    initialDestinationFilter: String? = null,
    modifier: Modifier = Modifier
) {
    var selectedDestinationId by remember(initialDestinationFilter) {
        mutableStateOf(initialDestinationFilter)
    }

    val destinations = listOf(
        Pair("all", "All Packages"),
        Pair("goa", "Goa"),
        Pair("manali", "Manali"),
        Pair("kerala", "Kerala"),
        Pair("jaipur", "Jaipur"),
        Pair("taj_mahal", "Taj Mahal")
    )

    val filteredPackages by remember(packages, selectedDestinationId) {
        derivedStateOf {
            if (selectedDestinationId == null || selectedDestinationId == "all") {
                packages
            } else {
                packages.filter { it.destinationId == selectedDestinationId }
            }
        }
    }

    Column(
        modifier = modifier
            .fillMaxSize()
            .testTag("packages_screen")
    ) {
        Column(modifier = Modifier.padding(horizontal = 16.dp, vertical = 8.dp)) {
            Text(
                text = "Tour Packages",
                style = MaterialTheme.typography.titleLarge,
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.onSurface
            )
            Text(
                text = "Showing duration, pricing and inclusions for each destination",
                style = MaterialTheme.typography.bodySmall,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
        }

        // Destination Filter Chips
        LazyRow(
            contentPadding = PaddingValues(horizontal = 16.dp),
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            items(destinations) { (destId, label) ->
                val isSelected = (selectedDestinationId == null && destId == "all") || (selectedDestinationId == destId)
                FilterChip(
                    selected = isSelected,
                    onClick = {
                        selectedDestinationId = if (destId == "all") null else destId
                    },
                    label = { Text(label) },
                    colors = FilterChipDefaults.filterChipColors(
                        selectedContainerColor = OceanBluePrimary,
                        selectedLabelColor = MaterialTheme.colorScheme.surface
                    ),
                    modifier = Modifier.testTag("pkg_filter_$destId")
                )
            }
        }

        Spacer(modifier = Modifier.height(12.dp))

        LazyColumn(
            contentPadding = PaddingValues(start = 16.dp, end = 16.dp, bottom = 24.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            items(filteredPackages, key = { it.id }) { pkg ->
                PackageCard(
                    tourPackage = pkg,
                    onBookClick = { onBookPackage(pkg) }
                )
            }
        }
    }
}
