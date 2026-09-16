package com.example.data.service

import kotlin.math.ceil
import kotlin.math.floor
import kotlin.math.pow
import kotlin.math.roundToInt

data class EmiResult(
    val monthlyEmi: Double,
    val totalInterest: Double,
    val totalPayment: Double,
    val principal: Double,
    val amortization: List<AmortizationRow>
)

data class AmortizationRow(
    val period: Int,
    val payment: Double,
    val principalPart: Double,
    val interestPart: Double,
    val balance: Double
)

data class SipResult(
    val totalInvested: Double,
    val estimatedReturns: Double,
    val futureValue: Double
)

data class SalaryResult(
    val grossAnnual: Double,
    val grossMonthly: Double,
    val ssfContributionMonthly: Double,
    val taxableIncomeAnnual: Double,
    val totalTaxAnnual: Double,
    val totalTaxMonthly: Double,
    val netSalaryMonthly: Double
)

data class AttendanceResult(
    val currentPercentage: Double,
    val isEligible: Boolean,
    val classesNeeded: Int,
    val classesCanMiss: Int,
    val is100PercentImpossible: Boolean = false
)

data class BmiResult(
    val bmi: Double,
    val category: String,
    val minHealthyWeightKg: Double,
    val maxHealthyWeightKg: Double
)

data class AgeResult(
    val years: Int,
    val months: Int,
    val days: Int,
    val totalDays: Long,
    val daysToNextBirthday: Int
)

object CalculatorEngine {

    fun calculateEmi(principal: Double, annualRate: Double, tenureMonths: Int): EmiResult {
        val cleanPrincipal = principal.coerceAtLeast(0.0)
        val cleanRate = annualRate.coerceAtLeast(0.0)
        if (cleanPrincipal <= 0.0 || tenureMonths <= 0) {
            return EmiResult(0.0, 0.0, 0.0, cleanPrincipal, emptyList())
        }
        val monthlyRate = (cleanRate / 100.0) / 12.0
        val emi = if (monthlyRate > 0.0) {
            val factor = (1.0 + monthlyRate).pow(tenureMonths.toDouble())
            (cleanPrincipal * monthlyRate * factor) / (factor - 1.0)
        } else {
            cleanPrincipal / tenureMonths.toDouble()
        }

        val totalPayment = emi * tenureMonths
        val totalInterest = (totalPayment - cleanPrincipal).coerceAtLeast(0.0)

        val schedule = mutableListOf<AmortizationRow>()
        var balance = cleanPrincipal
        val sampleStep = if (tenureMonths > 24) tenureMonths / 12 else 1

        for (m in 1..tenureMonths) {
            val interestPart = balance * monthlyRate
            val principalPart = emi - interestPart
            balance = (balance - principalPart).coerceAtLeast(0.0)

            if (m % sampleStep == 0 || m == tenureMonths) {
                schedule.add(
                    AmortizationRow(
                        period = m,
                        payment = emi,
                        principalPart = principalPart,
                        interestPart = interestPart,
                        balance = balance
                    )
                )
            }
        }

        return EmiResult(
            monthlyEmi = emi,
            totalInterest = totalInterest,
            totalPayment = totalPayment,
            principal = cleanPrincipal,
            amortization = schedule
        )
    }

    fun calculateSip(monthlyAmount: Double, annualRate: Double, tenureYears: Int): SipResult {
        val cleanMonthly = monthlyAmount.coerceAtLeast(0.0)
        val cleanRate = annualRate.coerceAtLeast(0.0)
        if (cleanMonthly <= 0.0 || tenureYears <= 0) {
            return SipResult(0.0, 0.0, 0.0)
        }
        val totalMonths = tenureYears * 12
        val i = (cleanRate / 100.0) / 12.0
        val futureValue = if (i > 0.0) {
            cleanMonthly * (((1.0 + i).pow(totalMonths.toDouble()) - 1.0) / i) * (1.0 + i)
        } else {
            cleanMonthly * totalMonths
        }
        val totalInvested = cleanMonthly * totalMonths
        val estimatedReturns = (futureValue - totalInvested).coerceAtLeast(0.0)

        return SipResult(
            totalInvested = totalInvested,
            estimatedReturns = estimatedReturns,
            futureValue = futureValue
        )
    }

