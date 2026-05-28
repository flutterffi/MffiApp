package com.flutterffi.mffiapp.core.data.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface FeatureCardDao {
    @Query("SELECT * FROM feature_cards WHERE module = :module ORDER BY id ASC")
    fun observeByModule(module: String): Flow<List<FeatureCardEntity>>

    @Query("SELECT COUNT(*) FROM feature_cards")
    suspend fun count(): Int

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(items: List<FeatureCardEntity>)
}
