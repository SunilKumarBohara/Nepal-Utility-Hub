package com.example.ui.screens.calculators

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ExpandLess
import androidx.compose.material.icons.filled.ExpandMore
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Divider
import androidx.compose.material3.FilterChip
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Switch
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
fun EmiCalculatorView(onRecordResult: (String) -> Unit = {}) {
    val defaultPrincipal = "1500000"
    val defaultRate = "10.5"
    val defaultTenure = "5"

    var principalStr by remember { mutableStateOf(defaultPrincipal) }
    var rateStr by remember { mutableStateOf(defaultRate) }
    var tenureStr by remember { mutableStateOf(defaultTenure) }
    var isTenureYears by remember { mutableStateOf(true) }
    var showAmortization by remember { mutableStateOf(false) }

    val principal = principalStr.toDoubleOrNull() ?: 0.0
    val rate = rateStr.toDoubleOrNull() ?: 0.0
    val tenureNum = tenureStr.toIntOrNull() ?: 0
    val tenureMonths = if (isTenureYears) tenureNum * 12 else tenureNum

    val result = CalculatorEngine.calculateEmi(principal, rate, tenureMonths)

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
                    Text("Loan Details", style = MaterialTheme.typography.titleMedium, fontWeight = FontWeight.Bold, color = MaterialTheme.colorScheme.onSurface)
                    TextButton(
                        onClick = {
                            principalStr = defaultPrincipal
                            rateStr = defaultRate
                            tenureStr = defaultTenure
                            isTenureYears = true
                        }
                    ) {
                        Icon(Icons.Default.Refresh, contentDescription = "Reset", modifier = Modifier.padding(end = 4.dp))
                        Text("Reset")
                    }
                }

                OutlinedTextField(
                    value = principalStr,
                    onValueChange = { principalStr = it.filter { ch -> ch.isDigit() || ch == '.' } },
                    label = { Text("Loan Amount (Principal)") },
                    prefix = { Text("NPR ") },
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                    modifier = Modifier.fillMaxWidth()
                )

                OutlinedTextField(
                    value = rateStr,
                    onValueChange = { rateStr = it.filter { ch -> ch.isDigit() || ch == '.' } },
                    label = { Text("Annual Interest Rate") },
                    suffix = { Text("%") },
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Decimal),
                    modifier = Modifier.fillMaxWidth()
                )

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(8.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    OutlinedTextField(
                        value = tenureStr,
                        onValueChange = { tenureStr = it.filter { ch -> ch.isDigit() } },
                        label = { Text("Loan Tenure") },
                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                        modifier = Modifier.weight(1f),
                        singleLine = true
                    )
                    FilterChip(
                        selected = isTenureYears,
                        onClick = { isTenureYears = true },
                        label = { Text("Years") }
                    )
                    FilterChip(
                        selected = !isTenureYears,
                        onClick = { isTenureYears = false },
                        label = { Text("Months") }
                    )
                }
            }
        }

        // Result Card
        Card(
            colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.primaryContainer),
            shape = RoundedCornerShape(12.dp),
            modifier = Modifier.fillMaxWidth().border(1.dp, MaterialTheme.colorScheme.primary.copy(alpha = 0.3f), RoundedCornerShape(12.dp))
        ) {
            Column(modifier = Modifier.padding(16.dp)) {
                Text("Monthly EMI", style = MaterialTheme.typography.bodyMedium, color = MaterialTheme.colorScheme.onPrimaryContainer)
                Text(
                    text = NumberFormatters.formatNepaliCurrency(result.monthlyEmi),
                    style = MaterialTheme.typography.headlineMedium,
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colorScheme.onPrimaryContainer
                )

                Spacer(modifier = Modifier.height(12.dp))
                Divider(color = MaterialTheme.colorScheme.onPrimaryContainer.copy(alpha = 0.2f))
                Spacer(modifier = Modifier.height(12.dp))

                Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
                    Column {
                        Text("Total Interest", style = MaterialTheme.typography.labelMedium, color = MaterialTheme.colorScheme.onPrimaryContainer.copy(alpha = 0.8f))
                        Text(
                            NumberFormatters.formatNepaliCurrency(result.totalInterest),
                            style = MaterialTheme.typography.titleMedium,
                            fontWeight = FontWeight.SemiBold,
                            color = MaterialTheme.colorScheme.secondary
                        )
                    }
                    Column(horizontalAlignment = Alignment.End) {
                        Text("Total Payment", style = MaterialTheme.typography.labelMedium, color = MaterialTheme.colorScheme.onPrimaryContainer.copy(alpha = 0.8f))
                        Text(
                            NumberFormatters.formatNepaliCurrency(result.totalPayment),
                            style = MaterialTheme.typography.titleMedium,
                            fontWeight = FontWeight.SemiBold,
                            color = MaterialTheme.colorScheme.onPrimaryContainer
                        )
                    }
                }

                val summary = "Loan: ${NumberFormatters.formatNepaliCurrency(principal)} | EMI: ${NumberFormatters.formatNepaliCurrency(result.monthlyEmi)} | Interest: ${NumberFormatters.formatNepaliCurrency(result.totalInterest)}"
                ResultActionButtons(toolTitle = "EMI Calculator", resultSummary = summary)
            }
        }

        // Amortization Toggle
        Card(
            colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
            shape = RoundedCornerShape(12.dp),
            modifier = Modifier.fillMaxWidth().border(1.dp, MaterialTheme.colorScheme.outline, RoundedCornerShape(12.dp))
        ) {
            Column(modifier = Modifier.padding(16.dp)) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text("Payment Schedule (Amortization)", fontWeight = FontWeight.Bold, style = MaterialTheme.typography.titleSmall, color = MaterialTheme.colorScheme.onSurface)
                    IconButton(onClick = { showAmortization = !showAmortization }) {
                        Icon(
                            if (showAmortization) Icons.Default.ExpandLess else Icons.Default.ExpandMore,
                            contentDescription = "Toggle Schedule",
                            tint = MaterialTheme.colorScheme.onSurface
                        )
                    }
                }

                AnimatedVisibility(visible = showAmortization) {
                    Column(modifier = Modifier.padding(top = 8.dp), verticalArrangement = Arrangement.spacedBy(6.dp)) {
                        Row(
                            modifier = Modifier.fillMaxWidth().background(MaterialTheme.colorScheme.surfaceVariant, RoundedCornerShape(6.dp)).padding(8.dp),
                            horizontalArrangement = Arrangement.SpaceBetween
                        ) {
                            Text("Month", fontWeight = FontWeight.Bold, style = MaterialTheme.typography.labelSmall, color = MaterialTheme.colorScheme.onSurface)
                            Text("Principal", fontWeight = FontWeight.Bold, style = MaterialTheme.typography.labelSmall, color = MaterialTheme.colorScheme.onSurface)
                            Text("Interest", fontWeight = FontWeight.Bold, style = MaterialTheme.typography.labelSmall, color = MaterialTheme.colorScheme.onSurface)
                            Text("Balance", fontWeight = FontWeight.Bold, style = MaterialTheme.typography.labelSmall, color = MaterialTheme.colorScheme.onSurface)
                        }

                        result.amortization.take(15).forEach { row ->
                            Row(
                                modifier = Modifier.fillMaxWidth().padding(horizontal = 8.dp, vertical = 4.dp),
                                horizontalArrangement = Arrangement.SpaceBetween
                            ) {
                                Text("#${row.period}", style = MaterialTheme.typography.bodySmall, color = MaterialTheme.colorScheme.onSurface)
                                Text(NumberFormatters.formatNumber(row.principalPart, 0), style = MaterialTheme.typography.bodySmall, color = MaterialTheme.colorScheme.onSurface)
                                Text(NumberFormatters.formatNumber(row.interestPart, 0), style = MaterialTheme.typography.bodySmall, color = MaterialTheme.colorScheme.onSurface)
                                Text(NumberFormatters.formatNumber(row.balance, 0), style = MaterialTheme.typography.bodySmall, color = MaterialTheme.colorScheme.onSurface)
                            }
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun SipCalculatorView() {
    val defaultMonthly = "5000"
    val defaultRate = "12.0"
    val defaultYears = "10"

    var monthlyStr by remember { mutableStateOf(defaultMonthly) }
    var rateStr by remember { mutableStateOf(defaultRate) }
    var yearsStr by remember { mutableStateOf(defaultYears) }

    val monthly = monthlyStr.toDoubleOrNull() ?: 0.0
    val rate = rateStr.toDoubleOrNull() ?: 0.0
    val years = yearsStr.toIntOrNull() ?: 0

    val res = CalculatorEngine.calculateSip(monthly, rate, years)

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
                    Text("SIP Investment Parameters", style = MaterialTheme.typography.titleMedium, fontWeight = FontWeight.Bold, color = MaterialTheme.colorScheme.onSurface)
                    TextButton(
                        onClick = {
                            monthlyStr = defaultMonthly
                            rateStr = defaultRate
                            yearsStr = defaultYears
                        }
                    ) {
                        Icon(Icons.Default.Refresh, contentDescription = "Reset", modifier = Modifier.padding(end = 4.dp))
                        Text("Reset")
                    }
                }

                OutlinedTextField(
                    value = monthlyStr,
                    onValueChange = { monthlyStr = it.filter { ch -> ch.isDigit() || ch == '.' } },
                    label = { Text("Monthly Investment Amount") },
                    prefix = { Text("NPR ") },
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                    modifier = Modifier.fillMaxWidth()
                )

                OutlinedTextField(
                    value = rateStr,
                    onValueChange = { rateStr = it.filter { ch -> ch.isDigit() || ch == '.' } },
                    label = { Text("Expected Annual Return Rate") },
                    suffix = { Text("%") },
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Decimal),
                    modifier = Modifier.fillMaxWidth()
                )

                OutlinedTextField(
                    value = yearsStr,
                    onValueChange = { yearsStr = it.filter { ch -> ch.isDigit() } },
                    label = { Text("Time Period (Years)") },
                    suffix = { Text("Years") },
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                    modifier = Modifier.fillMaxWidth()
                )
            }
        }

        Card(
            colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.primaryContainer),
            shape = RoundedCornerShape(12.dp),
            modifier = Modifier.fillMaxWidth().border(1.dp, MaterialTheme.colorScheme.primary.copy(alpha = 0.3f), RoundedCornerShape(12.dp))
        ) {
            Column(modifier = Modifier.padding(16.dp)) {
                Text("Expected Future Maturity Value", style = MaterialTheme.typography.bodyMedium, color = MaterialTheme.colorScheme.onPrimaryContainer)
                Text(
                    text = NumberFormatters.formatNepaliCurrency(res.futureValue),
                    style = MaterialTheme.typography.headlineMedium,
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colorScheme.onPrimaryContainer
                )

                Spacer(modifier = Modifier.height(12.dp))
                Divider(color = MaterialTheme.colorScheme.onPrimaryContainer.copy(alpha = 0.2f))
                Spacer(modifier = Modifier.height(12.dp))

                Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
                    Column {
                        Text("Total Invested Amount", style = MaterialTheme.typography.labelMedium, color = MaterialTheme.colorScheme.onPrimaryContainer.copy(alpha = 0.8f))
                        Text(
                            NumberFormatters.formatNepaliCurrency(res.totalInvested),
                            style = MaterialTheme.typography.titleMedium,
                            fontWeight = FontWeight.SemiBold,
                            color = MaterialTheme.colorScheme.onPrimaryContainer
                        )
                    }
                    Column(horizontalAlignment = Alignment.End) {
                        Text("Estimated Wealth Gain", style = MaterialTheme.typography.labelMedium, color = MaterialTheme.colorScheme.onPrimaryContainer.copy(alpha = 0.8f))
                        Text(
                            NumberFormatters.formatNepaliCurrency(res.estimatedReturns),
                            style = MaterialTheme.typography.titleMedium,
                            fontWeight = FontWeight.SemiBold,
                            color = Color(0xFF16A34A)
                        )
                    }
                }

                val summary = "SIP NPR $monthly/mo for $years yrs @ $rate%: Invested = ${NumberFormatters.formatNepaliCurrency(res.totalInvested)}, Returns = ${NumberFormatters.formatNepaliCurrency(res.estimatedReturns)}, Total = ${NumberFormatters.formatNepaliCurrency(res.futureValue)}"
                ResultActionButtons(toolTitle = "SIP Calculator", resultSummary = summary)
            }
        }
    }
}

