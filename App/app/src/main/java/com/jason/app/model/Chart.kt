package com.jason.app.model

import com.jason.app.extensions.convertStringToMillis
import com.jason.app.extensions.dp
import com.jason.app.extensions.formatPriceWithoutCurrency
import com.jason.app.extensions.timeFormat
import kotlin.math.max

class Chart constructor(
    val time: String,
    val moneyIn: Double = 0.0,
    val moneyOut: Double = 0.0,
){
    var isFocused: Boolean = false

    fun inHeight(maxValue: Double): Int {
        return Calculator(maxValue).math(moneyIn)
    }

    fun outHeight(maxValue: Double): Int {
        return Calculator(maxValue).math(moneyOut)
    }

    fun getMaxValue(): Double {
        return max(moneyIn, moneyOut)
    }

    fun inDisplayValue(): String {
        return moneyIn.formatPriceWithoutCurrency().plus(" SGD")
    }

    fun outDisplayValue(): String {
        return moneyOut.formatPriceWithoutCurrency().plus(" SGD")
    }
    fun timeDisplayValue(format: String): String {
        return time.convertStringToMillis(format = "yyyy-MM-dd").timeFormat(format)
    }

    class Calculator(maxValue: Double) {
        private val MAX_HEIGHT = 176.dp
        private val ratio: Double = MAX_HEIGHT.toDouble() / maxValue
        fun math(value: Double): Int {
            return (value * ratio).toInt()
        }
    }
}