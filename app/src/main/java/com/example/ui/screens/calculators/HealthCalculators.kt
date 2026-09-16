package com.example.ui.screens.calculators

import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Divider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import com.example.data.service.CalculatorEngine
import com.example.ui.components.NumberFormatters
import com.example.ui.components.ResultActionButtons

@Composable
fun BmiCalculatorView() {
    val defaultHeight = "172"
    val defaultWeight = "68"

    var heightStr by remember { mutableStateOf(defaultHeight) }
    var weightStr by remember { mutableStateOf(defaultWeight) }

    val height = heightStr.toDoubleOrNull() ?: 0.0
    val weight = weightStr.toDoubleOrNull() ?: 0.0

    val res = CalculatorEngine.calculateBmi(height, weight)

    Column(verticalArrangement = Arrangement.spacedBy(16.dp)) {
        Card(
            colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
            shape = RoundedCornerShape(12.dp),
            modifier = Modifier.fillMaxWidth().border(1.dp, MaterialTheme.colorScheme.outline, RoundedCornerShape(12.dp))
        ) {
            Column(modifier = Modifier.padding(16.dp), verticalArrangement = Arrangement.spacedBy(12.dp)) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text("Body Measurements", style = MaterialTheme.typography.titleMedium, fontWeight = FontWeight.Bold, color = MaterialTheme.colorScheme.onSurface)
                    TextButton(onClick = {
                        heightStr = defaultHeight
                        weightStr = defaultWeight
                    }) {
                        Icon(Icons.Default.Refresh, contentDescription = "Reset", modifier = Modifier.padding(end = 4.dp))
                        Text("Reset")
                    }
                }

                OutlinedTextField(
                    value = heightStr,
                    onValueChange = { heightStr = it.filter { ch -> ch.isDigit() || ch == '.' } },
                    label = { Text("Height (in centimeters)") },
                    suffix = { Text("cm") },
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                    modifier = Modifier.fillMaxWidth()
                )

                OutlinedTextField(
                    value = weightStr,
                    onValueChange = { weightStr = it.filter { ch -> ch.isDigit() || ch == '.' } },
                    label = { Text("Weight (in kilograms)") },
                    suffix = { Text("kg") },
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                    modifier = Modifier.fillMaxWidth()
                )
            }
        }

        val categoryColor = when (res.category) {
            "Normal weight" -> Color(0xFF16A34A)
            "Underweight" -> Color(0xFFD97706)
            else -> MaterialTheme.colorScheme.error
        }

        Card(
            colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surfaceVariant),
            shape = RoundedCornerShape(12.dp),
            modifier = Modifier.fillMaxWidth().border(1.dp, MaterialTheme.colorScheme.outline, RoundedCornerShape(12.dp))
        ) {
            Column(modifier = Modifier.padding(16.dp)) {
                Text("Body Mass Index (BMI)", style = MaterialTheme.typography.bodyMedium, color = MaterialTheme.colorScheme.onSurfaceVariant)
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = NumberFormatters.formatNumber(res.bmi, 1),
                        style = MaterialTheme.typography.headlineMedium,
                        fontWeight = FontWeight.Bold,
                        color = categoryColor
                    )
                    Text(
                        text = res.category,
                        style = MaterialTheme.typography.titleMedium,
                        fontWeight = FontWeight.Bold,
                        color = categoryColor
                    )
                }

                Spacer(modifier = Modifier.height(8.dp))
                Divider(color = MaterialTheme.colorScheme.outline.copy(alpha = 0.3f))
                Spacer(modifier = Modifier.height(8.dp))

                Text(
                    "Healthy weight range for ${height.toInt()} cm: ${NumberFormatters.formatNumber(res.minHealthyWeightKg, 1)} kg - ${NumberFormatters.formatNumber(res.maxHealthyWeightKg, 1)} kg",
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )

                val summary = "BMI: ${NumberFormatters.formatNumber(res.bmi, 1)} (${res.category}). Ideal range: ${NumberFormatters.formatNumber(res.minHealthyWeightKg, 1)}-${NumberFormatters.formatNumber(res.maxHealthyWeightKg, 1)} kg."
                ResultActionButtons(toolTitle = "BMI Calculator", resultSummary = summary)
            }
        }
    }
}

