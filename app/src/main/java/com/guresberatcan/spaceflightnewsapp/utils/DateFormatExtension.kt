package com.guresberatcan.spaceflightnewsapp.utils

import android.os.Build
import java.text.ParseException
import java.text.SimpleDateFormat
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.util.Date
import java.util.Locale

fun String.convertDate(): String {
    return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
        val parsedDate = LocalDateTime.parse(
            this, DateTimeFormatter.ISO_DATE_TIME
        )
        parsedDate.format(DateTimeFormatter.ofPattern("HH:mm - dd.MM.yyyy"))
    } else {
        val outFormat = SimpleDateFormat("dd MM", Locale.getDefault())
        outFormat.format(this)
    }
}