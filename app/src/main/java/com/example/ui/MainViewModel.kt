package com.example.ui

import android.app.Application
import android.content.Context
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.data.db.AppDatabase
import com.example.data.db.CalculationHistoryEntity
import com.example.data.model.ToolCatalog
import com.example.data.model.ToolItem
import com.example.data.repository.UtilityRepository
import com.example.ui.theme.ThemeMode
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

enum class MainTab {
    HOME,
    TOOLS,
    TODAY,
    SAVED,
    ABOUT
}

class MainViewModel(application: Application) : AndroidViewModel(application) {

    private val repository: UtilityRepository
    private val prefs = application.getSharedPreferences("nepal_utility_prefs", Context.MODE_PRIVATE)

    private val _currentTab = MutableStateFlow(MainTab.HOME)
    val currentTab: StateFlow<MainTab> = _currentTab.asStateFlow()

    private val _selectedTool = MutableStateFlow<ToolItem?>(null)
    val selectedTool: StateFlow<ToolItem?> = _selectedTool.asStateFlow()

    private val _themeMode = MutableStateFlow(loadInitialTheme())
    val themeMode: StateFlow<ThemeMode> = _themeMode.asStateFlow()

    val favoriteSlugs: StateFlow<List<String>>
    val calculationHistory: StateFlow<List<CalculationHistoryEntity>>

    init {
        val db = AppDatabase.getDatabase(application)
        repository = UtilityRepository(db.appDao())

        favoriteSlugs = repository.favoriteSlugs.stateIn(
            viewModelScope,
            SharingStarted.WhileSubscribed(5000),
            listOf("emi-calculator", "nepali-date-converter", "gpa-calculator", "vat-calculator")
        )

        calculationHistory = repository.calculationHistory.stateIn(
            viewModelScope,
            SharingStarted.WhileSubscribed(5000),
            emptyList()
        )
    }

    private fun loadInitialTheme(): ThemeMode {
        val saved = prefs.getString("app_theme_mode", ThemeMode.SYSTEM.name)
        return try {
            ThemeMode.valueOf(saved ?: ThemeMode.SYSTEM.name)
        } catch (_: Exception) {
            ThemeMode.SYSTEM
        }
    }

    fun setThemeMode(mode: ThemeMode) {
        _themeMode.value = mode
        prefs.edit().putString("app_theme_mode", mode.name).apply()
    }

    fun toggleTheme(isCurrentlyDark: Boolean) {
        val nextMode = if (isCurrentlyDark) ThemeMode.LIGHT else ThemeMode.DARK
        setThemeMode(nextMode)
    }

    fun setTab(tab: MainTab) {
        _currentTab.value = tab
        _selectedTool.value = null
    }

    fun selectTool(tool: ToolItem) {
        _selectedTool.value = tool
    }

    fun clearSelectedTool() {
        _selectedTool.value = null
    }

    fun toggleFavorite(slug: String) {
        viewModelScope.launch {
            val isFav = favoriteSlugs.value.contains(slug)
            repository.toggleFavorite(slug, isFav)
        }
    }

    fun recordHistory(tool: ToolItem, summary: String) {
        viewModelScope.launch {
            repository.recordHistory(tool.slug, tool.name, summary)
        }
    }

    fun clearHistory() {
        viewModelScope.launch {
            repository.clearHistory()
        }
    }
}

