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
import androidx.compose.material3.FilterChip
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
fun PercentageCalculatorView() {
    val defaultNum1 = "15"
    val defaultNum2 = "8500"

    var num1Str by remember { mutableStateOf(defaultNum1) }
    var num2Str by remember { mutableStateOf(defaultNum2) }

    val p = (num1Str.toDoubleOrNull() ?: 0.0).coerceAtLeast(0.0)
    val total = (num2Str.toDoubleOrNull() ?: 0.0).coerceAtLeast(0.0)
    val resultVal = (p / 100.0) * total

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
                    Text("Calculate Percentage of a Value", style = MaterialTheme.typography.titleMedium, fontWeight = FontWeight.Bold, color = MaterialTheme.colorScheme.onSurface)
                    TextButton(onClick = {
                        num1Str = defaultNum1
                        num2Str = defaultNum2
                    }) {
                        Icon(Icons.Default.Refresh, contentDescription = "Reset", modifier = Modifier.padding(end = 4.dp))
                        Text("Reset")
                    }
                }

                OutlinedTextField(
                    value = num1Str,
                    onValueChange = { num1Str = it.filter { ch -> ch.isDigit() || ch == '.' } },
                    label = { Text("What is Percentage (%)") },
                    suffix = { Text("%") },
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Decimal),
                    modifier = Modifier.fillMaxWidth()
                )

                OutlinedTextField(
                    value = num2Str,
                    onValueChange = { num2Str = it.filter { ch -> ch.isDigit() || ch == '.' } },
                    label = { Text("Of Total Amount") },
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Decimal),
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
                Text("Result", style = MaterialTheme.typography.bodyMedium, color = MaterialTheme.colorScheme.onPrimaryContainer)
                Text(
                    text = NumberFormatters.formatNumber(resultVal, 2),
                    style = MaterialTheme.typography.headlineMedium,
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colorScheme.onPrimaryContainer
                )
                Spacer(modifier = Modifier.height(4.dp))
                Text(
                    "$p% of ${NumberFormatters.formatNumber(total, 2)} is ${NumberFormatters.formatNumber(resultVal, 2)}",
                    style = MaterialTheme.typography.bodySmall,
                    color = MaterialTheme.colorScheme.onPrimaryContainer.copy(alpha = 0.85f)
                )

                val summary = "$p% of $total = ${NumberFormatters.formatNumber(resultVal, 2)}"
                ResultActionButtons(toolTitle = "Percentage Calculator", resultSummary = summary)
            }
        }
    }
}

@Composable
fun VatCalculatorView() {
    val defaultAmount = "10000"
    val defaultVat = "13"

    var amountStr by remember { mutableStateOf(defaultAmount) }
    var vatPercentStr by remember { mutableStateOf(defaultVat) }
    var isExclusive by remember { mutableStateOf(true) }

    val amount = (amountStr.toDoubleOrNull() ?: 0.0).coerceAtLeast(0.0)
    val vatRate = (vatPercentStr.toDoubleOrNull() ?: 13.0).coerceAtLeast(0.0)

    val (vatAmount, finalOrBase) = CalculatorEngine.calculateVat(amount, vatRate, isExclusive)

    Column(verticalArrangement = Arrangement.spacedBy(16.dp)) {
        Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
            FilterChip(
                selected = isExclusive,
                onClick = { isExclusive = true },
                label = { Text("Add VAT (Exclusive)") }
            )
            FilterChip(
                selected = !isExclusive,
                onClick = { isExclusive = false },
                label = { Text("Extract VAT (Inclusive)") }
            )
        }

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
                    Text("Price & VAT Rate", style = MaterialTheme.typography.titleMedium, fontWeight = FontWeight.Bold, color = MaterialTheme.colorScheme.onSurface)
                    TextButton(onClick = {
                        amountStr = defaultAmount
                        vatPercentStr = defaultVat
                        isExclusive = true
                    }) {
                        Icon(Icons.Default.Refresh, contentDescription = "Reset", modifier = Modifier.padding(end = 4.dp))
                        Text("Reset")
                    }
                }

                OutlinedTextField(
                    value = amountStr,
                    onValueChange = { amountStr = it.filter { ch -> ch.isDigit() || ch == '.' } },
                    label = { Text(if (isExclusive) "Net Amount (Before VAT)" else "Gross Total (Including VAT)") },
                    prefix = { Text("NPR ") },
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Decimal),
                    modifier = Modifier.fillMaxWidth()
                )

                OutlinedTextField(
                    value = vatPercentStr,
                    onValueChange = { vatPercentStr = it.filter { ch -> ch.isDigit() || ch == '.' } },
                    label = { Text("VAT Rate (Nepal Standard 13%)") },
                    suffix = { Text("%") },
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Decimal),
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
                Text(
                    text = if (isExclusive) "Gross Total (With VAT)" else "Base Price (Without VAT)",
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.onPrimaryContainer
                )
                Text(
                    text = NumberFormatters.formatNepaliCurrency(finalOrBase),
                    style = MaterialTheme.typography.headlineMedium,
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colorScheme.onPrimaryContainer
                )

                Spacer(modifier = Modifier.height(8.dp))
                Divider(color = MaterialTheme.colorScheme.onPrimaryContainer.copy(alpha = 0.2f))
                Spacer(modifier = Modifier.height(8.dp))

                Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
                    Text("VAT Amount (${vatRate.toInt()}%):", style = MaterialTheme.typography.bodyMedium, color = MaterialTheme.colorScheme.onPrimaryContainer.copy(alpha = 0.85f))
                    Text(NumberFormatters.formatNepaliCurrency(vatAmount), style = MaterialTheme.typography.titleMedium, fontWeight = FontWeight.Bold, color = MaterialTheme.colorScheme.secondary)
                }

                val summary = "Amount: NPR $amount | VAT: ${NumberFormatters.formatNepaliCurrency(vatAmount)} | Total: ${NumberFormatters.formatNepaliCurrency(finalOrBase)}"
                ResultActionButtons(toolTitle = "Nepal VAT Calculator", resultSummary = summary)
            }
        }
    }
}

