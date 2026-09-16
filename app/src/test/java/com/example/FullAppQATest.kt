package com.example

import android.app.Application
import androidx.test.core.app.ApplicationProvider
import com.example.data.model.ToolCatalog
import com.example.data.service.BikramSambatConverter
import com.example.data.service.CalculatorEngine
import com.example.ui.MainTab
import com.example.ui.MainViewModel
import com.example.ui.components.NumberFormatters
import com.example.ui.theme.ThemeMode
import org.junit.Assert.assertEquals
import org.junit.Assert.assertFalse
import org.junit.Assert.assertNull
import org.junit.Assert.assertTrue
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner
import org.robolectric.annotation.Config
import kotlin.math.abs

@RunWith(RobolectricTestRunner::class)
@Config(sdk = [36])
class FullAppQATest {

    private val application: Application
        get() = ApplicationProvider.getApplicationContext()

    // -------------------------------------------------------------
    // SECTION 1: EMI & LOAN CALCULATIONS
    // -------------------------------------------------------------
    @Test
    fun testEmiStandardLoan() {
        // Principal: 10,00,000, 12% annual, 12 months
        // Expected EMI: ~88,848.79
        val res = CalculatorEngine.calculateEmi(1000000.0, 12.0, 12)
        assertTrue("EMI should be around 88,849", abs(res.monthlyEmi - 88848.79) < 2.0)
        assertTrue("Total interest must be positive", res.totalInterest > 0.0)
        assertEquals("Total payment equals EMI * tenure", res.monthlyEmi * 12, res.totalPayment, 0.01)
        assertEquals("Amortization must be computed", 12, res.amortization.size)
    }

    @Test
    fun testEmiZeroRateLoan() {
        // Zero percent interest loan
        val res = CalculatorEngine.calculateEmi(120000.0, 0.0, 12)
        assertEquals(10000.0, res.monthlyEmi, 0.01)
        assertEquals(0.0, res.totalInterest, 0.01)
        assertEquals(120000.0, res.totalPayment, 0.01)
    }

    @Test
    fun testEmiEdgeCases() {
        // Zero principal or zero months
        val zeroP = CalculatorEngine.calculateEmi(0.0, 10.0, 12)
        assertEquals(0.0, zeroP.monthlyEmi, 0.0)

        val zeroTenure = CalculatorEngine.calculateEmi(100000.0, 10.0, 0)
        assertEquals(0.0, zeroTenure.monthlyEmi, 0.0)

        // Negative principal coerced gracefully
        val negP = CalculatorEngine.calculateEmi(-50000.0, 10.0, 12)
        assertEquals(0.0, negP.monthlyEmi, 0.0)
    }

    // -------------------------------------------------------------
    // SECTION 2: SIP & INVESTMENT CALCULATIONS
    // -------------------------------------------------------------
    @Test
    fun testSipStandardInvestment() {
        // Monthly 5000, 12% return, 10 years (120 months)
        val res = CalculatorEngine.calculateSip(5000.0, 12.0, 10)
        val expectedInvested = 5000.0 * 120
        assertEquals(expectedInvested, res.totalInvested, 0.01)
        assertTrue("Returns should be greater than invested", res.estimatedReturns > 0)
        assertEquals(res.totalInvested + res.estimatedReturns, res.futureValue, 0.01)
    }

    @Test
    fun testSipZeroRate() {
        val res = CalculatorEngine.calculateSip(2000.0, 0.0, 5)
        assertEquals(120000.0, res.totalInvested, 0.01)
        assertEquals(120000.0, res.futureValue, 0.01)
        assertEquals(0.0, res.estimatedReturns, 0.01)
    }

    // -------------------------------------------------------------
    // SECTION 3: SIMPLE & COMPOUND INTEREST
    // -------------------------------------------------------------
    @Test
    fun testInterestCalculations() {
        // SI: 100,000 at 10% for 2 years -> 20,000 interest, 120,000 total
        val (siInterest, siTotal) = CalculatorEngine.calculateSimpleInterest(100000.0, 10.0, 2.0)
        assertEquals(20000.0, siInterest, 0.01)
        assertEquals(120000.0, siTotal, 0.01)

        // CI: 100,000 at 10% annual for 2 years annual compounding -> 21,000 interest
        val (ciInterest, ciTotal) = CalculatorEngine.calculateCompoundInterest(100000.0, 10.0, 2.0, 1)
        assertEquals(21000.0, ciInterest, 0.01)
        assertEquals(121000.0, ciTotal, 0.01)
    }

