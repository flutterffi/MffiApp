package com.flutterffi.mffiapp.core.data.local

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(
    entities = [FeatureCardEntity::class],
    version = 1,
    exportSchema = false,
)
abstract class MffiDatabase : RoomDatabase() {
    abstract fun featureCardDao(): FeatureCardDao
}
