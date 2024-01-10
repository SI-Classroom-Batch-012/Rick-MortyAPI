package com.example.rickandmortyapi.data.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.rickandmortyapi.data.model.LocalModel

@Dao
interface LocalDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertItem(item: LocalModel)

    @Query("SELECT * FROM local_table")
    suspend fun getAll(): List<LocalModel>

    @Query("DELETE FROM local_table WHERE localID = :modelID")
    fun deleteByID(modelID: Int)
}