    // -------------------------------------------------------------
    // SECTION 4: NEPAL SALARY TAX (IRD COMPLIANCE)
    // -------------------------------------------------------------
    @Test
    fun testNepalSalaryTaxUnmarriedWithSsf() {
        // 75,000 / month gross = 900,000 annual
        // SSF employee: 11% of 75,000 = 8,250/mo -> 99,000 annual
        // Taxable: 801,000
        // Slab 1: 500,000 @ 0% (SSF exempt)
        // Slab 2: 200,000 @ 10% = 20,000
        // Slab 3: 101,000 @ 20% = 20,200
        // Total annual tax: 40,200 -> 3,350/mo
        val res = CalculatorEngine.calculateNepalSalaryTax(75000.0, isMarried = false, hasSsf = true)
        assertEquals(900000.0, res.grossAnnual, 0.01)
        assertEquals(8250.0, res.ssfContributionMonthly, 0.01)
        assertEquals(801000.0, res.taxableIncomeAnnual, 0.01)
        assertEquals(40200.0, res.totalTaxAnnual, 0.01)
        assertEquals(3350.0, res.totalTaxMonthly, 0.01)
        assertEquals(75000.0 - 8250.0 - 3350.0, res.netSalaryMonthly, 0.01)
    }

    @Test
    fun testNepalSalaryTaxUnmarriedWithoutSsf() {
        // Without SSF: 1% tax on first 500k slab
        // Gross 500k annual = 41,666.67/mo
        val res = CalculatorEngine.calculateNepalSalaryTax(500000.0 / 12.0, isMarried = false, hasSsf = false)
        assertEquals(5000.0, res.totalTaxAnnual, 0.5) // 1% of 500,000
    }

    // -------------------------------------------------------------
    // SECTION 5: GPA & ACADEMIC ATTENDANCE
    // -------------------------------------------------------------
    @Test
    fun testGpaCalculation() {
        val courses = listOf(
            Pair(3.0, 4.0), // 12
            Pair(3.0, 3.6), // 10.8
            Pair(4.0, 3.2)  // 12.8
        )
        // Total points: 35.6 / 10 credits = 3.56
        val (gpa, credits) = CalculatorEngine.calculateGpa(courses)
        assertEquals(10.0, credits, 0.01)
        assertEquals(3.56, gpa, 0.01)

        // Empty courses check
        val (zeroGpa, zeroCr) = CalculatorEngine.calculateGpa(emptyList())
        assertEquals(0.0, zeroGpa, 0.0)
        assertEquals(0.0, zeroCr, 0.0)
    }

    @Test
    fun testAttendanceStandardAndEdges() {
        // 48 total, 38 attended, 75% required
        // Current: 38/48 = 79.17% -> eligible
        val el = CalculatorEngine.calculateAttendance(48, 38, 75.0)
        assertTrue(el.isEligible)
        assertEquals(2, el.classesCanMiss)

        // Shortfall: 50 total, 30 attended (60%), 75% req
        val sh = CalculatorEngine.calculateAttendance(50, 30, 75.0)
        assertFalse(sh.isEligible)
        assertEquals(30, sh.classesNeeded) // (75*50 - 100*30)/(100-75) = (3750-3000)/25 = 750/25 = 30

        // Impossible 100% attendance test
        val imp = CalculatorEngine.calculateAttendance(10, 9, 100.0)
        assertFalse(imp.isEligible)
        assertTrue(imp.is100PercentImpossible)
    }

    // -------------------------------------------------------------
    // SECTION 6: BMI & AGE CALCULATIONS
    // -------------------------------------------------------------
    @Test
    fun testBmiCalculation() {
        // Height 172cm (1.72m), weight 68kg -> 68 / (1.72^2) = 22.986
        val res = CalculatorEngine.calculateBmi(172.0, 68.0)
        assertEquals("Normal weight", res.category)
        assertTrue(abs(res.bmi - 22.99) < 0.1)

        // Zero / negative edge cases
        val zero = CalculatorEngine.calculateBmi(0.0, 50.0)
        assertEquals(0.0, zero.bmi, 0.0)
    }

    @Test
    fun testAgeCalculation() {
        // Born: 2000-01-15, Target: 2024-09-17
        val res = CalculatorEngine.calculateAge(2000, 1, 15, 2024, 9, 17)
        assertEquals(24, res.years)
        assertEquals(8, res.months)
        assertEquals(2, res.days)
        assertTrue("Total days must be > 8000", res.totalDays > 8000)

        // Target before birth
        val future = CalculatorEngine.calculateAge(2025, 1, 1, 2024, 1, 1)
        assertEquals(0, future.years)
        assertEquals(0, future.totalDays)
    }

