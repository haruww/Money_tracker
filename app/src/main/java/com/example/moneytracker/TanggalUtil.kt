package com.example.moneytracker

import com.google.android.material.datepicker.CalendarConstraints
import com.google.android.material.datepicker.DateValidatorPointBackward
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Date
import java.util.Locale

fun formatTanggal(millis: Long): String {
    val  pola = SimpleDateFormat("dd MMM yyyy, HH:mm", Locale.forLanguageTag("id-ID"))
    return pola.format(Date(millis))
}

fun batasTidakMasaDepan(): CalendarConstraints =
    CalendarConstraints.Builder()
        .setValidator(DateValidatorPointBackward.now())
        .build()