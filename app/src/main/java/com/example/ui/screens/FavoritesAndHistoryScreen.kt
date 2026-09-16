package com.example.ui.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Bookmark
import androidx.compose.material.icons.filled.DeleteSweep
import androidx.compose.material.icons.filled.History
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Tab
import androidx.compose.material3.TabRow
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.example.data.db.CalculationHistoryEntity
import com.example.data.model.ToolCatalog
import com.example.data.model.ToolItem

@Composable
fun FavoritesAndHistoryScreen(
    favoriteSlugs: List<String>,
    historyList: List<CalculationHistoryEntity>,
    onSelectTool: (ToolItem) -> Unit,
    onToggleFavorite: (String) -> Unit,
    onClearHistory: () -> Unit,
    modifier: Modifier = Modifier
) {
    var selectedTab by remember { mutableIntStateOf(0) }
    val favoriteTools = remember(favoriteSlugs) {
        ToolCatalog.allTools.filter { favoriteSlugs.contains(it.slug) }
    }

    Column(modifier = modifier.fillMaxSize()) {
        TabRow(selectedTabIndex = selectedTab) {
            Tab(
                selected = selectedTab == 0,
                onClick = { selectedTab = 0 },
                text = { Text("Saved Tools (${favoriteTools.size})") },
                icon = { Icon(Icons.Default.Bookmark, contentDescription = "Favorites") }
            )
            Tab(
                selected = selectedTab == 1,
                onClick = { selectedTab = 1 },
                text = { Text("Recent History (${historyList.size})") },
                icon = { Icon(Icons.Default.History, contentDescription = "History") }
            )
        }

        if (selectedTab == 0) {
            if (favoriteTools.isEmpty()) {
                Box(
                    modifier = Modifier.fillMaxSize().padding(32.dp),
                    contentAlignment = Alignment.Center
                ) {
                    Column(horizontalAlignment = Alignment.CenterHorizontally, verticalArrangement = Arrangement.spacedBy(8.dp)) {
                        Icon(Icons.Default.Bookmark, contentDescription = "No favorites", tint = MaterialTheme.colorScheme.onSurfaceVariant)
                        Text("No saved tools yet", style = MaterialTheme.typography.titleMedium, fontWeight = FontWeight.Bold, color = MaterialTheme.colorScheme.onSurface)
                        Text(
                            "Tap the bookmark icon on any calculator to pin it here for instant one-tap access.",
                            style = MaterialTheme.typography.bodySmall,
                            color = MaterialTheme.colorScheme.onSurfaceVariant
                        )
                    }
                }
            } else {
                LazyColumn(
                    modifier = Modifier.fillMaxSize().padding(16.dp),
                    verticalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    items(favoriteTools) { tool ->
                        ToolCardItem(
                            tool = tool,
                            isFavorite = true,
                            onToggleFavorite = { onToggleFavorite(tool.slug) },
                            onClick = { onSelectTool(tool) }
                        )
                    }
                }
            }
        } else {
            Column(modifier = Modifier.fillMaxSize().padding(16.dp)) {
                if (historyList.isNotEmpty()) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text("Past Calculations", style = MaterialTheme.typography.titleSmall, fontWeight = FontWeight.Bold, color = MaterialTheme.colorScheme.onSurface)
                        OutlinedButton(
                            onClick = onClearHistory,
                            contentPadding = ButtonDefaults.ContentPadding
                        ) {
                            Icon(Icons.Default.DeleteSweep, contentDescription = "Clear", tint = Color(0xFFDC2626))
                            Spacer(modifier = Modifier.padding(2.dp))
                            Text("Clear All", color = Color(0xFFDC2626))
                        }
                    }
                    Spacer(modifier = Modifier.height(8.dp))
                }

                if (historyList.isEmpty()) {
                    Box(
                        modifier = Modifier.fillMaxSize().padding(32.dp),
                        contentAlignment = Alignment.Center
                    ) {
                        Column(horizontalAlignment = Alignment.CenterHorizontally, verticalArrangement = Arrangement.spacedBy(8.dp)) {
                            Icon(Icons.Default.History, contentDescription = "No history", tint = MaterialTheme.colorScheme.onSurfaceVariant)
                            Text("No calculation history", style = MaterialTheme.typography.titleMedium, fontWeight = FontWeight.Bold, color = MaterialTheme.colorScheme.onSurface)
                            Text("Your recent calculations will be recorded locally for easy reference.", style = MaterialTheme.typography.bodySmall, color = MaterialTheme.colorScheme.onSurfaceVariant)
                        }
                    }
                } else {
                    LazyColumn(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                        items(historyList) { item ->
                            val tool = ToolCatalog.allTools.find { it.slug == item.toolSlug }
                            ToolCardItem(
                                tool = tool ?: ToolItem(item.toolSlug, item.toolName, com.example.data.model.ToolCategory.FINANCE, item.summaryText),
                                isFavorite = favoriteSlugs.contains(item.toolSlug),
                                onToggleFavorite = { onToggleFavorite(item.toolSlug) },
                                onClick = { tool?.let { onSelectTool(it) } }
                            )
                        }
                    }
                }
            }
        }
    }
}