    // -------------------------------------------------------------
    // SECTION 7: VAT & DISCOUNT
    // -------------------------------------------------------------
    @Test
    fun testVatCalculation() {
        // Exclusive: 10,000 + 13% = 11,300
        val (vatEx, totalEx) = CalculatorEngine.calculateVat(10000.0, 13.0, isExclusive = true)
        assertEquals(1300.0, vatEx, 0.01)
        assertEquals(11300.0, totalEx, 0.01)

        // Inclusive: 11,300 extracted VAT = 1,300, base = 10,000
        val (vatInc, baseInc) = CalculatorEngine.calculateVat(11300.0, 13.0, isExclusive = false)
        assertEquals(1300.0, vatInc, 0.01)
        assertEquals(10000.0, baseInc, 0.01)
    }

    // -------------------------------------------------------------
    // SECTION 8: NEPAL LAND AREA & FUEL
    // -------------------------------------------------------------
    @Test
    fun testNepalLandConversion() {
        // 1 Ropani = 16 Aana = 5476 Sq. Ft
        val r1 = CalculatorEngine.convertNepalLand(ropani = 1.0)
        assertEquals(5476.0, r1["sqFeet"] ?: 0.0, 0.01)
        assertEquals(16.0, r1["aana"] ?: 0.0, 0.01)

        // 1 Bigha = 20 Kattha = 72900 Sq. Ft
        val b1 = CalculatorEngine.convertNepalLand(bigha = 1.0)
        assertEquals(72900.0, b1["sqFeet"] ?: 0.0, 0.01)
        assertEquals(20.0, b1["kattha"] ?: 0.0, 0.01)
    }

    @Test
    fun testNepalFuelCost() {
        // 200 km, 15 km/L, NPR 170/L
        // Liters = 200 / 15 = 13.3333 L
        // Cost = 13.3333 * 170 = 2266.67 NPR
        val (liters, cost) = CalculatorEngine.calculateNepalFuelCost(200.0, 15.0, 170.0)
        assertTrue(abs(liters - 13.33) < 0.1)
        assertTrue(abs(cost - 2266.67) < 1.0)
    }

    // -------------------------------------------------------------
    // SECTION 9: BIKRAM SAMBAT DATE CONVERTER
    // -------------------------------------------------------------
    @Test
    fun testBikramSambatConversions() {
        // Nepali New Year 2081: 2024-04-13 AD -> 2081-01-01 BS
        val bs = BikramSambatConverter.convertAdToBs(2024, 4, 13)
        assertEquals(2081, bs.year)
        assertEquals(1, bs.month)
        assertEquals(1, bs.day)
        assertEquals("बैशाख", bs.monthNameNp)

        // Round trip test: 2081-01-01 BS -> 2024-04-13 AD
        val (adY, adM, adD) = BikramSambatConverter.convertBsToAd(2081, 1, 1)
        assertEquals(2024, adY)
        assertEquals(4, adM)
        assertEquals(13, adD)
    }

    // -------------------------------------------------------------
    // SECTION 10: NUMBER FORMATTERS
    // -------------------------------------------------------------
    @Test
    fun testNepaliNumberFormatting() {
        // South Asian grouping: 1 Lakh = 1,00,000
        val lakh = NumberFormatters.formatNepaliCurrency(100000.0)
        assertEquals("NPR 1,00,000", lakh)

        // 1.5 Crore = 1,50,00,000
        val crore = NumberFormatters.formatNepaliCurrency(15000000.0)
        assertEquals("NPR 1,50,00,000", crore)

        // Negative value
        val neg = NumberFormatters.formatNepaliCurrency(-5000.0)
        assertEquals("NPR -5,000", neg)

        // NaN & Infinite safety
        assertEquals("NPR 0", NumberFormatters.formatNepaliCurrency(Double.NaN))
        assertEquals("0", NumberFormatters.formatNumber(Double.POSITIVE_INFINITY))
    }

    // -------------------------------------------------------------
    // SECTION 11: MAIN VIEWMODEL STATE & NAVIGATION
    // -------------------------------------------------------------
    @Test
    fun testMainViewModelStateManagement() {
        val vm = MainViewModel(application)
        assertEquals(MainTab.HOME, vm.currentTab.value)

        // Tab switching
        vm.setTab(MainTab.TOOLS)
        assertEquals(MainTab.TOOLS, vm.currentTab.value)
        assertNull(vm.selectedTool.value)

        // Tool selection
        val testTool = ToolCatalog.allTools.first()
        vm.selectTool(testTool)
        assertEquals(testTool, vm.selectedTool.value)

        // Clear tool selection
        vm.clearSelectedTool()
        assertNull(vm.selectedTool.value)

        // Theme toggle
        vm.setThemeMode(ThemeMode.DARK)
        assertEquals(ThemeMode.DARK, vm.themeMode.value)
        vm.setThemeMode(ThemeMode.LIGHT)
        assertEquals(ThemeMode.LIGHT, vm.themeMode.value)
        vm.setThemeMode(ThemeMode.SYSTEM)
        assertEquals(ThemeMode.SYSTEM, vm.themeMode.value)
    }
}
