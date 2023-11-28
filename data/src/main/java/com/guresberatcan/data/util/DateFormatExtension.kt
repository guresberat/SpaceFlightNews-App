package com.guresberatcan.data.util

import android.os.Build
import java.text.SimpleDateFormat
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.util.Locale

fun String.convertDate(): String {
    return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
        val parsedDate = LocalDateTime.parse(
            this, DateTimeFormatter.ISO_DATE_TIME
        )
        parsedDate.format(DateTimeFormatter.ofPattern("HH:mm - dd.MM.yyyy"))
    } else {
        val inputFormat = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss", Locale.getDefault())
        val outputFormat = SimpleDateFormat("HH:mm - dd.MM.yyyy", Locale.getDefault())

        val date = inputFormat.parse(this)
        return date?.let { outputFormat.format(it) } ?: this
    }
}