package com.guresberatcan.data.util

import android.os.Build
import java.text.SimpleDateFormat
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.util.Locale

/**
 * Extension function to convert a date string to a formatted date string.
 *
 * @return The formatted date string.
 */
fun String.convertDate(): String {
    return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
        // If API level is 26 or higher, use Java 8 time API for parsing and formatting
        val parsedDate = LocalDateTime.parse(
            this, DateTimeFormatter.ISO_DATE_TIME
        )
        parsedDate.format(DateTimeFormatter.ofPattern("HH:mm - dd.MM.yyyy"))
    } else {
        // For lower API levels, use SimpleDateFormat for parsing and formatting
        val inputFormat = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss", Locale.getDefault())
        val outputFormat = SimpleDateFormat("HH:mm - dd.MM.yyyy", Locale.getDefault())

        val date = inputFormat.parse(this)
        return date?.let { outputFormat.format(it) } ?: this
    }
}