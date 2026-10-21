package com.example.data

import com.example.model.Booking
import com.example.model.BookingStatus
import com.example.model.Destination
import com.example.model.TourPackage
import com.example.model.VivaQuestion

object SampleData {

    val destinations: List<Destination> = listOf(
        Destination(
            id = "goa",
            name = "Goa",
            state = "Goa",
            tagline = "Golden Beaches, Water Sports & Portuguese Charm",
            description = "Famous for its pristine sandy coastlines, lively beach shacks, 17th-century churches, spice plantations, and vibrant coastal culture. Perfect for relaxation and aquatic adventures.",
            category = "Beach & Coastal",
            topAttractions = listOf("Baga & Calangute Beach", "Aguada Fort", "Basilica of Bom Jesus", "Dudhsagar Waterfalls", "Anjuna Flea Market"),
            bestTimeToVisit = "November to March",
            idealDuration = "4 to 5 Days",
            rating = 4.8,
            reviewsCount = 1420,
            startingPrice = 8999,
            emoji = "🏖️",
            gradientColors = Pair(0xFF0284C7, 0xFF0D9488) // Blue to Teal
        ),
        Destination(
            id = "manali",
            name = "Manali",
            state = "Himachal Pradesh",
            tagline = "Valley of the Gods, Snow Peaks & Pine Forests",
            description = "Nestled in the breathtaking Himalayas along the Beas River, Manali offers thrilling adventure sports, snow-capped mountain views, hot springs, and serene apple orchards.",
            category = "Hills & Mountains",
            topAttractions = listOf("Solang Valley", "Rohtang Pass", "Hadimba Temple", "Jogini Waterfall", "Old Manali Cafes"),
            bestTimeToVisit = "October to June",
            idealDuration = "5 to 6 Days",
            rating = 4.9,
            reviewsCount = 1860,
            startingPrice = 11499,
            emoji = "🏔️",
            gradientColors = Pair(0xFF0369A1, 0xFF0284C7) // Deep Blue to Sky Blue
        ),
        Destination(
            id = "kerala",
            name = "Kerala",
            state = "Kerala",
            tagline = "God's Own Country, Tranquil Backwaters & Tea Hills",
            description = "Renowned for its emerald palm-fringed backwaters, traditional houseboats, misty Munnar tea hills, wildlife reserves, and authentic Ayurvedic wellness therapies.",
            category = "Backwaters & Nature",
            topAttractions = listOf("Alleppey Houseboats", "Munnar Tea Gardens", "Periyar National Park", "Kovalam Beach", "Athirappilly Falls"),
            bestTimeToVisit = "September to March",
            idealDuration = "5 to 7 Days",
            rating = 4.9,
            reviewsCount = 2150,
            startingPrice = 13999,
            emoji = "🌴",
            gradientColors = Pair(0xFF0D9488, 0xFF0F766E) // Teal to Deep Teal
        ),
        Destination(
            id = "jaipur",
            name = "Jaipur",
            state = "Rajasthan",
            tagline = "The Royal Pink City of Majestic Forts & Palaces",
            description = "A UNESCO World Heritage city bursting with royal splendor, grandiose hill forts, ornate pink sandstone palaces, lively colorful bazaars, and rich Rajputana history.",
            category = "Heritage & Culture",
            topAttractions = listOf("Hawa Mahal", "Amer Fort & Palace", "City Palace", "Jantar Mantar", "Nahargarh Fort Sunset"),
            bestTimeToVisit = "October to March",
            idealDuration = "3 to 4 Days",
            rating = 4.7,
            reviewsCount = 1290,
            startingPrice = 9999,
            emoji = "🏰",
            gradientColors = Pair(0xFF0284C7, 0xFF0F766E) // Blue to Teal Green
        ),
        Destination(
            id = "taj_mahal",
            name = "Taj Mahal",
            state = "Agra, Uttar Pradesh",
            tagline = "World Wonder of White Marble & Mughal Romance",
            description = "An immortal symbol of love built by Emperor Shah Jahan on the banks of Yamuna River. Its symmetry, delicate marble inlay work, and lush gardens captivate visitors from across the world.",
            category = "Wonder of the World",
            topAttractions = listOf("Taj Mahal Sunrise View", "Agra Fort", "Mehtab Bagh", "Fatehpur Sikri", "Itmad-ud-Daulah"),
            bestTimeToVisit = "October to April",
            idealDuration = "2 to 3 Days",
            rating = 5.0,
            reviewsCount = 3800,
            startingPrice = 5499,
            emoji = "🕌",
            gradientColors = Pair(0xFF0369A1, 0xFF0D9488) // Deep Blue to Teal
        )
    )

