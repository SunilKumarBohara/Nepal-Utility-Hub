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
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import com.example.data.service.BikramSambatConverter
import com.example.data.service.CalculatorEngine
import com.example.ui.components.NumberFormatters
import com.example.ui.components.ResultActionButtons

@Composable
fun NepaliDateConverterView() {
    var isBsToAd by remember { mutableStateOf(true) }
    var yearStr by remember { mutableStateOf("2081") }
    var monthStr by remember { mutableStateOf("6") }
    var dayStr by remember { mutableStateOf("1") }

    val y = (yearStr.toIntOrNull() ?: 2081).coerceIn(2000, 2100)
    val m = (monthStr.toIntOrNull() ?: 1).coerceIn(1, 12)
    val d = (dayStr.toIntOrNull() ?: 1).coerceIn(1, 32)

    Column(verticalArrangement = Arrangement.spacedBy(16.dp)) {
        Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
            FilterChip(
                selected = isBsToAd,
                onClick = {
                    isBsToAd = true
                    yearStr = "2081"
                    monthStr = "6"
                    dayStr = "1"
                },
                label = { Text("वि.सं. (BS) ➔ ई.सं. (AD)") }
            )
            FilterChip(
                selected = !isBsToAd,
                onClick = {
                    isBsToAd = false
                    yearStr = "2024"
                    monthStr = "9"
                    dayStr = "17"
                },
                label = { Text("ई.सं. (AD) ➔ वि.सं. (BS)") }
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
                    Text(
                        text = if (isBsToAd) "Enter Bikram Sambat Date (वि.सं.)" else "Enter Gregorian Date (AD)",
                        style = MaterialTheme.typography.titleMedium,
                        fontWeight = FontWeight.Bold,
                        color = MaterialTheme.colorScheme.onSurface
                    )
                    TextButton(onClick = {
                        if (isBsToAd) {
                            yearStr = "2081"
                            monthStr = "6"
                            dayStr = "1"
                        } else {
                            yearStr = "2024"
                            monthStr = "9"
                            dayStr = "17"
                        }
                    }) {
                        Icon(Icons.Default.Refresh, contentDescription = "Reset", modifier = Modifier.padding(end = 4.dp))
                        Text("Reset")
                    }
                }

                Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                    OutlinedTextField(
                        value = yearStr,
                        onValueChange = { yearStr = it.filter { ch -> ch.isDigit() } },
                        label = { Text("Year") },
                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                        modifier = Modifier.weight(1.2f)
                    )
                    OutlinedTextField(
                        value = monthStr,
                        onValueChange = { monthStr = it.filter { ch -> ch.isDigit() } },
                        label = { Text("Month (1-12)") },
                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                        modifier = Modifier.weight(1f)
                    )
                    OutlinedTextField(
                        value = dayStr,
                        onValueChange = { dayStr = it.filter { ch -> ch.isDigit() } },
                        label = { Text("Day") },
                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                        modifier = Modifier.weight(1f)
                    )
                }
            }
        }

        // Result
        Card(
            colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.primaryContainer),
            shape = RoundedCornerShape(12.dp),
            modifier = Modifier.fillMaxWidth().border(1.dp, MaterialTheme.colorScheme.primary.copy(alpha = 0.3f), RoundedCornerShape(12.dp))
        ) {
            Column(modifier = Modifier.padding(16.dp)) {
                if (isBsToAd) {
                    val (adY, adM, adD) = BikramSambatConverter.convertBsToAd(y, m, d)
                    val monthName = java.text.DateFormatSymbols().months.getOrNull(adM - 1) ?: "$adM"

                    Text("Converted English Date (AD)", style = MaterialTheme.typography.bodyMedium, color = MaterialTheme.colorScheme.onPrimaryContainer)
                    Text(
                        text = "$monthName $adD, $adY",
                        style = MaterialTheme.typography.headlineMedium,
                        fontWeight = FontWeight.Bold,
                        color = MaterialTheme.colorScheme.onPrimaryContainer
                    )
                    Spacer(modifier = Modifier.height(6.dp))
                    Text(
                        "Standard ISO: $adY-${adM.toString().padStart(2, '0')}-${adD.toString().padStart(2, '0')}",
                        style = MaterialTheme.typography.bodySmall,
                        color = MaterialTheme.colorScheme.onPrimaryContainer.copy(alpha = 0.85f)
                    )

                    val summary = "$y-$m-$d BS = $monthName $adD, $adY AD"
                    ResultActionButtons(toolTitle = "Nepali Date Converter", resultSummary = summary)
                } else {
                    val bs = BikramSambatConverter.convertAdToBs(y, m, d)
                    Text("Converted Nepali Date (वि.सं.)", style = MaterialTheme.typography.bodyMedium, color = MaterialTheme.colorScheme.onPrimaryContainer)
                    Text(
                        text = "${bs.year} ${bs.monthNameNp} ${bs.day}",
                        style = MaterialTheme.typography.headlineMedium,
                        fontWeight = FontWeight.Bold,
                        color = MaterialTheme.colorScheme.onPrimaryContainer
                    )
                    Spacer(modifier = Modifier.height(4.dp))
                    Text(
                        "${bs.monthNameEn} ${bs.day}, ${bs.year} BS (${bs.dayOfWeekNp} / ${bs.dayOfWeekEn})",
                        style = MaterialTheme.typography.bodyMedium,
                        color = MaterialTheme.colorScheme.onPrimaryContainer.copy(alpha = 0.85f)
                    )

                    val summary = "$y-$m-$d AD = ${bs.year} ${bs.monthNameNp} ${bs.day}, ${bs.dayOfWeekNp}"
                    ResultActionButtons(toolTitle = "Nepali Date Converter", resultSummary = summary)
                }
            }
        }
    }
}

