package com.example.ui.components

import java.text.DecimalFormat
import java.text.DecimalFormatSymbols
import java.util.Locale

object NumberFormatters {

    /**
     * Formats numbers in South Asian / Nepali grouping:
     * e.g., 100000 -> "1,00,000" (1 Lakh)
     * e.g., 15000000 -> "1,50,00,000" (1.5 Crore)
     */
    fun formatNepaliCurrency(amount: Double, includeSymbol: Boolean = true): String {
        if (amount.isNaN() || amount.isInfinite()) {
            return if (includeSymbol) "NPR 0" else "0"
        }

        val isNegative = amount < 0
        val absVal = kotlin.math.abs(amount)
        val intPart = absVal.toLong()
        val decPart = ((absVal - intPart) * 100).toLong()

        val intStr = intPart.toString()
        val formattedInt = if (intStr.length <= 3) {
            intStr
        } else {
            val lastThree = intStr.substring(intStr.length - 3)
            val rest = intStr.substring(0, intStr.length - 3)
            val groups = mutableListOf<String>()
            var i = rest.length
            while (i > 0) {
                val start = (i - 2).coerceAtLeast(0)
                groups.add(0, rest.substring(start, i))
                i -= 2
            }
            groups.joinToString(",") + "," + lastThree
        }

        val result = if (decPart > 0) {
            val decStr = if (decPart < 10) "0$decPart" else "$decPart"
            "$formattedInt.$decStr"
        } else {
            formattedInt
        }

        val sign = if (isNegative) "-" else ""
        return if (includeSymbol) "NPR $sign$result" else "$sign$result"
    }

    fun formatNumber(number: Double, decimals: Int = 2): String {
        if (number.isNaN() || number.isInfinite()) {
            return "0"
        }
        val safeDecimals = decimals.coerceAtLeast(0)
        val pattern = if (safeDecimals > 0) "#,##0.${"#".repeat(safeDecimals)}" else "#,##0"
        val df = DecimalFormat(pattern, DecimalFormatSymbols(Locale.US))
        return df.format(number)
    }

    fun formatPercent(value: Double, decimals: Int = 2): String {
        return "${formatNumber(value, decimals)}%"
    }
}
