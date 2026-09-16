package com.example.data.service

import java.util.Calendar
import java.util.TimeZone

data class BsDate(
    val year: Int,
    val month: Int, // 1-12 (1 = Baisakh)
    val day: Int,
    val monthNameNp: String,
    val monthNameEn: String,
    val dayOfWeekNp: String,
    val dayOfWeekEn: String
)

object BikramSambatConverter {

    val nepaliMonthNames = listOf(
        "बैशाख" to "Baisakh",
        "जेठ" to "Jestha",
        "असार" to "Ashadh",
        "श्रावण" to "Shrawan",
        "भदौ" to "Bhadra",
        "असोज" to "Ashwin",
        "कार्तिक" to "Kartik",
        "मंसिर" to "Mangsir",
        "पौष" to "Poush",
        "माघ" to "Magh",
        "फागुन" to "Falgun",
        "चैत" to "Chaitra"
    )

    val nepaliDayNames = listOf(
        "आइतबार" to "Sunday",
        "सोमबार" to "Monday",
        "मंगलबार" to "Tuesday",
        "बुधबार" to "Wednesday",
        "बिहीबार" to "Thursday",
        "शुक्रबार" to "Friday",
        "शनिबार" to "Saturday"
    )

    // Number of days in each month for BS years 2075 to 2085
    private val bsMonthDays: Map<Int, List<Int>> = mapOf(
        2075 to listOf(31, 31, 31, 32, 31, 31, 30, 29, 30, 29, 30, 30),
        2076 to listOf(31, 31, 32, 31, 31, 31, 30, 29, 30, 29, 30, 30),
        2077 to listOf(31, 32, 31, 32, 31, 30, 30, 30, 29, 29, 30, 30),
        2078 to listOf(31, 31, 31, 32, 31, 31, 30, 29, 30, 29, 30, 30),
        2079 to listOf(31, 31, 32, 31, 31, 31, 30, 29, 30, 29, 30, 30),
        2080 to listOf(31, 31, 31, 32, 31, 31, 30, 29, 30, 29, 30, 30),
        2081 to listOf(31, 31, 32, 31, 31, 30, 30, 30, 29, 30, 29, 31),
        2082 to listOf(31, 32, 31, 32, 31, 30, 30, 30, 29, 30, 30, 30),
        2083 to listOf(31, 31, 31, 32, 31, 31, 30, 29, 30, 29, 30, 30),
        2084 to listOf(31, 31, 32, 31, 31, 30, 30, 30, 29, 30, 30, 30),
        2085 to listOf(31, 32, 31, 32, 31, 30, 30, 30, 29, 30, 29, 31)
    )

    // Reference point: 2080-01-01 BS = 2023-04-14 AD (Friday)
    private const val refBsYear = 2080
    private const val refBsMonth = 1
    private const val refBsDay = 1
    private const val refAdYear = 2023
    private const val refAdMonth = 4 // April
    private const val refAdDay = 14

    private val utcZone: TimeZone = TimeZone.getTimeZone("UTC")

    fun convertAdToBs(adYear: Int, adMonth: Int, adDay: Int): BsDate {
        val refCal = Calendar.getInstance(utcZone).apply {
            clear()
            set(refAdYear, refAdMonth - 1, refAdDay)
        }
        val targetCal = Calendar.getInstance(utcZone).apply {
            clear()
            set(adYear, adMonth - 1, adDay)
        }

        val diffMillis = targetCal.timeInMillis - refCal.timeInMillis
        var diffDays = (diffMillis / (1000L * 60 * 60 * 24)).toInt()

        var curYear = refBsYear
        var curMonth = refBsMonth
        var curDay = refBsDay

        if (diffDays >= 0) {
            while (diffDays > 0) {
                val daysInMonth = getDaysInBsMonth(curYear, curMonth)
                val remainingInMonth = daysInMonth - curDay + 1
                if (diffDays >= remainingInMonth) {
                    diffDays -= remainingInMonth
                    curDay = 1
                    curMonth++
                    if (curMonth > 12) {
                        curMonth = 1
                        curYear++
                    }
                } else {
                    curDay += diffDays
                    diffDays = 0
                }
            }
        } else {
            while (diffDays < 0) {
                if (curDay > 1) {
                    val back = minOf(-diffDays, curDay - 1)
                    curDay -= back
                    diffDays += back
                } else {
                    curMonth--
                    if (curMonth < 1) {
                        curMonth = 12
                        curYear--
                    }
                    val daysInPrevMonth = getDaysInBsMonth(curYear, curMonth)
                    curDay = daysInPrevMonth
                    diffDays++
                }
            }
        }

        val dayOfWeek = targetCal.get(Calendar.DAY_OF_WEEK) // 1 = Sunday, 7 = Saturday
        val (dayNp, dayEn) = nepaliDayNames[(dayOfWeek - 1).coerceIn(0, 6)]
        val (monthNp, monthEn) = nepaliMonthNames[(curMonth - 1).coerceIn(0, 11)]

        return BsDate(
            year = curYear,
            month = curMonth,
            day = curDay,
            monthNameNp = monthNp,
            monthNameEn = monthEn,
            dayOfWeekNp = dayNp,
            dayOfWeekEn = dayEn
        )
    }

    fun convertBsToAd(bsYear: Int, bsMonth: Int, bsDay: Int): Triple<Int, Int, Int> {
        var totalDays = 0
        if (bsYear >= refBsYear) {
            for (y in refBsYear until bsYear) {
                val months = bsMonthDays[y] ?: List(12) { 30 }
                totalDays += months.sum()
            }
            for (m in 1 until bsMonth) {
                totalDays += getDaysInBsMonth(bsYear, m)
            }
            totalDays += (bsDay - refBsDay)
        } else {
            for (y in bsYear until refBsYear) {
                val months = bsMonthDays[y] ?: List(12) { 30 }
                totalDays -= months.sum()
            }
            for (m in 1 until bsMonth) {
                totalDays += getDaysInBsMonth(bsYear, m)
            }
            totalDays += (bsDay - 1)
        }

        val cal = Calendar.getInstance(utcZone).apply {
            clear()
            set(refAdYear, refAdMonth - 1, refAdDay)
            add(Calendar.DAY_OF_YEAR, totalDays)
        }
        return Triple(cal.get(Calendar.YEAR), cal.get(Calendar.MONTH) + 1, cal.get(Calendar.DAY_OF_MONTH))
    }

    fun getDaysInBsMonth(year: Int, month: Int): Int {
        val list = bsMonthDays[year] ?: listOf(31, 31, 31, 32, 31, 31, 30, 29, 30, 29, 30, 30)
        val idx = (month - 1).coerceIn(0, 11)
        return list[idx]
    }

    fun getCurrentBsDate(): BsDate {
        val cal = Calendar.getInstance(TimeZone.getTimeZone("Asia/Kathmandu"))
        return convertAdToBs(
            cal.get(Calendar.YEAR),
            cal.get(Calendar.MONTH) + 1,
            cal.get(Calendar.DAY_OF_MONTH)
        )
    }
}
