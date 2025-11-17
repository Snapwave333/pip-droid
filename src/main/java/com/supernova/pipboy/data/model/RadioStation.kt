package com.supernova.pipboy.data.model

/**
 * Radio station data model
 */
data class RadioStation(
    val id: String,
    val name: String,
    val description: String,
    val location: String,
    val streamUrl: String,
    val genre: RadioGenre = RadioGenre.FALLOUT,
    val isLocal: Boolean = false,
    val frequency: Float? = null // FM frequency in MHz
)

enum class RadioGenre {
    FALLOUT,
    LOCAL_FM,
    LOCAL_AM,
    INTERNET
}

/**
 * Predefined Fallout.FM stations with streaming URLs
 */
object FalloutStations {
    val GALAXY_NEWS_RADIO = RadioStation(
        id = "gnr",
        name = "GALAXY NEWS RADIO",
        description = "Three Dog's legendary Fallout 3 station",
        location = "Capital Wasteland",
        streamUrl = "https://fallout.fm/gnr",
        genre = RadioGenre.FALLOUT
    )

    val RADIO_NEW_VEGAS = RadioStation(
        id = "rnv",
        name = "RADIO NEW VEGAS",
        description = "Mr. New Vegas from the Mojave",
        location = "New Vegas",
        streamUrl = "https://fallout.fm/rnv",
        genre = RadioGenre.FALLOUT
    )

    val DIAMOND_CITY_RADIO = RadioStation(
        id = "dcr",
        name = "DIAMOND CITY RADIO",
        description = "Travis Miles from Fallout 4",
        location = "Commonwealth",
        streamUrl = "https://fallout.fm/dcr",
        genre = RadioGenre.FALLOUT
    )

    val CLASSICAL_RADIO = RadioStation(
        id = "classical",
        name = "FALLOUT 4 CLASSICAL",
        description = "Classical music from the Commonwealth",
        location = "Commonwealth",
        streamUrl = "https://fallout.fm/classical",
        genre = RadioGenre.FALLOUT
    )

    val FALLOUT_FM_MAIN = RadioStation(
        id = "main",
        name = "FALLOUT.FM MAIN",
        description = "All Fallout music in one place",
        location = "Worldwide",
        streamUrl = "https://fallout.fm/main",
        genre = RadioGenre.FALLOUT
    )

    val APPALACHIA_RADIO = RadioStation(
        id = "appalachia",
        name = "FALLOUT 76 APPALACHIA",
        description = "Radio waves from Appalachia",
        location = "Appalachia",
        streamUrl = "https://fallout.fm/appalachia",
        genre = RadioGenre.FALLOUT
    )

    val FALLOUT_1_OST = RadioStation(
        id = "fo1",
        name = "FALLOUT 1 OST",
        description = "Original ambient soundtrack",
        location = "West Coast",
        streamUrl = "https://fallout.fm/fo1",
        genre = RadioGenre.FALLOUT
    )

    val FALLOUT_2_OST = RadioStation(
        id = "fo2",
        name = "FALLOUT 2 OST",
        description = "The Chosen One's journey",
        location = "West Coast",
        streamUrl = "https://fallout.fm/fo2",
        genre = RadioGenre.FALLOUT
    )

    /**
     * Get all predefined stations
     */
    fun getAllStations() = listOf(
        GALAXY_NEWS_RADIO,
        RADIO_NEW_VEGAS,
        DIAMOND_CITY_RADIO,
        CLASSICAL_RADIO,
        FALLOUT_FM_MAIN,
        APPALACHIA_RADIO,
        FALLOUT_1_OST,
        FALLOUT_2_OST
    )
}
