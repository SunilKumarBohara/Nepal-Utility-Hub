package com.example.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Bookmark
import androidx.compose.material.icons.filled.BookmarkBorder
import androidx.compose.material.icons.filled.HelpOutline
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Divider
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SuggestionChip
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.data.model.ToolCatalog
import com.example.data.model.ToolItem
import com.example.ui.components.AdBannerPlaceholder
import com.example.ui.screens.calculators.AgeCalculatorView
import com.example.ui.screens.calculators.AttendanceCalculatorView
import com.example.ui.screens.calculators.BmiCalculatorView
import com.example.ui.screens.calculators.DiscountCalculatorView
import com.example.ui.screens.calculators.EmiCalculatorView
import com.example.ui.screens.calculators.GpaCalculatorView
import com.example.ui.screens.calculators.NepalFuelCostView
import com.example.ui.screens.calculators.NepalLandAreaConverterView
import com.example.ui.screens.calculators.NepaliDateConverterView
import com.example.ui.screens.calculators.PercentageCalculatorView
import com.example.ui.screens.calculators.SalaryCalculatorView
import com.example.ui.screens.calculators.SipCalculatorView
import com.example.ui.screens.calculators.StudyTimerView
import com.example.ui.screens.calculators.VatCalculatorView

import androidx.compose.material.icons.filled.DarkMode
import androidx.compose.material.icons.filled.LightMode

