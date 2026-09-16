package com.example.data.repository

import com.example.data.db.AppDao
import com.example.data.db.CalculationHistoryEntity
import com.example.data.db.FavoriteToolEntity
import kotlinx.coroutines.flow.Flow

class UtilityRepository(private val appDao: AppDao) {

    val favoriteSlugs: Flow<List<String>> = appDao.getAllFavorites()
    val calculationHistory: Flow<List<CalculationHistoryEntity>> = appDao.getRecentHistory()

    suspend fun toggleFavorite(slug: String, isFav: Boolean) {
        if (isFav) {
            appDao.removeFavorite(slug)
        } else {
            appDao.addFavorite(FavoriteToolEntity(slug = slug))
        }
    }

    suspend fun recordHistory(toolSlug: String, toolName: String, summary: String) {
        appDao.insertHistory(
            CalculationHistoryEntity(
                toolSlug = toolSlug,
                toolName = toolName,
                summaryText = summary
            )
        )
    }

    suspend fun clearHistory() {
        appDao.clearHistory()
    }
}
