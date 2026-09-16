package com.example.ui.screens.calculators

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
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Pause
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Divider
import androidx.compose.material3.FilterChip
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import com.example.data.service.CalculatorEngine
import com.example.ui.components.NumberFormatters
import com.example.ui.components.ResultActionButtons
import kotlinx.coroutines.delay

data class SubjectRow(
    val id: Int,
    var name: String = "",
    var credits: String = "3",
    var grade: String = "A (3.6)"
)

val gradeOptions = listOf(
    "A+ (4.0)" to 4.0,
    "A (3.6)" to 3.6,
    "B+ (3.2)" to 3.2,
    "B (2.8)" to 2.8,
    "C+ (2.4)" to 2.4,
    "C (2.0)" to 2.0,
    "D (1.6)" to 1.6
)

@Composable
fun GpaCalculatorView() {
    var nextId by remember { mutableIntStateOf(4) }
    val subjects = remember {
        mutableStateListOf(
            SubjectRow(1, "English", "3", "A (3.6)"),
            SubjectRow(2, "Mathematics", "4", "A+ (4.0)"),
            SubjectRow(3, "Computer Science", "3", "B+ (3.2)")
        )
    }

    val coursePairs = subjects.map { sub ->
        val cr = sub.credits.toDoubleOrNull() ?: 0.0
        val gp = gradeOptions.find { it.first == sub.grade }?.second ?: 3.0
        Pair(cr, gp)
    }

    val (gpa, totalCredits) = CalculatorEngine.calculateGpa(coursePairs)

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
                    Text("Courses & Grades", style = MaterialTheme.typography.titleMedium, fontWeight = FontWeight.Bold, color = MaterialTheme.colorScheme.onSurface)
                    Row(horizontalArrangement = Arrangement.spacedBy(4.dp)) {
                        TextButton(onClick = {
                            subjects.clear()
                            subjects.add(SubjectRow(1, "English", "3", "A (3.6)"))
                            subjects.add(SubjectRow(2, "Mathematics", "4", "A+ (4.0)"))
                            subjects.add(SubjectRow(3, "Computer Science", "3", "B+ (3.2)"))
                            nextId = 4
                        }) {
                            Icon(Icons.Default.Refresh, contentDescription = "Reset", modifier = Modifier.padding(end = 4.dp))
                            Text("Reset")
                        }
                        OutlinedButton(
                            onClick = {
                                subjects.add(SubjectRow(nextId++, "Course ${subjects.size + 1}", "3", "A (3.6)"))
                            }
                        ) {
                            Icon(Icons.Default.Add, contentDescription = "Add", modifier = Modifier.padding(end = 4.dp))
                            Text("Add")
                        }
                    }
                }

                subjects.forEachIndexed { index, sub ->
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.spacedBy(8.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        OutlinedTextField(
                            value = sub.name,
                            onValueChange = { sub.name = it },
                            label = { Text("Subject") },
                            modifier = Modifier.weight(1.4f),
                            singleLine = true
                        )

                        OutlinedTextField(
                            value = sub.credits,
                            onValueChange = { sub.credits = it.filter { ch -> ch.isDigit() || ch == '.' } },
                            label = { Text("Cr") },
                            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                            modifier = Modifier.weight(0.8f),
                            singleLine = true
                        )

                        Column(modifier = Modifier.weight(1.3f)) {
                            Text("Grade", style = MaterialTheme.typography.labelSmall, color = MaterialTheme.colorScheme.onSurfaceVariant)
                            Text(sub.grade, fontWeight = FontWeight.SemiBold, style = MaterialTheme.typography.bodySmall, color = MaterialTheme.colorScheme.onSurface)
                        }

                        if (subjects.size > 1) {
                            IconButton(onClick = { subjects.removeAt(index) }) {
                                Icon(Icons.Default.Delete, contentDescription = "Delete", tint = MaterialTheme.colorScheme.error)
                            }
                        }
                    }
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
                Text("Calculated GPA", style = MaterialTheme.typography.bodyMedium, color = MaterialTheme.colorScheme.onPrimaryContainer)
                Text(
                    text = "${NumberFormatters.formatNumber(gpa, 2)} / 4.00",
                    style = MaterialTheme.typography.headlineMedium,
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colorScheme.onPrimaryContainer
                )

                Spacer(modifier = Modifier.height(8.dp))
                Text(
                    "Total Credits: ${totalCredits.toInt()} credit hours",
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.onPrimaryContainer.copy(alpha = 0.85f)
                )

                val summary = "GPA: ${NumberFormatters.formatNumber(gpa, 2)} / 4.0 across ${totalCredits.toInt()} credits."
                ResultActionButtons(toolTitle = "GPA Calculator", resultSummary = summary)
            }
        }
    }
}