    fun calculateSimpleInterest(principal: Double, rate: Double, timeYears: Double): Pair<Double, Double> {
        val p = principal.coerceAtLeast(0.0)
        val r = rate.coerceAtLeast(0.0)
        val t = timeYears.coerceAtLeast(0.0)
        val interest = (p * r * t) / 100.0
        val total = p + interest
        return Pair(interest, total)
    }

    fun calculateCompoundInterest(principal: Double, rate: Double, timeYears: Double, compoundingPerYear: Int = 1): Pair<Double, Double> {
        val p = principal.coerceAtLeast(0.0)
        val r = rate.coerceAtLeast(0.0)
        val t = timeYears.coerceAtLeast(0.0)
        val n = compoundingPerYear.coerceAtLeast(1).toDouble()
        val total = p * (1.0 + (r / 100.0) / n).pow(n * t)
        val interest = (total - p).coerceAtLeast(0.0)
        return Pair(interest, total)
    }

    fun calculateNepalSalaryTax(monthlyGross: Double, isMarried: Boolean, hasSsf: Boolean = true): SalaryResult {
        val cleanGross = monthlyGross.coerceAtLeast(0.0)
        val annualGross = cleanGross * 12.0
        val ssfEmployeeMonthly = if (hasSsf) cleanGross * 0.11 else 0.0
        val annualTaxable = (annualGross - (ssfEmployeeMonthly * 12.0)).coerceAtLeast(0.0)

        // Nepal Progressive tax slabs (Finance Act)
        val slab1Cap = if (isMarried) 600000.0 else 500000.0
        val slab2Cap = if (isMarried) 800000.0 else 700000.0
        val slab3Cap = if (isMarried) 1100000.0 else 1000000.0
        val slab4Cap = 2000000.0

        var remaining = annualTaxable
        var tax = 0.0

        // Slab 1: 1% for general individuals, 0% if SSF registered under Nepal Income Tax Act
        val slab1Rate = if (hasSsf) 0.0 else 0.01
        val s1 = minOf(remaining, slab1Cap)
        tax += s1 * slab1Rate
        remaining = (remaining - s1).coerceAtLeast(0.0)

        // Slab 2: 10%
        if (remaining > 0.0) {
            val s2 = minOf(remaining, slab2Cap - slab1Cap)
            tax += s2 * 0.10
            remaining = (remaining - s2).coerceAtLeast(0.0)
        }

        // Slab 3: 20%
        if (remaining > 0.0) {
            val s3 = minOf(remaining, slab3Cap - slab2Cap)
            tax += s3 * 0.20
            remaining = (remaining - s3).coerceAtLeast(0.0)
        }

        // Slab 4: 30%
        if (remaining > 0.0) {
            val s4 = minOf(remaining, slab4Cap - slab3Cap)
            tax += s4 * 0.30
            remaining = (remaining - s4).coerceAtLeast(0.0)
        }

        // Above 20 Lakhs: 36%
        if (remaining > 0.0) {
            tax += remaining * 0.36
        }

        val monthlyTax = tax / 12.0
        val netMonthly = (cleanGross - ssfEmployeeMonthly - monthlyTax).coerceAtLeast(0.0)

        return SalaryResult(
            grossAnnual = annualGross,
            grossMonthly = cleanGross,
            ssfContributionMonthly = ssfEmployeeMonthly,
            taxableIncomeAnnual = annualTaxable,
            totalTaxAnnual = tax,
            totalTaxMonthly = monthlyTax,
            netSalaryMonthly = netMonthly
        )
    }

