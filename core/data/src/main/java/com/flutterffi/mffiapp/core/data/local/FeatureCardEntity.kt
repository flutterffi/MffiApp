package com.flutterffi.mffiapp.core.data.local

import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.ColumnInfo

@Entity(tableName = "feature_cards")
data class FeatureCardEntity(
    @PrimaryKey(autoGenerate = true) val id: Long = 0,
    val module: String,
    val title: String,
    val description: String,
    @ColumnInfo(name = "image_url") val imageUrl: String? = null,
)