@Composable
fun SalaryCalculatorView() {
    val defaultGross = "75000"
    var grossMonthlyStr by remember { mutableStateOf(defaultGross) }
    var isMarried by remember { mutableStateOf(false) }
    var hasSsf by remember { mutableStateOf(true) }

    val gross = grossMonthlyStr.toDoubleOrNull() ?: 0.0
    val result = CalculatorEngine.calculateNepalSalaryTax(gross, isMarried, hasSsf)

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
                    Text("Salary & Tax Deductions", style = MaterialTheme.typography.titleMedium, fontWeight = FontWeight.Bold, color = MaterialTheme.colorScheme.onSurface)
                    TextButton(
                        onClick = {
                            grossMonthlyStr = defaultGross
                            isMarried = false
                            hasSsf = true
                        }
                    ) {
                        Icon(Icons.Default.Refresh, contentDescription = "Reset", modifier = Modifier.padding(end = 4.dp))
                        Text("Reset")
                    }
                }

                OutlinedTextField(
                    value = grossMonthlyStr,
                    onValueChange = { grossMonthlyStr = it.filter { ch -> ch.isDigit() || ch == '.' } },
                    label = { Text("Monthly Gross Salary (NPR)") },
                    prefix = { Text("NPR ") },
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                    modifier = Modifier.fillMaxWidth()
                )

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text("Marital Status", color = MaterialTheme.colorScheme.onSurface)
                    Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                        FilterChip(selected = !isMarried, onClick = { isMarried = false }, label = { Text("Individual") })
                        FilterChip(selected = isMarried, onClick = { isMarried = true }, label = { Text("Couple / Married") })
                    }
                }

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Column {
                        Text("Social Security Fund (SSF 11%)", fontWeight = FontWeight.Medium, color = MaterialTheme.colorScheme.onSurface)
                        Text("11% employee contribution, exempts 1% slab 1 tax", style = MaterialTheme.typography.bodySmall, color = MaterialTheme.colorScheme.onSurfaceVariant)
                    }
                    Switch(checked = hasSsf, onCheckedChange = { hasSsf = it })
                }
            }
        }

        // Results
        Card(
            colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.primaryContainer),
            shape = RoundedCornerShape(12.dp),
            modifier = Modifier.fillMaxWidth().border(1.dp, MaterialTheme.colorScheme.primary.copy(alpha = 0.3f), RoundedCornerShape(12.dp))
        ) {
            Column(modifier = Modifier.padding(16.dp)) {
                Text("Net Monthly Take-Home Pay", style = MaterialTheme.typography.bodyMedium, color = MaterialTheme.colorScheme.onPrimaryContainer)
                Text(
                    text = NumberFormatters.formatNepaliCurrency(result.netSalaryMonthly),
                    style = MaterialTheme.typography.headlineMedium,
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colorScheme.onPrimaryContainer
                )

                Spacer(modifier = Modifier.height(12.dp))
                Divider(color = MaterialTheme.colorScheme.onPrimaryContainer.copy(alpha = 0.2f))
                Spacer(modifier = Modifier.height(12.dp))

                Column(verticalArrangement = Arrangement.spacedBy(6.dp)) {
                    Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
                        Text("Monthly SSF Deduction (11%):", style = MaterialTheme.typography.bodySmall, color = MaterialTheme.colorScheme.onPrimaryContainer.copy(alpha = 0.85f))
                        Text(NumberFormatters.formatNepaliCurrency(result.ssfContributionMonthly), style = MaterialTheme.typography.bodySmall, fontWeight = FontWeight.SemiBold, color = MaterialTheme.colorScheme.onPrimaryContainer)
                    }
                    Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
                        Text("Monthly Income Tax (IRD):", style = MaterialTheme.typography.bodySmall, color = MaterialTheme.colorScheme.onPrimaryContainer.copy(alpha = 0.85f))
                        Text(NumberFormatters.formatNepaliCurrency(result.totalTaxMonthly), style = MaterialTheme.typography.bodySmall, fontWeight = FontWeight.SemiBold, color = MaterialTheme.colorScheme.secondary)
                    }
                    Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
                        Text("Annual Tax Payable:", style = MaterialTheme.typography.bodySmall, color = MaterialTheme.colorScheme.onPrimaryContainer.copy(alpha = 0.85f))
                        Text(NumberFormatters.formatNepaliCurrency(result.totalTaxAnnual), style = MaterialTheme.typography.bodySmall, fontWeight = FontWeight.SemiBold, color = MaterialTheme.colorScheme.onPrimaryContainer)
                    }
                }

                val summary = "Gross: NPR $gross | Net Take-Home: ${NumberFormatters.formatNepaliCurrency(result.netSalaryMonthly)} | Tax/mo: ${NumberFormatters.formatNepaliCurrency(result.totalTaxMonthly)}"
                ResultActionButtons(toolTitle = "Salary Calculator", resultSummary = summary)
            }
        }
    }
}