    val tourPackages: List<TourPackage> = listOf(
        TourPackage(
            id = "pkg_goa_01",
            title = "Goa Coastal Beach & Cruise Explorer",
            destinationId = "goa",
            destinationName = "Goa",
            durationDays = 4,
            durationNights = 3,
            pricePerPerson = 12499,
            rating = 4.8,
            reviewsCount = 420,
            badge = "Popular",
            inclusions = listOf("4-Star Resort Stay", "Mandovi Sunset Cruise", "Daily Breakfast & Dinner", "North & South Goa Cab", "Water Sports Voucher"),
            highlights = listOf("Water sports at Baga", "Fort Aguada heritage walk", "Old Goa Cathedral tour", "Candlelit beach shack dinner"),
            itineraryHighlights = listOf("Day 1: Arrival & Calangute Beach", "Day 2: Fort Aguada & Dolphin Cruise", "Day 3: South Goa & Mandovi River", "Day 4: Flea Market & Departure")
        ),
        TourPackage(
            id = "pkg_goa_02",
            title = "Goa Weekend Getaway & Water Sports",
            destinationId = "goa",
            destinationName = "Goa",
            durationDays = 3,
            durationNights = 2,
            pricePerPerson = 8999,
            rating = 4.7,
            reviewsCount = 280,
            badge = "Budget Friendly",
            inclusions = listOf("Beachside Hotel Stay", "Parasailing & Jet Ski", "Breakfast Included", "Airport Transfers"),
            highlights = listOf("Jet Skiing & Banana Ride", "Anjuna sunset views", "Goan seafood tasting"),
            itineraryHighlights = listOf("Day 1: Check-in & Beach Walk", "Day 2: Water Sports & Nightlife", "Day 3: Souvenir Shopping & Departure")
        ),
        TourPackage(
            id = "pkg_manali_01",
            title = "Manali Snow Valley & Rohtang Pass Adventure",
            destinationId = "manali",
            destinationName = "Manali",
            durationDays = 5,
            durationNights = 4,
            pricePerPerson = 14999,
            rating = 4.9,
            reviewsCount = 580,
            badge = "Bestseller",
            inclusions = listOf("Deluxe Valley View Hotel", "Rohtang Pass Snow Permit", "Daily Breakfast & Dinner", "Private Heater Cab", "Paragliding Assistance"),
            highlights = listOf("Snow activities at Solang Valley", "Atal Tunnel expedition", "Old Manali cafe hopping", "Vashisht Hot Water Springs"),
            itineraryHighlights = listOf("Day 1: Arrival in Manali & Mall Road", "Day 2: Solang Valley & Zorbing", "Day 3: Rohtang Pass / Atal Tunnel", "Day 4: Hadimba Temple & Vashisht", "Day 5: River Rafting & Departure")
        ),
        TourPackage(
            id = "pkg_manali_02",
            title = "Manali Quick Himalayan Escape",
            destinationId = "manali",
            destinationName = "Manali",
            durationDays = 4,
            durationNights = 3,
            pricePerPerson = 11499,
            rating = 4.8,
            reviewsCount = 310,
            badge = "Value",
            inclusions = listOf("3-Star Resort", "Breakfast Included", "Sightseeing Cab", "Bonfire Night"),
            highlights = listOf("Solang valley ropeway", "Hadimba pine woods", "Local trout fish dining"),
            itineraryHighlights = listOf("Day 1: Welcome & Leisure", "Day 2: Solang Adventure", "Day 3: Kullu Shawl Factory & Rafting", "Day 4: Check-out")
        ),
        TourPackage(
            id = "pkg_kerala_01",
            title = "Kerala Premium Houseboat & Munnar Hills",
            destinationId = "kerala",
            destinationName = "Kerala",
            durationDays = 5,
            durationNights = 4,
            pricePerPerson = 16999,
            rating = 4.9,
            reviewsCount = 640,
            badge = "Top Rated",
            inclusions = listOf("1 Night Luxury Houseboat", "3 Nights Munnar Hill Resort", "All Meals on Houseboat", "Private AC Sedan", "Tea Plantation Entry"),
            highlights = listOf("Alleppey backwater cruise", "Munnar tea museum & waterfalls", "Kathakali cultural performance", "Spice garden tour"),
            itineraryHighlights = listOf("Day 1: Cochin to Munnar Tea Hills", "Day 2: Mattupetty Dam & Eravikulam", "Day 3: Transfer to Alleppey Houseboat", "Day 4: Backwater village cruise", "Day 5: Cochin Sightseeing & Departure")
        ),
        TourPackage(
            id = "pkg_kerala_02",
            title = "Kerala Backwater Bliss Weekend",
            destinationId = "kerala",
            destinationName = "Kerala",
            durationDays = 3,
            durationNights = 2,
            pricePerPerson = 13999,
            rating = 4.8,
            reviewsCount = 290,
            badge = "Relaxing",
            inclusions = listOf("Deluxe Houseboat Stay", "Traditional Kerala Meals", "Ayurvedic Massage Voucher", "Cochin Transfers"),
            highlights = listOf("Paddy fields cruise", "Village fishing experience", "Authentic sadhya lunch"),
            itineraryHighlights = listOf("Day 1: Houseboat check-in & Sunset", "Day 2: Canal kayaking & Village walk", "Day 3: Breakfast & Transfer back")
        ),
        TourPackage(
            id = "pkg_jaipur_01",
            title = "Royal Jaipur Heritage & Palaces Tour",
            destinationId = "jaipur",
            destinationName = "Jaipur",
            durationDays = 4,
            durationNights = 3,
            pricePerPerson = 11999,
            rating = 4.7,
            reviewsCount = 370,
            badge = "Heritage",
            inclusions = listOf("Heritage Haveli Stay", "Elephant/Jeep Ride at Amer", "Daily Breakfast & Dinner", "Chokhi Dhani Cultural Dinner", "Certified Tour Guide"),
            highlights = listOf("Hawa Mahal photo stop", "Amer Fort mirror hall (Sheesh Mahal)", "Jantar Mantar sundial tour", "Johari Bazaar gem shopping"),
            itineraryHighlights = listOf("Day 1: City Palace & Hawa Mahal", "Day 2: Amer Fort & Nahargarh Sunset", "Day 3: Albert Hall Museum & Bazaars", "Day 4: Chokhi Dhani & Departure")
        ),
        TourPackage(
            id = "pkg_taj_01",
            title = "Taj Mahal & Golden Triangle Splendor",
            destinationId = "taj_mahal",
            destinationName = "Taj Mahal",
            durationDays = 3,
            durationNights = 2,
            pricePerPerson = 7499,
            rating = 5.0,
            reviewsCount = 890,
            badge = "Must Visit",
            inclusions = listOf("4-Star Hotel near Taj", "Taj Mahal Sunrise Ticket", "Agra Fort Entry Passes", "Private AC Cab", "Expert Historian Guide"),
            highlights = listOf("Sunrise view at Taj Mahal", "Agra Fort Mughal pavilions", "Sunset view from Mehtab Bagh", "Petha sweet tasting"),
            itineraryHighlights = listOf("Day 1: Arrival & Agra Fort", "Day 2: Taj Mahal Sunrise & Mehtab Bagh", "Day 3: Fatehpur Sikri & Departure")
        ),
        TourPackage(
            id = "pkg_taj_02",
            title = "Taj Mahal Express Day-and-Night Tour",
            destinationId = "taj_mahal",
            destinationName = "Taj Mahal",
            durationDays = 2,
            durationNights = 1,
            pricePerPerson = 5499,
            rating = 4.9,
            reviewsCount = 410,
            badge = "Express",
            inclusions = listOf("3-Star Hotel Stay", "Breakfast Included", "Taj Mahal Monument Entry", "Round-trip Station Transfer"),
            highlights = listOf("Skip-the-line Taj entry", "Mughal marble inlay demonstration", "Yamuna riverbank views"),
            itineraryHighlights = listOf("Day 1: Check-in, Agra Fort & Sunset", "Day 2: Early Morning Taj & Return")
        )
    )