@Composable
fun NepalLandAreaConverterView() {
    val defaultRopani = "0"
    val defaultAana = "4"
    val defaultPaisa = "0"
    val defaultDaam = "0"

    var ropaniStr by remember { mutableStateOf(defaultRopani) }
    var aanaStr by remember { mutableStateOf(defaultAana) }
    var paisaStr by remember { mutableStateOf(defaultPaisa) }
    var daamStr by remember { mutableStateOf(defaultDaam) }

    val ropani = (ropaniStr.toDoubleOrNull() ?: 0.0).coerceAtLeast(0.0)
    val aana = (aanaStr.toDoubleOrNull() ?: 0.0).coerceAtLeast(0.0)
    val paisa = (paisaStr.toDoubleOrNull() ?: 0.0).coerceAtLeast(0.0)
    val daam = (daamStr.toDoubleOrNull() ?: 0.0).coerceAtLeast(0.0)

    val res = CalculatorEngine.convertNepalLand(ropani, aana, paisa, daam)

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
                    Text("Hilly Region Land Units (रोपनी - आना - पैसा - दाम)", style = MaterialTheme.typography.titleMedium, fontWeight = FontWeight.Bold, color = MaterialTheme.colorScheme.onSurface)
                    TextButton(onClick = {
                        ropaniStr = defaultRopani
                        aanaStr = defaultAana
                        paisaStr = defaultPaisa
                        daamStr = defaultDaam
                    }) {
                        Icon(Icons.Default.Refresh, contentDescription = "Reset", modifier = Modifier.padding(end = 4.dp))
                        Text("Reset")
                    }
                }

                Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                    OutlinedTextField(
                        value = ropaniStr,
                        onValueChange = { ropaniStr = it.filter { ch -> ch.isDigit() || ch == '.' } },
                        label = { Text("Ropani") },
                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Decimal),
                        modifier = Modifier.weight(1f)
                    )
                    OutlinedTextField(
                        value = aanaStr,
                        onValueChange = { aanaStr = it.filter { ch -> ch.isDigit() || ch == '.' } },
                        label = { Text("Aana") },
                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Decimal),
                        modifier = Modifier.weight(1f)
                    )
                }
                Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                    OutlinedTextField(
                        value = paisaStr,
                        onValueChange = { paisaStr = it.filter { ch -> ch.isDigit() || ch == '.' } },
                        label = { Text("Paisa") },
                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Decimal),
                        modifier = Modifier.weight(1f)
                    )
                    OutlinedTextField(
                        value = daamStr,
                        onValueChange = { daamStr = it.filter { ch -> ch.isDigit() || ch == '.' } },
                        label = { Text("Daam") },
                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Decimal),
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
                Text("Total Area in Square Feet", style = MaterialTheme.typography.bodyMedium, color = MaterialTheme.colorScheme.onPrimaryContainer)
                Text(
                    text = "${NumberFormatters.formatNumber(res["sqFeet"] ?: 0.0, 2)} Sq. Ft",
                    style = MaterialTheme.typography.headlineMedium,
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colorScheme.onPrimaryContainer
                )

                Spacer(modifier = Modifier.height(12.dp))
                Divider(color = MaterialTheme.colorScheme.onPrimaryContainer.copy(alpha = 0.2f))
                Spacer(modifier = Modifier.height(12.dp))

                Column(verticalArrangement = Arrangement.spacedBy(6.dp)) {
                    Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
                        Text("Square Meters (वर्ग मिटर):", style = MaterialTheme.typography.bodyMedium, color = MaterialTheme.colorScheme.onPrimaryContainer.copy(alpha = 0.85f))
                        Text("${NumberFormatters.formatNumber(res["sqMeters"] ?: 0.0, 2)} m²", fontWeight = FontWeight.Bold, color = MaterialTheme.colorScheme.onPrimaryContainer)
                    }
                    Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
                        Text("Equivalent in Bigha (बिघा):", style = MaterialTheme.typography.bodyMedium, color = MaterialTheme.colorScheme.onPrimaryContainer.copy(alpha = 0.85f))
                        Text(NumberFormatters.formatNumber(res["bigha"] ?: 0.0, 4), fontWeight = FontWeight.Bold, color = MaterialTheme.colorScheme.onPrimaryContainer)
                    }
                    Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
                        Text("Equivalent in Kattha (कट्ठा):", style = MaterialTheme.typography.bodyMedium, color = MaterialTheme.colorScheme.onPrimaryContainer.copy(alpha = 0.85f))
                        Text(NumberFormatters.formatNumber(res["kattha"] ?: 0.0, 2), fontWeight = FontWeight.Bold, color = MaterialTheme.colorScheme.onPrimaryContainer)
                    }
                    Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
                        Text("Equivalent in Dhur (धुर):", style = MaterialTheme.typography.bodyMedium, color = MaterialTheme.colorScheme.onPrimaryContainer.copy(alpha = 0.85f))
                        Text(NumberFormatters.formatNumber(res["dhur"] ?: 0.0, 2), fontWeight = FontWeight.Bold, color = MaterialTheme.colorScheme.onPrimaryContainer)
                    }
                }

                val summary = "Land: $ropani Ropani, $aana Aana = ${NumberFormatters.formatNumber(res["sqFeet"] ?: 0.0, 2)} sq ft (${NumberFormatters.formatNumber(res["sqMeters"] ?: 0.0, 2)} m²)."
                ResultActionButtons(toolTitle = "Nepal Land Area Converter", resultSummary = summary)
            }
        }
    }
}

