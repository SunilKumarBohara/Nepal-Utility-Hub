package com.example.data.model

data class ToolItem(
    val slug: String,
    val name: String,
    val category: ToolCategory,
    val description: String,
    val isPopular: Boolean = false,
    val isRecentlyAdded: Boolean = false,
    val keywords: List<String> = emptyList(),
    val formulaExplanation: String = "",
    val formulaFormula: String = "",
    val workedExample: String = "",
    val faqs: List<Pair<String, String>> = emptyList()
)

enum class ToolCategory(val title: String, val badge: String) {
    FINANCE("Finance", "NPR"),
    STUDENT("Student", "GPA"),
    HEALTH("Health & Lifestyle", "Wellness"),
    NEPAL("Nepal Specific", "Nepal"),
    DATE_TIME("Date & Time", "B.S."),
    MATH("Math", "123"),
    CONVERSION("Conversion", "Units"),
    BUSINESS("Business & Shop", "VAT")
}
