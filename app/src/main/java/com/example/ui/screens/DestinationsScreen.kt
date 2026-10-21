package com.example.ui.screens

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
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Explore
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.FilterChip
import androidx.compose.material3.FilterChipDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
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
import com.example.model.Destination
import com.example.ui.components.DestinationCard
import com.example.ui.theme.OceanBluePrimary
import com.example.ui.theme.TealSecondary

@Composable
fun DestinationsScreen(
    destinations: List<Destination>,
    onBookTour: (Destination) -> Unit,
    onExplorePackages: (Destination) -> Unit,
    modifier: Modifier = Modifier
) {
    var searchQuery by remember { mutableStateOf("") }
    var selectedFilter by remember { mutableStateOf<String?>(null) }

    val filteredDestinations by remember(destinations, searchQuery, selectedFilter) {
        derivedStateOf {
            destinations.filter { dest ->
                val matchesFilter = selectedFilter == null || dest.id == selectedFilter
                val query = searchQuery.trim().lowercase()
                val matchesQuery = query.isEmpty() ||
                        dest.name.lowercase().contains(query) ||
                        dest.state.lowercase().contains(query) ||
                        dest.tagline.lowercase().contains(query) ||
                        dest.topAttractions.any { it.lowercase().contains(query) }
                matchesFilter && matchesQuery
            }
        }
    }

    Column(
        modifier = modifier
            .fillMaxSize()
            .testTag("destinations_screen")
    ) {
        // Search Input
        OutlinedTextField(
            value = searchQuery,
            onValueChange = { searchQuery = it },
            placeholder = { Text("Search Goa, Manali, Kerala, Jaipur, Taj Mahal...") },
            leadingIcon = {
                Icon(
                    imageVector = Icons.Default.Search,
                    contentDescription = "Search",
                    tint = OceanBluePrimary
                )
            },
            trailingIcon = {
                if (searchQuery.isNotEmpty()) {
                    IconButton(onClick = { searchQuery = "" }) {
                        Icon(imageVector = Icons.Default.Close, contentDescription = "Clear")
                    }
                }
            },
            singleLine = true,
            shape = RoundedCornerShape(16.dp),
            colors = OutlinedTextFieldDefaults.colors(
                focusedBorderColor = OceanBluePrimary,
                unfocusedBorderColor = MaterialTheme.colorScheme.outline
            ),
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp, vertical = 8.dp)
                .testTag("destination_search_input")
        )

        // Destination Filter Chips
        LazyRow(
            contentPadding = PaddingValues(horizontal = 16.dp),
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            item {
                FilterChip(
                    selected = selectedFilter == null,
                    onClick = { selectedFilter = null },
                    label = { Text("All (5)") },
                    colors = FilterChipDefaults.filterChipColors(
                        selectedContainerColor = OceanBluePrimary,
                        selectedLabelColor = MaterialTheme.colorScheme.surface
                    ),
                    modifier = Modifier.testTag("filter_all")
                )
            }
            items(destinations) { dest ->
                FilterChip(
                    selected = selectedFilter == dest.id,
                    onClick = {
                        selectedFilter = if (selectedFilter == dest.id) null else dest.id
                    },
                    label = { Text("${dest.emoji} ${dest.name}") },
                    colors = FilterChipDefaults.filterChipColors(
                        selectedContainerColor = TealSecondary,
                        selectedLabelColor = MaterialTheme.colorScheme.surface
                    ),
                    modifier = Modifier.testTag("filter_${dest.id}")
                )
            }
        }

        Spacer(modifier = Modifier.height(8.dp))

        // Results List
        if (filteredDestinations.isEmpty()) {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(32.dp),
                contentAlignment = Alignment.Center
            ) {
                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                    Icon(
                        imageVector = Icons.Default.Explore,
                        contentDescription = null,
                        tint = MaterialTheme.colorScheme.onSurfaceVariant,
                        modifier = Modifier.size(64.dp)
                    )
                    Spacer(modifier = Modifier.height(16.dp))
                    Text(
                        text = "No destinations match \"$searchQuery\"",
                        style = MaterialTheme.typography.titleMedium,
                        fontWeight = FontWeight.SemiBold,
                        color = MaterialTheme.colorScheme.onSurface
                    )
                    Text(
                        text = "Try searching for beaches, mountains, palaces or wonder.",
                        style = MaterialTheme.typography.bodyMedium,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                }
            }
        } else {
            LazyColumn(
                contentPadding = PaddingValues(start = 16.dp, end = 16.dp, bottom = 24.dp),
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                items(filteredDestinations, key = { it.id }) { destination ->
                    DestinationCard(
                        destination = destination,
                        onBookClick = { onBookTour(destination) },
                        onExplorePackagesClick = { onExplorePackages(destination) }
                    )
                }
            }
        }
    }
}