@Composable
fun AttendanceCalculatorView() {
    val defaultTotal = "48"
    val defaultAttended = "38"
    val defaultReq = "75"

    var totalStr by remember { mutableStateOf(defaultTotal) }
    var attendedStr by remember { mutableStateOf(defaultAttended) }
    var reqStr by remember { mutableStateOf(defaultReq) }

    val total = totalStr.toIntOrNull() ?: 0
    val rawAttended = attendedStr.toIntOrNull() ?: 0
    val attended = rawAttended.coerceAtMost(total)
    val req = (reqStr.toDoubleOrNull() ?: 75.0).coerceIn(0.0, 100.0)

    val res = CalculatorEngine.calculateAttendance(total, attended, req)

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
                    Text("Class Records", style = MaterialTheme.typography.titleMedium, fontWeight = FontWeight.Bold, color = MaterialTheme.colorScheme.onSurface)
                    TextButton(onClick = {
                        totalStr = defaultTotal
                        attendedStr = defaultAttended
                        reqStr = defaultReq
                    }) {
                        Icon(Icons.Default.Refresh, contentDescription = "Reset", modifier = Modifier.padding(end = 4.dp))
                        Text("Reset")
                    }
                }

                OutlinedTextField(
                    value = totalStr,
                    onValueChange = { totalStr = it.filter { ch -> ch.isDigit() } },
                    label = { Text("Total Classes Conducted") },
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                    modifier = Modifier.fillMaxWidth()
                )

                OutlinedTextField(
                    value = attendedStr,
                    onValueChange = { attendedStr = it.filter { ch -> ch.isDigit() } },
                    label = { Text("Classes You Attended") },
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                    modifier = Modifier.fillMaxWidth()
                )

                OutlinedTextField(
                    value = reqStr,
                    onValueChange = { reqStr = it.filter { ch -> ch.isDigit() || ch == '.' } },
                    label = { Text("Minimum Required Attendance (%)") },
                    suffix = { Text("%") },
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                    modifier = Modifier.fillMaxWidth()
                )
            }
        }

        val mainColor = if (res.isEligible) Color(0xFF16A34A) else MaterialTheme.colorScheme.error

        Card(
            colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surfaceVariant),
            shape = RoundedCornerShape(12.dp),
            modifier = Modifier.fillMaxWidth().border(1.dp, MaterialTheme.colorScheme.outline, RoundedCornerShape(12.dp))
        ) {
            Column(modifier = Modifier.padding(16.dp)) {
                Text("Current Attendance", style = MaterialTheme.typography.bodyMedium, color = MaterialTheme.colorScheme.onSurfaceVariant)
                Text(
                    text = "${NumberFormatters.formatNumber(res.currentPercentage, 1)}%",
                    style = MaterialTheme.typography.headlineMedium,
                    fontWeight = FontWeight.Bold,
                    color = mainColor
                )

                Spacer(modifier = Modifier.height(12.dp))
                Divider(color = MaterialTheme.colorScheme.outline.copy(alpha = 0.3f))
                Spacer(modifier = Modifier.height(12.dp))

                if (res.isEligible) {
                    Text(
                        "You are eligible for exams! You can safely miss up to ${res.classesCanMiss} upcoming classes while staying at or above $req%.",
                        style = MaterialTheme.typography.bodyMedium,
                        color = MaterialTheme.colorScheme.onSurfaceVariant,
                        fontWeight = FontWeight.Medium
                    )
                } else if (res.is100PercentImpossible) {
                    Text(
                        "Shortfall warning! Achieving 100% attendance is impossible because classes have already been missed.",
                        style = MaterialTheme.typography.bodyMedium,
                        color = MaterialTheme.colorScheme.error,
                        fontWeight = FontWeight.Medium
                    )
                } else {
                    Text(
                        "Shortfall warning! You need to attend the next ${res.classesNeeded} consecutive classes without missing to reach $req%.",
                        style = MaterialTheme.typography.bodyMedium,
                        color = MaterialTheme.colorScheme.error,
                        fontWeight = FontWeight.Medium
                    )
                }

                val summary = "Attendance: ${NumberFormatters.formatNumber(res.currentPercentage, 1)}% (Req: $req%). ${if (res.isEligible) "Safe to miss: ${res.classesCanMiss}" else "Must attend next: ${res.classesNeeded}"}"
                ResultActionButtons(toolTitle = "Attendance Calculator", resultSummary = summary)
            }
        }
    }
}