@Composable
fun NepalFuelCostView() {
    val defaultDist = "200"
    val defaultMileage = "15"
    val defaultFuelRate = "170"

    var distanceStr by remember { mutableStateOf(defaultDist) }
    var mileageStr by remember { mutableStateOf(defaultMileage) }
    var fuelRateStr by remember { mutableStateOf(defaultFuelRate) }

    val dist = (distanceStr.toDoubleOrNull() ?: 0.0).coerceAtLeast(0.0)
    val mileage = (mileageStr.toDoubleOrNull() ?: 1.0).coerceAtLeast(0.1)
    val rate = (fuelRateStr.toDoubleOrNull() ?: 170.0).coerceAtLeast(0.0)

    val (liters, totalCost) = CalculatorEngine.calculateNepalFuelCost(dist, mileage, rate)

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
                    Text("Trip & Vehicle Specs", style = MaterialTheme.typography.titleMedium, fontWeight = FontWeight.Bold, color = MaterialTheme.colorScheme.onSurface)
                    TextButton(onClick = {
                        distanceStr = defaultDist
                        mileageStr = defaultMileage
                        fuelRateStr = defaultFuelRate
                    }) {
                        Icon(Icons.Default.Refresh, contentDescription = "Reset", modifier = Modifier.padding(end = 4.dp))
                        Text("Reset")
                    }
                }

                OutlinedTextField(
                    value = distanceStr,
                    onValueChange = { distanceStr = it.filter { ch -> ch.isDigit() || ch == '.' } },
                    label = { Text("Trip Distance (km)") },
                    suffix = { Text("km") },
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                    modifier = Modifier.fillMaxWidth()
                )

                OutlinedTextField(
                    value = mileageStr,
                    onValueChange = { mileageStr = it.filter { ch -> ch.isDigit() || ch == '.' } },
                    label = { Text("Vehicle Mileage (km per liter)") },
                    suffix = { Text("km/L") },
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Decimal),
                    modifier = Modifier.fillMaxWidth()
                )

                OutlinedTextField(
                    value = fuelRateStr,
                    onValueChange = { fuelRateStr = it.filter { ch -> ch.isDigit() || ch == '.' } },
                    label = { Text("Current Fuel Rate per Liter (NPR)") },
                    prefix = { Text("NPR ") },
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
                Text("Estimated Total Fuel Expense", style = MaterialTheme.typography.bodyMedium, color = MaterialTheme.colorScheme.onPrimaryContainer)
                Text(
                    text = NumberFormatters.formatNepaliCurrency(totalCost),
                    style = MaterialTheme.typography.headlineMedium,
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colorScheme.onPrimaryContainer
                )

                Spacer(modifier = Modifier.height(8.dp))
                Text(
                    "Fuel Needed: ${NumberFormatters.formatNumber(liters, 2)} Liters for ${dist.toInt()} km journey.",
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.onPrimaryContainer.copy(alpha = 0.85f)
                )

                val summary = "Fuel Cost for ${dist.toInt()} km @ $mileage km/L = ${NumberFormatters.formatNepaliCurrency(totalCost)} (${NumberFormatters.formatNumber(liters, 1)} Liters)."
                ResultActionButtons(toolTitle = "Nepal Fuel Cost Calculator", resultSummary = summary)
            }
        }
    }
}
