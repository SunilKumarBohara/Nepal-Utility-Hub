package com.example.ui

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.background
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Apps
import androidx.compose.material.icons.filled.Bookmark
import androidx.compose.material.icons.filled.CalendarToday
import androidx.compose.material.icons.filled.DarkMode
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.LightMode
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.data.service.BikramSambatConverter
import com.example.ui.screens.AboutAndLegalScreen
import com.example.ui.screens.CalculatorDetailScreen
import com.example.ui.screens.FavoritesAndHistoryScreen
import com.example.ui.screens.HomeScreen
import com.example.ui.screens.TodayInNepalScreen
import com.example.ui.screens.ToolsDirectoryScreen
import com.example.ui.theme.ThemeMode

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun NepalUtilityHubApp(
    viewModel: MainViewModel = viewModel()
) {
    val currentTab by viewModel.currentTab.collectAsState()
    val selectedTool by viewModel.selectedTool.collectAsState()
    val favoriteSlugs by viewModel.favoriteSlugs.collectAsState()
    val historyList by viewModel.calculationHistory.collectAsState()
    val themeMode by viewModel.themeMode.collectAsState()
    val systemInDark = isSystemInDarkTheme()
    val isDark = when (themeMode) {
        ThemeMode.LIGHT -> false
        ThemeMode.DARK -> true
        ThemeMode.SYSTEM -> systemInDark
    }
    val currentBs = remember { BikramSambatConverter.getCurrentBsDate() }

    // Intercept back button if currently viewing a calculator detail
    BackHandler(enabled = selectedTool != null) {
        viewModel.clearSelectedTool()
    }

    if (selectedTool != null) {
        val tool = selectedTool!!
        CalculatorDetailScreen(
            tool = tool,
            isFavorite = favoriteSlugs.contains(tool.slug),
            isDark = isDark,
            onToggleFavorite = { viewModel.toggleFavorite(tool.slug) },
            onToggleTheme = { viewModel.toggleTheme(isDark) },
            onBack = { viewModel.clearSelectedTool() },
            onSelectTool = { newTool -> viewModel.selectTool(newTool) }
        )
    } else {
        Scaffold(
            topBar = {
                TopAppBar(
                    title = {
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Box(
                                modifier = Modifier
                                    .clip(RoundedCornerShape(6.dp))
                                    .background(Color(0xFFDC2626))
                                    .padding(horizontal = 8.dp, vertical = 2.dp)
                            ) {
                                Text("NUH", color = Color.White, fontWeight = FontWeight.Bold, fontSize = 12.sp)
                            }
                            Text(
                                text = "Nepal Utility Hub",
                                style = MaterialTheme.typography.titleMedium,
                                fontWeight = FontWeight.Bold,
                                color = MaterialTheme.colorScheme.onSurface,
                                modifier = Modifier.padding(start = 10.dp)
                            )
                        }
                    },
                    actions = {
                        Box(
                            modifier = Modifier
                                .clip(RoundedCornerShape(8.dp))
                                .background(MaterialTheme.colorScheme.primaryContainer)
                                .padding(horizontal = 8.dp, vertical = 4.dp)
                        ) {
                            Text(
                                text = "${currentBs.day} ${currentBs.monthNameNp}",
                                style = MaterialTheme.typography.labelSmall,
                                color = MaterialTheme.colorScheme.onPrimaryContainer,
                                fontWeight = FontWeight.Bold
                            )
                        }

                        IconButton(
                            onClick = { viewModel.toggleTheme(isDark) },
                            modifier = Modifier.testTag("theme_toggle_button")
                        ) {
                            Icon(
                                imageVector = if (isDark) Icons.Default.LightMode else Icons.Default.DarkMode,
                                contentDescription = if (isDark) "Switch to Light Mode" else "Switch to Dark Mode",
                                tint = if (isDark) Color(0xFFFBBF24) else MaterialTheme.colorScheme.primary
                            )
                        }
                    },
                    colors = TopAppBarDefaults.topAppBarColors(
                        containerColor = MaterialTheme.colorScheme.surface
                    )
                )
            },
            bottomBar = {
                NavigationBar(
                    containerColor = MaterialTheme.colorScheme.surface,
                    tonalElevation = 8.dp,
                    modifier = Modifier.testTag("bottom_nav_bar")
                ) {
                    NavigationBarItem(
                        selected = currentTab == MainTab.HOME,
                        onClick = { viewModel.setTab(MainTab.HOME) },
                        icon = { Icon(Icons.Default.Home, contentDescription = "Home") },
                        label = { Text("Home") },
                        colors = NavigationBarItemDefaults.colors(
                            selectedIconColor = MaterialTheme.colorScheme.primary,
                            selectedTextColor = MaterialTheme.colorScheme.primary,
                            indicatorColor = MaterialTheme.colorScheme.primaryContainer
                        )
                    )

                    NavigationBarItem(
                        selected = currentTab == MainTab.TOOLS,
                        onClick = { viewModel.setTab(MainTab.TOOLS) },
                        icon = { Icon(Icons.Default.Apps, contentDescription = "Tools") },
                        label = { Text("Tools") },
                        colors = NavigationBarItemDefaults.colors(
                            selectedIconColor = MaterialTheme.colorScheme.primary,
                            selectedTextColor = MaterialTheme.colorScheme.primary,
                            indicatorColor = MaterialTheme.colorScheme.primaryContainer
                        )
                    )

                    NavigationBarItem(
                        selected = currentTab == MainTab.TODAY,
                        onClick = { viewModel.setTab(MainTab.TODAY) },
                        icon = { Icon(Icons.Default.CalendarToday, contentDescription = "Today") },
                        label = { Text("Today") },
                        colors = NavigationBarItemDefaults.colors(
                            selectedIconColor = Color(0xFFDC2626),
                            selectedTextColor = Color(0xFFDC2626),
                            indicatorColor = MaterialTheme.colorScheme.secondaryContainer
                        )
                    )

                    NavigationBarItem(
                        selected = currentTab == MainTab.SAVED,
                        onClick = { viewModel.setTab(MainTab.SAVED) },
                        icon = { Icon(Icons.Default.Bookmark, contentDescription = "Saved") },
                        label = { Text("Saved") },
                        colors = NavigationBarItemDefaults.colors(
                            selectedIconColor = MaterialTheme.colorScheme.primary,
                            selectedTextColor = MaterialTheme.colorScheme.primary,
                            indicatorColor = MaterialTheme.colorScheme.primaryContainer
                        )
                    )

                    NavigationBarItem(
                        selected = currentTab == MainTab.ABOUT,
                        onClick = { viewModel.setTab(MainTab.ABOUT) },
                        icon = { Icon(Icons.Default.Info, contentDescription = "About") },
                        label = { Text("About") },
                        colors = NavigationBarItemDefaults.colors(
                            selectedIconColor = MaterialTheme.colorScheme.primary,
                            selectedTextColor = MaterialTheme.colorScheme.primary,
                            indicatorColor = MaterialTheme.colorScheme.primaryContainer
                        )
                    )
                }
            }
        ) { innerPadding ->
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(innerPadding)
            ) {
                when (currentTab) {
                    MainTab.HOME -> HomeScreen(
                        onSelectTool = { tool -> viewModel.selectTool(tool) },
                        onNavigateToday = { viewModel.setTab(MainTab.TODAY) },
                        favoriteSlugs = favoriteSlugs,
                        onToggleFavorite = { slug -> viewModel.toggleFavorite(slug) }
                    )
                    MainTab.TOOLS -> ToolsDirectoryScreen(
                        onSelectTool = { tool -> viewModel.selectTool(tool) },
                        favoriteSlugs = favoriteSlugs,
                        onToggleFavorite = { slug -> viewModel.toggleFavorite(slug) }
                    )
                    MainTab.TODAY -> TodayInNepalScreen()
                    MainTab.SAVED -> FavoritesAndHistoryScreen(
                        favoriteSlugs = favoriteSlugs,
                        historyList = historyList,
                        onSelectTool = { tool -> viewModel.selectTool(tool) },
                        onToggleFavorite = { slug -> viewModel.toggleFavorite(slug) },
                        onClearHistory = { viewModel.clearHistory() }
                    )
                    MainTab.ABOUT -> AboutAndLegalScreen(
                        currentThemeMode = themeMode,
                        onSelectThemeMode = { viewModel.setThemeMode(it) }
                    )
                }
            }
        }
    }
}