@Composable
fun StudyTimerView() {
    var modeMinutes by remember { mutableIntStateOf(25) }
    var secondsLeft by remember { mutableIntStateOf(25 * 60) }
    var isRunning by remember { mutableStateOf(false) }

    LaunchedEffect(isRunning, secondsLeft) {
        if (isRunning && secondsLeft > 0) {
            delay(1000L)
            secondsLeft--
        } else if (secondsLeft == 0) {
            isRunning = false
        }
    }

    val minutes = secondsLeft / 60
    val seconds = secondsLeft % 60
    val formattedTime = String.format("%02d:%02d", minutes, seconds)

    Column(
        modifier = Modifier.fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
            FilterChip(
                selected = modeMinutes == 25,
                onClick = {
                    modeMinutes = 25
                    secondsLeft = 25 * 60
                    isRunning = false
                },
                label = { Text("Study (25m)") }
            )
            FilterChip(
                selected = modeMinutes == 5,
                onClick = {
                    modeMinutes = 5
                    secondsLeft = 5 * 60
                    isRunning = false
                },
                label = { Text("Short Break (5m)") }
            )
            FilterChip(
                selected = modeMinutes == 15,
                onClick = {
                    modeMinutes = 15
                    secondsLeft = 15 * 60
                    isRunning = false
                },
                label = { Text("Long Break (15m)") }
            )
        }

        Box(
            modifier = Modifier
                .padding(24.dp)
                .clip(CircleShape)
                .background(MaterialTheme.colorScheme.primaryContainer)
                .border(4.dp, MaterialTheme.colorScheme.primary, CircleShape)
                .padding(48.dp),
            contentAlignment = Alignment.Center
        ) {
            Text(
                text = formattedTime,
                style = MaterialTheme.typography.displayMedium,
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.onPrimaryContainer
            )
        }

        Row(horizontalArrangement = Arrangement.spacedBy(12.dp)) {
            Button(
                onClick = { isRunning = !isRunning },
                colors = ButtonDefaults.buttonColors(
                    containerColor = if (isRunning) MaterialTheme.colorScheme.error else MaterialTheme.colorScheme.primary,
                    contentColor = if (isRunning) MaterialTheme.colorScheme.onError else MaterialTheme.colorScheme.onPrimary
                )
            ) {
                Icon(if (isRunning) Icons.Default.Pause else Icons.Default.PlayArrow, contentDescription = "Control")
                Spacer(modifier = Modifier.padding(2.dp))
                Text(if (isRunning) "Pause" else "Start Focus")
            }

            OutlinedButton(onClick = {
                isRunning = false
                secondsLeft = modeMinutes * 60
            }) {
                Icon(Icons.Default.Refresh, contentDescription = "Reset")
                Spacer(modifier = Modifier.padding(2.dp))
                Text("Reset")
            }
        }
    }
}