    val initialBookings: List<Booking> = listOf(
        Booking(
            id = "b1",
            bookingRef = "TMS-2026-GOA-1084",
            touristName = "Rahul Sharma",
            phoneNumber = "9876543210",
            email = "rahul.sharma@example.com",
            destinationName = "Goa",
            packageName = "Goa Coastal Beach & Cruise Explorer",
            travelDate = "2026-11-15",
            numberOfTravellers = 2,
            pricePerPerson = 12499,
            basePrice = 24998,
            gstAmount = 1250,
            totalPrice = 26248,
            specialNotes = "Requesting sea-view room and late check-in.",
            status = BookingStatus.CONFIRMED,
            bookingTimestamp = "2026-10-18 10:30 AM"
        ),
        Booking(
            id = "b2",
            bookingRef = "TMS-2026-TAJ-4921",
            touristName = "Priya Patel",
            phoneNumber = "9123456780",
            email = "priya.patel@example.com",
            destinationName = "Taj Mahal",
            packageName = "Taj Mahal & Golden Triangle Splendor",
            travelDate = "2026-12-05",
            numberOfTravellers = 3,
            pricePerPerson = 7499,
            basePrice = 22497,
            gstAmount = 1125,
            totalPrice = 23622,
            specialNotes = "Early morning sunrise entry preferred for photography.",
            status = BookingStatus.CONFIRMED,
            bookingTimestamp = "2026-10-20 02:15 PM"
        )
    )

