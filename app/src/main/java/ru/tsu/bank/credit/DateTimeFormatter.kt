package ru.tsu.bank.credit

import java.text.SimpleDateFormat
import java.util.*

object DateTimeFormatter {

    // The error will be noticeable in the logs one way or another, there is no reason to provide different behavior
    // to child types of exceptions
    @Suppress("TooGenericExceptionCaught")
    fun formatServerDate(value: String): String? = try {
        val formattedString = value
            .replace('T', ' ')
            .replace('Z', ' ')
            .substringBefore('.')
        val serverDateFormat = SimpleDateFormat("yyyy-MM-dd HH:mm:ss",Locale.getDefault() )
        val dateFormat = SimpleDateFormat("dd MMM, yyyy HH:mm",Locale.getDefault() )
        val date = serverDateFormat.parse(formattedString)
        if (date != null) {
            dateFormat
                .format(date)
                .replace(".", "")
                .takeIf { it.isNotBlank() }
        } else {
            null
        }
    } catch (e: Exception) {
        null
    }
//2023-03-02T19:05:30.076Z
    @Suppress("TooGenericExceptionCaught")
    fun toServerDate(date:Date): String? {
        val serverDateFormat = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'.076Z'", Locale.getDefault())
        return serverDateFormat.format(date)
    }
}