    fun calculateGpa(courses: List<Pair<Double, Double>>): Pair<Double, Double> {
        var totalGradePoints = 0.0
        var totalCredits = 0.0
        for ((credits, grade) in courses) {
            val cr = credits.coerceAtLeast(0.0)
            val gr = grade.coerceAtLeast(0.0)
            if (cr > 0.0) {
                totalGradePoints += (cr * gr)
                totalCredits += cr
            }
        }
        val gpa = if (totalCredits > 0.0) (totalGradePoints / totalCredits).coerceIn(0.0, 4.0) else 0.0
        return Pair(gpa, totalCredits)
    }

    fun calculateAttendance(totalClasses: Int, attendedClasses: Int, requiredPercent: Double): AttendanceResult {
        if (totalClasses <= 0) {
            return AttendanceResult(0.0, false, 0, 0)
        }
        val cleanAttended = attendedClasses.coerceIn(0, totalClasses)
        val cleanReq = requiredPercent.coerceIn(0.0, 100.0)

        val current = (cleanAttended.toDouble() / totalClasses.toDouble()) * 100.0
        val isEligible = current >= cleanReq

        var needed = 0
        var canMiss = 0
        var is100Impossible = false

        if (!isEligible) {
            val num = (cleanReq * totalClasses) - (100.0 * cleanAttended)
            val den = 100.0 - cleanReq
            if (den <= 0.0) {
                // If required is 100% and you missed a class, 100% is impossible
                is100Impossible = true
                needed = -1
            } else {
                needed = ceil(num / den).toInt().coerceAtLeast(1)
            }
        } else {
            val num = (100.0 * cleanAttended) - (cleanReq * totalClasses)
            canMiss = if (cleanReq > 0.0) floor(num / cleanReq).toInt().coerceAtLeast(0) else totalClasses
        }

        return AttendanceResult(
            currentPercentage = current,
            isEligible = isEligible,
            classesNeeded = needed,
            classesCanMiss = canMiss,
            is100PercentImpossible = is100Impossible
        )
    }

    fun calculateBmi(heightCm: Double, weightKg: Double): BmiResult {
        if (heightCm <= 0.0 || weightKg <= 0.0) {
            return BmiResult(0.0, "Invalid input", 0.0, 0.0)
        }
        val heightM = heightCm / 100.0
        val bmi = weightKg / (heightM * heightM)

        val category = when {
            bmi < 18.5 -> "Underweight"
            bmi < 25.0 -> "Normal weight"
            bmi < 30.0 -> "Overweight"
            else -> "Obese"
        }

        val minHealthy = 18.5 * (heightM * heightM)
        val maxHealthy = 24.9 * (heightM * heightM)

        return BmiResult(
            bmi = bmi,
            category = category,
            minHealthyWeightKg = minHealthy,
            maxHealthyWeightKg = maxHealthy
        )
    }

