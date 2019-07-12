package com.arsoft.caldatelib

import java.util.*

fun Date.isToday(): Boolean {

    val today = Calendar.getInstance()
    val compareDate = Calendar.getInstance()
    compareDate.time = this

    return today.get(Calendar.YEAR) == compareDate.get(Calendar.YEAR) &&
            today.get(Calendar.DAY_OF_YEAR) == compareDate.get(Calendar.DAY_OF_YEAR)
}