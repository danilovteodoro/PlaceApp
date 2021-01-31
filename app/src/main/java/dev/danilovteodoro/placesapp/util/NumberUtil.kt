package dev.danilovteodoro.placesapp.util

import java.text.DecimalFormat
import java.util.*

object NumberUtil {
    fun getCurrency(money:Double):String {
        val numberFormatter = DecimalFormat
                .getCurrencyInstance(Locale("pt","BR"))
        return numberFormatter.format(money)
    }
}