    fun calculateAge(birthYear: Int, birthMonth: Int, birthDay: Int, targetYear: Int, targetMonth: Int, targetDay: Int): AgeResult {
        if (birthYear <= 0 || birthMonth !in 1..12 || birthDay !in 1..31 ||
            targetYear <= 0 || targetMonth !in 1..12 || targetDay !in 1..31) {
            return AgeResult(0, 0, 0, 0, 0)
        }

        val utc = java.util.TimeZone.getTimeZone("UTC")
        val birthCal = java.util.Calendar.getInstance(utc).apply {
            clear()
            set(birthYear, birthMonth - 1, birthDay)
        }
        val targetCal = java.util.Calendar.getInstance(utc).apply {
            clear()
            set(targetYear, targetMonth - 1, targetDay)
        }

        if (targetCal.before(birthCal)) {
            return AgeResult(0, 0, 0, 0, 0)
        }

        val totalDays = (targetCal.timeInMillis - birthCal.timeInMillis) / (1000L * 60 * 60 * 24)

        var cur = birthCal.clone() as java.util.Calendar
        var years = 0
        while (true) {
            val nextYear = cur.clone() as java.util.Calendar
            nextYear.add(java.util.Calendar.YEAR, 1)
            if (!nextYear.after(targetCal)) {
                years++
                cur = nextYear
            } else {
                break
            }
        }

        var months = 0
        while (true) {
            val nextMonth = cur.clone() as java.util.Calendar
            nextMonth.add(java.util.Calendar.MONTH, 1)
            if (!nextMonth.after(targetCal)) {
                months++
                cur = nextMonth
            } else {
                break
            }
        }

        val days = ((targetCal.timeInMillis - cur.timeInMillis) / (1000L * 60 * 60 * 24)).toInt()

        val nextBirthdayCal = java.util.Calendar.getInstance(utc).apply {
            clear()
            set(targetYear, birthMonth - 1, birthDay)
        }
        if (nextBirthdayCal.before(targetCal)) {
            nextBirthdayCal.add(java.util.Calendar.YEAR, 1)
        }
        val daysToNext = ((nextBirthdayCal.timeInMillis - targetCal.timeInMillis) / (1000L * 60 * 60 * 24)).toInt()

        return AgeResult(
            years = years,
            months = months,
            days = days,
            totalDays = totalDays,
            daysToNextBirthday = daysToNext
        )
    }

    fun calculateVat(amount: Double, vatPercent: Double = 13.0, isExclusive: Boolean = true): Pair<Double, Double> {
        val cleanAmount = amount.coerceAtLeast(0.0)
        val cleanRate = vatPercent.coerceAtLeast(0.0)
        return if (isExclusive) {
            val vat = cleanAmount * (cleanRate / 100.0)
            val total = cleanAmount + vat
            Pair(vat, total)
        } else {
            val base = cleanAmount / (1.0 + (cleanRate / 100.0))
            val vat = cleanAmount - base
            Pair(vat, base)
        }
    }

    // Nepal Land Units to Sq Feet & Metres
    fun convertNepalLand(
        ropani: Double = 0.0,
        aana: Double = 0.0,
        paisa: Double = 0.0,
        daam: Double = 0.0,
        bigha: Double = 0.0,
        kattha: Double = 0.0,
        dhur: Double = 0.0
    ): Map<String, Double> {
        val hillySqFt = (ropani.coerceAtLeast(0.0) * 5476.0) +
                (aana.coerceAtLeast(0.0) * 342.25) +
                (paisa.coerceAtLeast(0.0) * 85.5625) +
                (daam.coerceAtLeast(0.0) * 21.390625)
        val teraiSqFt = (bigha.coerceAtLeast(0.0) * 72900.0) +
                (kattha.coerceAtLeast(0.0) * 3645.0) +
                (dhur.coerceAtLeast(0.0) * 182.25)
        val totalSqFt = hillySqFt + teraiSqFt

        val sqMeters = totalSqFt * 0.09290304
        val calcRopani = totalSqFt / 5476.0
        val calcAana = totalSqFt / 342.25
        val calcBigha = totalSqFt / 72900.0
        val calcKattha = totalSqFt / 3645.0
        val calcDhur = totalSqFt / 182.25

        return mapOf(
            "sqFeet" to totalSqFt,
            "sqMeters" to sqMeters,
            "ropani" to calcRopani,
            "aana" to calcAana,
            "bigha" to calcBigha,
            "kattha" to calcKattha,
            "dhur" to calcDhur
        )
    }

    fun calculateNepalFuelCost(distanceKm: Double, mileageKmPerLtr: Double, pricePerLtr: Double): Pair<Double, Double> {
        val dist = distanceKm.coerceAtLeast(0.0)
        val mileage = mileageKmPerLtr.coerceAtLeast(0.0)
        val price = pricePerLtr.coerceAtLeast(0.0)
        if (dist <= 0.0 || mileage <= 0.0) return Pair(0.0, 0.0)
        val liters = dist / mileage
        val totalCost = liters * price
        return Pair(liters, totalCost)
    }
}
