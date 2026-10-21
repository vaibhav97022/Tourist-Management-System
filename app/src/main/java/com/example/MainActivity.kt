package com.example

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.BackHandler
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.BookOnline
import androidx.compose.material.icons.filled.ConfirmationNumber
import androidx.compose.material.icons.filled.Explore
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Luggage
import androidx.compose.material.icons.filled.School
import androidx.compose.material.icons.outlined.BookOnline
import androidx.compose.material.icons.outlined.ConfirmationNumber
import androidx.compose.material.icons.outlined.Explore
import androidx.compose.material.icons.outlined.Home
import androidx.compose.material.icons.outlined.Luggage
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.ui.components.BookingSuccessDialog
import com.example.ui.screens.AboutScreen
import com.example.ui.screens.BookingScreen
import com.example.ui.screens.DestinationsScreen
import com.example.ui.screens.HomeScreen
import com.example.ui.screens.MyBookingsScreen
import com.example.ui.screens.PackagesScreen
import com.example.ui.theme.MyApplicationTheme
import com.example.ui.theme.OceanBluePrimary
import com.example.viewmodel.TouristViewModel

enum class AppScreen(
    val title: String,
    val selectedIcon: ImageVector,
    val unselectedIcon: ImageVector
) {
    HOME("Home", Icons.Filled.Home, Icons.Outlined.Home),
    DESTINATIONS("Destinations", Icons.Filled.Explore, Icons.Outlined.Explore),
    PACKAGES("Packages", Icons.Filled.Luggage, Icons.Outlined.Luggage),
    BOOKING("Book", Icons.Filled.BookOnline, Icons.Outlined.BookOnline),
    MY_BOOKINGS("Bookings", Icons.Filled.ConfirmationNumber, Icons.Outlined.ConfirmationNumber),
    ABOUT("About & Viva", Icons.Filled.School, Icons.Filled.School)
}

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            MyApplicationTheme {
                TouristApp()
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TouristApp(
    viewModel: TouristViewModel = viewModel()
) {
    var currentScreen by remember { mutableStateOf(AppScreen.HOME) }
    var initialPackageFilter by remember { mutableStateOf<String?>(null) }

    val destinations by viewModel.destinations.collectAsStateWithLifecycle()
    val packages by viewModel.packages.collectAsStateWithLifecycle()
    val bookings by viewModel.bookings.collectAsStateWithLifecycle()
    val formState by viewModel.formState.collectAsStateWithLifecycle()
    val confirmedBooking by viewModel.confirmedBooking.collectAsStateWithLifecycle()

    // Handle back button: if not on Home screen, go back to Home
    BackHandler(enabled = currentScreen != AppScreen.HOME) {
        currentScreen = AppScreen.HOME
    }

    // Success Confirmation Dialog
    confirmedBooking?.let { booking ->
        BookingSuccessDialog(
            booking = booking,
            onDismiss = { viewModel.dismissConfirmationDialog() },
            onViewMyBookings = {
                currentScreen = AppScreen.MY_BOOKINGS
            }
        )
    }

    Scaffold(
        modifier = Modifier
            .fillMaxSize()
            .testTag("tourist_app_root"),
        topBar = {
            CenterAlignedTopAppBar(
                title = {
                    Text(
                        text = when (currentScreen) {
                            AppScreen.HOME -> "Tourist Management"
                            AppScreen.DESTINATIONS -> "Destinations"
                            AppScreen.PACKAGES -> "Tour Packages"
                            AppScreen.BOOKING -> "Book Tour"
                            AppScreen.MY_BOOKINGS -> "My Bookings"
                            AppScreen.ABOUT -> "About & Viva Guide"
                        },
                        style = MaterialTheme.typography.titleMedium,
                        fontWeight = FontWeight.Bold
                    )
                },
                navigationIcon = {
                    if (currentScreen == AppScreen.ABOUT) {
                        IconButton(
                            onClick = { currentScreen = AppScreen.HOME },
                            modifier = Modifier.testTag("topbar_back_btn")
                        ) {
                            Icon(imageVector = Icons.Default.ArrowBack, contentDescription = "Back")
                        }
                    }
                },
                actions = {
                    if (currentScreen != AppScreen.ABOUT) {
                        IconButton(
                            onClick = { currentScreen = AppScreen.ABOUT },
                            modifier = Modifier.testTag("topbar_viva_btn")
                        ) {
                            Icon(
                                imageVector = Icons.Default.School,
                                contentDescription = "Viva & About",
                                tint = OceanBluePrimary
                            )
                        }
                    }
                },
                colors = TopAppBarDefaults.centerAlignedTopAppBarColors(
                    containerColor = MaterialTheme.colorScheme.surface
                )
            )
        },
        bottomBar = {
            NavigationBar(
                containerColor = MaterialTheme.colorScheme.surface,
                tonalElevation = 6.dp,
                modifier = Modifier.testTag("bottom_nav_bar")
            ) {
                val bottomNavItems = listOf(
                    AppScreen.HOME,
                    AppScreen.DESTINATIONS,
                    AppScreen.PACKAGES,
                    AppScreen.BOOKING,
                    AppScreen.MY_BOOKINGS
                )

                bottomNavItems.forEach { screen ->
                    val isSelected = currentScreen == screen
                    NavigationBarItem(
                        selected = isSelected,
                        onClick = { currentScreen = screen },
                        icon = {
                            Icon(
                                imageVector = if (isSelected) screen.selectedIcon else screen.unselectedIcon,
                                contentDescription = screen.title
                            )
                        },
                        label = {
                            Text(
                                text = screen.title,
                                style = MaterialTheme.typography.labelSmall,
                                fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Normal
                            )
                        },
                        colors = NavigationBarItemDefaults.colors(
                            selectedIconColor = OceanBluePrimary,
                            selectedTextColor = OceanBluePrimary,
                            indicatorColor = MaterialTheme.colorScheme.primaryContainer
                        ),
                        modifier = Modifier.testTag("nav_item_${screen.name.lowercase()}")
                    )
                }
            }
        }
    ) { innerPadding ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
        ) {
            when (currentScreen) {
                AppScreen.HOME -> {
                    HomeScreen(
                        destinations = destinations,
                        popularPackages = packages,
                        onNavigateToDestinations = { currentScreen = AppScreen.DESTINATIONS },
                        onNavigateToPackages = { currentScreen = AppScreen.PACKAGES },
                        onNavigateToBooking = { currentScreen = AppScreen.BOOKING },
                        onNavigateToMyBookings = { currentScreen = AppScreen.MY_BOOKINGS },
                        onNavigateToAbout = { currentScreen = AppScreen.ABOUT },
                        onSelectDestination = { dest ->
                            viewModel.prefillBookingForDestination(dest)
                            currentScreen = AppScreen.BOOKING
                        },
                        onSelectPackage = { pkg ->
                            viewModel.prefillBookingForPackage(pkg)
                            currentScreen = AppScreen.BOOKING
                        }
                    )
                }

                AppScreen.DESTINATIONS -> {
                    DestinationsScreen(
                        destinations = destinations,
                        onBookTour = { dest ->
                            viewModel.prefillBookingForDestination(dest)
                            currentScreen = AppScreen.BOOKING
                        },
                        onExplorePackages = { dest ->
                            initialPackageFilter = dest.id
                            currentScreen = AppScreen.PACKAGES
                        }
                    )
                }

                AppScreen.PACKAGES -> {
                    PackagesScreen(
                        packages = packages,
                        initialDestinationFilter = initialPackageFilter,
                        onBookPackage = { pkg ->
                            viewModel.prefillBookingForPackage(pkg)
                            currentScreen = AppScreen.BOOKING
                        }
                    )
                }

                AppScreen.BOOKING -> {
                    BookingScreen(
                        destinations = destinations,
                        packages = packages,
                        formState = formState,
                        onTouristNameChange = { viewModel.onTouristNameChange(it) },
                        onPhoneNumberChange = { viewModel.onPhoneNumberChange(it) },
                        onEmailChange = { viewModel.onEmailChange(it) },
                        onDestinationSelected = { viewModel.onDestinationSelected(it) },
                        onPackageSelected = { viewModel.onPackageSelected(it) },
                        onTravelDateChange = { viewModel.onTravelDateChange(it) },
                        onTravellersCountChange = { viewModel.onTravellersCountChange(it) },
                        onSpecialNotesChange = { viewModel.onSpecialNotesChange(it) },
                        onSubmitBooking = { viewModel.submitBooking() }
                    )
                }

                AppScreen.MY_BOOKINGS -> {
                    MyBookingsScreen(
                        bookings = bookings,
                        onCancelBooking = { viewModel.cancelBooking(it) },
                        onNavigateToBooking = { currentScreen = AppScreen.BOOKING }
                    )
                }

                AppScreen.ABOUT -> {
                    AboutScreen()
                }
            }
        }
    }
}
