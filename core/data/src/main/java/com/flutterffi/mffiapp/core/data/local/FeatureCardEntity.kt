package com.flutterffi.mffiapp.core.data.local

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "feature_cards")
data class FeatureCardEntity(
    @PrimaryKey(autoGenerate = true) val id: Long = 0,
    val module: String,
    val title: String,
    val description: String,
)
