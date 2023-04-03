package ru.tsu.domain.account.usecases

import android.annotation.SuppressLint
import java.text.SimpleDateFormat
import java.util.Calendar


@SuppressLint("SimpleDateFormat")
fun getCurrentData(): String {
    val currentDate = Calendar.getInstance().time
    val simpleDateFormat = SimpleDateFormat("yyyy-MM-dd HH:mm:ss z")
    return simpleDateFormat.format(currentDate)
}