    val vivaQuestions: List<VivaQuestion> = listOf(
        VivaQuestion(
            topic = "Jetpack Compose",
            question = "What is Jetpack Compose and why is it preferred over XML layouts?",
            answer = "Jetpack Compose is Android's modern declarative UI toolkit. Instead of imperatively mutating XML views via findViewById(), Compose builds the UI using pure Kotlin functions that automatically re-render (recompose) when state updates. It reduces boilerplate code, eliminates view hierarchy bugs, and enables easier dynamic theming."
        ),
        VivaQuestion(
            topic = "Architecture",
            question = "Explain the MVVM Architecture used in this Tourist Management System.",
            answer = "MVVM stands for Model-View-ViewModel:\n• Model (Destination, TourPackage, Booking): Holds business data entities.\n• ViewModel (TouristViewModel): Encapsulates application state using Kotlin StateFlow and processes business operations (booking validation, filtering, status updates).\n• View (Compose Screens): Observes state via collectAsStateWithLifecycle() and renders UI declaratively, sending user events back to ViewModel."
        ),
        VivaQuestion(
            topic = "State Management",
            question = "What is Unidirectional Data Flow (UDF) in this app?",
            answer = "State flows down from the ViewModel to the Composables through StateFlow, and user actions (events such as clicking 'Book Now' or submitting the form) flow up from Composables to the ViewModel. This ensures a single source of truth, making the app easier to test, debug, and demonstrate during viva."
        ),
        VivaQuestion(
            topic = "Recomposition",
            question = "What is Recomposition in Jetpack Compose?",
            answer = "Recomposition is the process of calling Composable functions again when their inputs or observed states change. Compose intelligently skips composables whose parameters have not changed, maximizing rendering efficiency."
        ),
        VivaQuestion(
            topic = "Validation",
            question = "How does the booking form validation work?",
            answer = "The ViewModel validates each field: tourist name must be non-empty and alphabetic (>= 3 chars), phone number must be exactly 10 Indian digits, destination and travel date must be selected, and number of travellers must be between 1 and 20. If valid, an in-memory Booking entity with a unique reference code (e.g. TMS-2026-XXX) is created and appended to the bookings list."
        )
    )
}
