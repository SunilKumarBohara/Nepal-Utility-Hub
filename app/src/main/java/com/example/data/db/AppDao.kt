package com.example.data.db

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface AppDao {
    @Query("SELECT slug FROM favorite_tools")
    fun getAllFavorites(): Flow<List<String>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addFavorite(entity: FavoriteToolEntity)

    @Query("DELETE FROM favorite_tools WHERE slug = :slug")
    suspend fun removeFavorite(slug: String)

    @Query("SELECT EXISTS(SELECT 1 FROM favorite_tools WHERE slug = :slug)")
    fun isFavorite(slug: String): Flow<Boolean>

    @Query("SELECT * FROM calculation_history ORDER BY timestamp DESC LIMIT 30")
    fun getRecentHistory(): Flow<List<CalculationHistoryEntity>>

    @Insert
    suspend fun insertHistory(history: CalculationHistoryEntity)

    @Query("DELETE FROM calculation_history")
    suspend fun clearHistory()
}