@OptIn(ExperimentalMaterial3Api::class, ExperimentalLayoutApi::class)
@Composable
fun CalculatorDetailScreen(
    tool: ToolItem,
    isFavorite: Boolean,
    isDark: Boolean = false,
    onToggleFavorite: () -> Unit,
    onToggleTheme: () -> Unit = {},
    onBack: () -> Unit,
    onSelectTool: (ToolItem) -> Unit
) {
    val scrollState = rememberScrollState()

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Column {
                        Text(
                            text = tool.name,
                            style = MaterialTheme.typography.titleMedium,
                            fontWeight = FontWeight.Bold,
                            color = MaterialTheme.colorScheme.onSurface
                        )
                        Text(
                            text = "Home > ${tool.category.title}",
                            style = MaterialTheme.typography.labelSmall,
                            color = MaterialTheme.colorScheme.onSurfaceVariant
                        )
                    }
                },
                navigationIcon = {
                    IconButton(onClick = onBack, modifier = Modifier.testTag("back_button")) {
                        Icon(
                            Icons.AutoMirrored.Filled.ArrowBack,
                            contentDescription = "Back",
                            tint = MaterialTheme.colorScheme.onSurface
                        )
                    }
                },
                actions = {
                    IconButton(onClick = onToggleTheme, modifier = Modifier.testTag("calculator_theme_toggle_button")) {
                        Icon(
                            imageVector = if (isDark) Icons.Default.LightMode else Icons.Default.DarkMode,
                            contentDescription = if (isDark) "Switch to Light Mode" else "Switch to Dark Mode",
                            tint = if (isDark) Color(0xFFFBBF24) else MaterialTheme.colorScheme.primary
                        )
                    }

                    IconButton(onClick = onToggleFavorite, modifier = Modifier.testTag("favorite_button")) {
                        Icon(
                            imageVector = if (isFavorite) Icons.Default.Bookmark else Icons.Default.BookmarkBorder,
                            contentDescription = "Toggle Favorite",
                            tint = if (isFavorite) Color(0xFFDC2626) else MaterialTheme.colorScheme.onSurfaceVariant
                        )
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.surface
                )
            )
        }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .verticalScroll(scrollState)
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            // Category Badge & Description
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Box(
                    modifier = Modifier
                        .clip(RoundedCornerShape(6.dp))
                        .background(MaterialTheme.colorScheme.primaryContainer)
                        .border(1.dp, MaterialTheme.colorScheme.primary.copy(alpha = 0.3f), RoundedCornerShape(6.dp))
                        .padding(horizontal = 8.dp, vertical = 4.dp)
                ) {
                    Text(
                        text = tool.category.title.uppercase(),
                        style = MaterialTheme.typography.labelSmall,
                        color = MaterialTheme.colorScheme.onPrimaryContainer,
                        fontWeight = FontWeight.Bold
                    )
                }

                if (tool.isPopular) {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(2.dp)
                    ) {
                        Icon(Icons.Default.Star, contentDescription = "Popular", tint = Color(0xFFF59E0B), modifier = Modifier.size(16.dp))
                        Text("Popular Tool", style = MaterialTheme.typography.labelSmall, color = Color(0xFFD97706), fontWeight = FontWeight.SemiBold)
                    }
                }
            }

            Text(
                text = tool.description,
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )


            // Calculator Engine Component
            Box(modifier = Modifier.fillMaxWidth().testTag("calculator_container")) {
                when (tool.slug) {
                    "emi-calculator", "loan-calculator", "loan-affordability", "bike-emi-calculator", "car-emi-calculator" -> {
                        EmiCalculatorView()
                    }
                    "sip-calculator", "compound-interest", "investment-return" -> {
                        SipCalculatorView()
                    }
                    "salary-calculator" -> {
                        SalaryCalculatorView()
                    }
                    "gpa-calculator", "cgpa-calculator", "gpa-to-percentage" -> {
                        GpaCalculatorView()
                    }
                    "attendance-calculator" -> {
                        AttendanceCalculatorView()
                    }
                    "study-timer" -> {
                        StudyTimerView()
                    }
                    "bmi-calculator", "calorie-calculator" -> {
                        BmiCalculatorView()
                    }
                    "age-calculator", "nepali-age-calculator" -> {
                        AgeCalculatorView()
                    }
                    "nepali-date-converter" -> {
                        NepaliDateConverterView()
                    }
                    "area-converter" -> {
                        NepalLandAreaConverterView()
                    }
                    "nepal-fuel-cost" -> {
                        NepalFuelCostView()
                    }
                    "vat-calculator" -> {
                        VatCalculatorView()
                    }
                    "discount-calculator", "profit-loss" -> {
                        DiscountCalculatorView()
                    }
                    else -> {
                        PercentageCalculatorView()
                    }
                }
            }

            // Clean Non-Intrusive In-Feed Ad Banner Placeholder
            AdBannerPlaceholder(adSlotName = "Google AdSense Responsive Unit")

            // Formula and Worked Example Card
            if (tool.formulaFormula.isNotEmpty() || tool.formulaExplanation.isNotEmpty()) {
                Card(
                    colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
                    shape = RoundedCornerShape(12.dp),
                    modifier = Modifier.fillMaxWidth().border(1.dp, MaterialTheme.colorScheme.outline, RoundedCornerShape(12.dp))
                ) {
                    Column(modifier = Modifier.padding(16.dp), verticalArrangement = Arrangement.spacedBy(8.dp)) {
                        Row(verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.spacedBy(6.dp)) {
                            Icon(Icons.Default.Info, contentDescription = "Formula", tint = MaterialTheme.colorScheme.primary, modifier = Modifier.size(20.dp))
                            Text("Formula & How it Works", style = MaterialTheme.typography.titleMedium, fontWeight = FontWeight.Bold, color = MaterialTheme.colorScheme.onSurface)
                        }

                        if (tool.formulaExplanation.isNotEmpty()) {
                            Text(tool.formulaExplanation, style = MaterialTheme.typography.bodyMedium, color = MaterialTheme.colorScheme.onSurfaceVariant)
                        }

                        if (tool.formulaFormula.isNotEmpty()) {
                            Box(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .clip(RoundedCornerShape(6.dp))
                                    .background(MaterialTheme.colorScheme.surfaceVariant)
                                    .padding(10.dp)
                            ) {
                                Text(
                                    text = tool.formulaFormula,
                                    fontFamily = FontFamily.Monospace,
                                    fontWeight = FontWeight.SemiBold,
                                    color = MaterialTheme.colorScheme.onSurface,
                                    fontSize = 13.sp
                                )
                            }
                        }

                        if (tool.workedExample.isNotEmpty()) {
                            Spacer(modifier = Modifier.height(4.dp))
                            Text("Worked Example:", fontWeight = FontWeight.Bold, style = MaterialTheme.typography.bodySmall, color = MaterialTheme.colorScheme.onSurface)
                            Text(tool.workedExample, style = MaterialTheme.typography.bodySmall, color = MaterialTheme.colorScheme.onSurfaceVariant)
                        }
                    }
                }
            }

            // FAQs Section
            if (tool.faqs.isNotEmpty()) {
                Card(
                    colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
                    shape = RoundedCornerShape(12.dp),
                    modifier = Modifier.fillMaxWidth().border(1.dp, MaterialTheme.colorScheme.outline, RoundedCornerShape(12.dp))
                ) {
                    Column(modifier = Modifier.padding(16.dp), verticalArrangement = Arrangement.spacedBy(10.dp)) {
                        Row(verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.spacedBy(6.dp)) {
                            Icon(Icons.Default.HelpOutline, contentDescription = "FAQ", tint = MaterialTheme.colorScheme.primary, modifier = Modifier.size(20.dp))
                            Text("Frequently Asked Questions", style = MaterialTheme.typography.titleMedium, fontWeight = FontWeight.Bold, color = MaterialTheme.colorScheme.onSurface)
                        }

                        tool.faqs.forEach { (q, a) ->
                            Column(verticalArrangement = Arrangement.spacedBy(2.dp)) {
                                Text(q, fontWeight = FontWeight.SemiBold, style = MaterialTheme.typography.bodyMedium, color = MaterialTheme.colorScheme.onSurface)
                                Text(a, style = MaterialTheme.typography.bodySmall, color = MaterialTheme.colorScheme.onSurfaceVariant)
                            }
                            Divider(color = MaterialTheme.colorScheme.outline.copy(alpha = 0.3f))
                        }
                    }
                }
            }

            // Related Tools
            val related = ToolCatalog.allTools.filter { it.category == tool.category && it.slug != tool.slug }.take(4)
            if (related.isNotEmpty()) {
                Text("Related Tools in ${tool.category.title}", style = MaterialTheme.typography.titleMedium, fontWeight = FontWeight.Bold, color = MaterialTheme.colorScheme.onSurface)
                FlowRow(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(8.dp),
                    verticalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    related.forEach { rel ->
                        SuggestionChip(
                            onClick = { onSelectTool(rel) },
                            label = { Text(rel.name) }
                        )
                    }
                }
            }

            // Author & Trust Disclaimer
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .clip(RoundedCornerShape(8.dp))
                    .background(MaterialTheme.colorScheme.surfaceVariant)
                    .border(1.dp, MaterialTheme.colorScheme.outline, RoundedCornerShape(8.dp))
                    .padding(12.dp)
            ) {
                Text(
                    text = "Disclaimer: Calculations provided by Nepal Utility Hub are for informational and planning purposes only. Please verify important financial or academic figures with official institutions or bank officers.",
                    style = MaterialTheme.typography.labelSmall,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
            }

        }
    }
}
