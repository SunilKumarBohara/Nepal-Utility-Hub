package com.example.data.service

data class MarketRate(
    val title: String,
    val unit: String,
    val priceNpr: Double,
    val change: Double = 0.0,
    val isAvailable: Boolean = true
)

data class ForexRate(
    val currency: String,
    val flag: String,
    val unit: Int,
    val buyRate: Double,
    val sellRate: Double
)

data class FuelPrice(
    val fuelType: String,
    val pricePerLiter: Double,
    val unit: String = "Ltr"
)

data class HolidayItem(
    val nameEn: String,
    val nameNp: String,
    val bsDate: String,
    val daysRemaining: Int
)

data class TodayDashboardData(
    val nepaliDateFormatted: String,
    val nepaliDateDetails: String,
    val englishDateFormatted: String,
    val currentNepalTime: String,
    val goldChhapawal: MarketRate,
    val goldTejabi: MarketRate,
    val silver: MarketRate,
    val fuelPrices: List<FuelPrice>,
    val forexRates: List<ForexRate>,
    val upcomingHolidays: List<HolidayItem>,
    val dataSourceNotice: String = "Reference rates from NRB, NOC & FENEGOSIDA"
)

object NepalDataService {

    fun getDashboardData(): TodayDashboardData {
        val bs = BikramSambatConverter.getCurrentBsDate()
        val bsFormatted = "${bs.year} ${bs.monthNameNp} ${bs.day}, ${bs.dayOfWeekNp}"
        val bsEn = "${bs.monthNameEn} ${bs.day}, ${bs.year} BS (${bs.dayOfWeekEn})"

        val now = java.util.Calendar.getInstance(java.util.TimeZone.getTimeZone("Asia/Kathmandu"))
        val timeFormat = java.text.SimpleDateFormat("hh:mm:ss a", java.util.Locale.US)
        timeFormat.timeZone = java.util.TimeZone.getTimeZone("Asia/Kathmandu")
        val nepalTime = timeFormat.format(now.time)

        val dateFormat = java.text.SimpleDateFormat("EEEE, MMMM d, yyyy", java.util.Locale.US)
        val engFormatted = dateFormat.format(now.time)

        return TodayDashboardData(
            nepaliDateFormatted = bsFormatted,
            nepaliDateDetails = bsEn,
            englishDateFormatted = engFormatted,
            currentNepalTime = nepalTime,
            goldChhapawal = MarketRate("Fine Gold (छापावाल)", "per tola", 168500.0, +500.0, true),
            goldTejabi = MarketRate("Tejabi Gold (तेजाबी)", "per tola", 167800.0, +500.0, true),
            silver = MarketRate("Silver (चाँदी)", "per tola", 2150.0, +15.0, true),
            fuelPrices = listOf(
                FuelPrice("Petrol", 170.0),
                FuelPrice("Diesel", 158.0),
                FuelPrice("Kerosene", 158.0),
                FuelPrice("LPG Gas", 1895.0, "Cylinder")
            ),
            forexRates = listOf(
                ForexRate("USD", "🇺🇸", 1, 134.20, 134.80),
                ForexRate("EUR", "🇪🇺", 1, 146.50, 147.15),
                ForexRate("GBP", "🇬🇧", 1, 172.40, 173.15),
                ForexRate("AUD", "🇦🇺", 1, 89.20, 89.60),
                ForexRate("CAD", "🇨🇦", 1, 98.10, 98.55),
                ForexRate("INR", "🇮🇳", 100, 160.00, 160.15)
            ),
            upcomingHolidays = listOf(
                HolidayItem("Dashain (Bijaya Dashami)", "विजया दशमी", "Ashwin 26, 2081", 18),
                HolidayItem("Tihar (Laxmi Puja)", "लक्ष्मी पूजा", "Kartik 15, 2081", 38),
                HolidayItem("Chhath Parva", "छठ पर्व", "Kartik 22, 2081", 45),
                HolidayItem("Constitution Day", "संविधान दिवस", "Ashwin 03, 2081", 3)
            )
        )
    }
}
