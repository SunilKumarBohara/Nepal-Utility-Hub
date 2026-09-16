package com.example.data.db

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "favorite_tools")
data class FavoriteToolEntity(
    @PrimaryKey val slug: String,
    val addedAt: Long = System.currentTimeMillis()
)

@Entity(tableName = "calculation_history")
data class CalculationHistoryEntity(
    @PrimaryKey(autoGenerate = true) val id: Long = 0,
    val toolSlug: String,
    val toolName: String,
    val summaryText: String,
    val timestamp: Long = System.currentTimeMillis()
)
