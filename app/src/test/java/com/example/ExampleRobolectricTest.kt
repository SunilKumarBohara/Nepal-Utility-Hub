package com.example

import android.content.Context
import androidx.test.core.app.ApplicationProvider
import com.example.data.service.BikramSambatConverter
import com.example.data.service.CalculatorEngine
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner
import org.robolectric.annotation.Config

@RunWith(RobolectricTestRunner::class)
@Config(sdk = [36])
class ExampleRobolectricTest {

  @Test
  fun `read string from context`() {
    val context = ApplicationProvider.getApplicationContext<Context>()
    val appName = context.getString(R.string.app_name)
    assertEquals("Nepal Utility Hub", appName)
  }

  @Test
  fun `test emi calculation`() {
    // 10,00,000 at 12% for 1 year (12 months)
    val result = CalculatorEngine.calculateEmi(1000000.0, 12.0, 12)
    assertTrue(result.monthlyEmi > 88000.0 && result.monthlyEmi < 89000.0)
    assertTrue(result.totalInterest > 0.0)
  }

  @Test
  fun `test bikram sambat converter`() {
    val bs = BikramSambatConverter.convertAdToBs(2024, 4, 13)
    assertEquals(2081, bs.year)
    assertEquals(1, bs.month)
  }

  @Test
  fun `test attendance calculation`() {
    val result = CalculatorEngine.calculateAttendance(50, 42, 75.0)
    assertTrue(result.isEligible)
    assertTrue(result.classesCanMiss > 0)
  }
}