@Composable
fun DiscountCalculatorView() {
    val defaultPrice = "4500"
    val defaultDiscount = "20"

    var priceStr by remember { mutableStateOf(defaultPrice) }
    var discountStr by remember { mutableStateOf(defaultDiscount) }

    val price = (priceStr.toDoubleOrNull() ?: 0.0).coerceAtLeast(0.0)
    val discount = (discountStr.toDoubleOrNull() ?: 0.0).coerceIn(0.0, 100.0)

    val savings = price * (discount / 100.0)
    val finalPrice = (price - savings).coerceAtLeast(0.0)

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
                    Text("Discount Details", style = MaterialTheme.typography.titleMedium, fontWeight = FontWeight.Bold, color = MaterialTheme.colorScheme.onSurface)
                    TextButton(onClick = {
                        priceStr = defaultPrice
                        discountStr = defaultDiscount
                    }) {
                        Icon(Icons.Default.Refresh, contentDescription = "Reset", modifier = Modifier.padding(end = 4.dp))
                        Text("Reset")
                    }
                }

                OutlinedTextField(
                    value = priceStr,
                    onValueChange = { priceStr = it.filter { ch -> ch.isDigit() || ch == '.' } },
                    label = { Text("Original Price") },
                    prefix = { Text("NPR ") },
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Decimal),
                    modifier = Modifier.fillMaxWidth()
                )

                OutlinedTextField(
                    value = discountStr,
                    onValueChange = { discountStr = it.filter { ch -> ch.isDigit() || ch == '.' } },
                    label = { Text("Discount Percentage (%)") },
                    suffix = { Text("%") },
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Decimal),
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
                Text("Final Discounted Price", style = MaterialTheme.typography.bodyMedium, color = MaterialTheme.colorScheme.onPrimaryContainer)
                Text(
                    text = NumberFormatters.formatNepaliCurrency(finalPrice),
                    style = MaterialTheme.typography.headlineMedium,
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colorScheme.onPrimaryContainer
                )

                Spacer(modifier = Modifier.height(8.dp))
                Divider(color = MaterialTheme.colorScheme.onPrimaryContainer.copy(alpha = 0.2f))
                Spacer(modifier = Modifier.height(8.dp))

                Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
                    Text("Total You Save:", style = MaterialTheme.typography.bodyMedium, color = MaterialTheme.colorScheme.onPrimaryContainer.copy(alpha = 0.85f))
                    Text(NumberFormatters.formatNepaliCurrency(savings), style = MaterialTheme.typography.titleMedium, fontWeight = FontWeight.Bold, color = Color(0xFF16A34A))
                }

                val summary = "Original: NPR $price | Discount: $discount% | Final: ${NumberFormatters.formatNepaliCurrency(finalPrice)} (You save ${NumberFormatters.formatNepaliCurrency(savings)})"
                ResultActionButtons(toolTitle = "Discount Calculator", resultSummary = summary)
            }
        }
    }
}
