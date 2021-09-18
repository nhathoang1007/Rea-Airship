package com.jason.app.extensions

import java.text.NumberFormat
import java.util.*
import kotlin.math.pow


/**
 * Created by Nhat.vo on 8/16/20.
 */

fun String?.getDefault(): String {
    return this ?: ""
}

fun String?.getDefaultDisplayValue(): String {
    return if (this.isNullOrEmpty()) {
        "-"
    } else {
        this
    }
}

fun Int?.getDefault(): Int {
    return this ?: 0
}

fun Int?.getDisplay(): String {
    return this?.toString() ?: "-"
}

fun Double?.getDefault(): Double {
    return this ?: 0.0
}

fun Long?.getDefault(): Long {
    return this ?: 0
}

fun Boolean?.getDefault(): Boolean {
    return this ?: false
}

fun Any?.formatPriceWithoutCurrency(): String {
    if (this != null) {
        kotlin.runCatching {
            val numberFormat = NumberFormat.getCurrencyInstance(Locale.US)
            numberFormat.maximumFractionDigits = 2
            when (this) {
                is Int -> this.toDouble()
                is Float -> this.toDouble()
                is Double -> this.toDouble()
                is String -> this.toDouble()
                else -> 0.0
            }.apply {
                val parse = withSuffix()
                val format = numberFormat.format(parse.first).replace("$", "")
                return "$format${parse.second}"
            }
        }
    }
    return "-"
}


fun Double.withSuffix(): Pair<Double, String> {
    val mill = 10.0.pow(6)
    val bill = 10.0.pow(9)

    return when {
        this / bill > 1 -> {
            Pair(this / bill, "B")
        }
        this / mill > 1 -> {
            Pair(this / mill, "M")
        }
        else -> {
            Pair(this, "")
        }
    }
}