@Composable
fun AgeCalculatorView() {
    val defaultYear = "1998"
    val defaultMonth = "5"
    val defaultDay = "15"

    var birthYearStr by remember { mutableStateOf(defaultYear) }
    var birthMonthStr by remember { mutableStateOf(defaultMonth) }
    var birthDayStr by remember { mutableStateOf(defaultDay) }

    val todayCal = java.util.Calendar.getInstance()
    val curYear = todayCal.get(java.util.Calendar.YEAR)
    val curMonth = todayCal.get(java.util.Calendar.MONTH) + 1
    val curDay = todayCal.get(java.util.Calendar.DAY_OF_MONTH)

    val bYear = (birthYearStr.toIntOrNull() ?: 1998).coerceIn(1900, curYear)
    val bMonth = (birthMonthStr.toIntOrNull() ?: 1).coerceIn(1, 12)
    val bDay = (birthDayStr.toIntOrNull() ?: 1).coerceIn(1, 31)

    val res = CalculatorEngine.calculateAge(bYear, bMonth, bDay, curYear, curMonth, curDay)

    Column(verticalArrangement = Arrangement.spacedBy(16.dp)) {
        Card(
            colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
            shape = RoundedCornerShape(12.dp),
            modifier = Modifier.fillMaxWidth().border(1.dp, MaterialTheme.colorScheme.outline, RoundedCornerShape(12.dp))
        ) {
            Column(modifier = Modifier.padding(16.dp), verticalArrangement = Arrangement.spacedBy(12.dp)) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text("Date of Birth (AD)", style = MaterialTheme.typography.titleMedium, fontWeight = FontWeight.Bold, color = MaterialTheme.colorScheme.onSurface)
                    TextButton(onClick = {
                        birthYearStr = defaultYear
                        birthMonthStr = defaultMonth
                        birthDayStr = defaultDay
                    }) {
                        Icon(Icons.Default.Refresh, contentDescription = "Reset", modifier = Modifier.padding(end = 4.dp))
                        Text("Reset")
                    }
                }

                Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                    OutlinedTextField(
                        value = birthYearStr,
                        onValueChange = { birthYearStr = it.filter { ch -> ch.isDigit() } },
                        label = { Text("Year") },
                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                        modifier = Modifier.weight(1.2f)
                    )
                    OutlinedTextField(
                        value = birthMonthStr,
                        onValueChange = { birthMonthStr = it.filter { ch -> ch.isDigit() } },
                        label = { Text("Month (1-12)") },
                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                        modifier = Modifier.weight(1f)
                    )
                    OutlinedTextField(
                        value = birthDayStr,
                        onValueChange = { birthDayStr = it.filter { ch -> ch.isDigit() } },
                        label = { Text("Day (1-31)") },
                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                        modifier = Modifier.weight(1f)
                    )
                }
            }
        }

        Card(
            colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.primaryContainer),
            shape = RoundedCornerShape(12.dp),
            modifier = Modifier.fillMaxWidth().border(1.dp, MaterialTheme.colorScheme.primary.copy(alpha = 0.3f), RoundedCornerShape(12.dp))
        ) {
            Column(modifier = Modifier.padding(16.dp)) {
                Text("Exact Age Today", style = MaterialTheme.typography.bodyMedium, color = MaterialTheme.colorScheme.onPrimaryContainer)
                Text(
                    text = "${res.years} Years, ${res.months} Months, ${res.days} Days",
                    style = MaterialTheme.typography.headlineSmall,
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colorScheme.onPrimaryContainer
                )

                Spacer(modifier = Modifier.height(12.dp))
                Divider(color = MaterialTheme.colorScheme.onPrimaryContainer.copy(alpha = 0.2f))
                Spacer(modifier = Modifier.height(12.dp))

                Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
                    Column {
                        Text("Total Days Lived", style = MaterialTheme.typography.labelMedium, color = MaterialTheme.colorScheme.onPrimaryContainer.copy(alpha = 0.8f))
                        Text("${res.totalDays} Days", style = MaterialTheme.typography.titleMedium, fontWeight = FontWeight.SemiBold, color = MaterialTheme.colorScheme.onPrimaryContainer)
                    }
                    Column(horizontalAlignment = Alignment.End) {
                        Text("Next Birthday In", style = MaterialTheme.typography.labelMedium, color = MaterialTheme.colorScheme.onPrimaryContainer.copy(alpha = 0.8f))
                        Text("${res.daysToNextBirthday} Days", style = MaterialTheme.typography.titleMedium, fontWeight = FontWeight.SemiBold, color = MaterialTheme.colorScheme.secondary)
                    }
                }

                val summary = "Age: ${res.years}y ${res.months}m ${res.days}d (${res.totalDays} total days). Next birthday in ${res.daysToNextBirthday} days."
                ResultActionButtons(toolTitle = "Age Calculator", resultSummary = summary)
            }
        }
    }
}
