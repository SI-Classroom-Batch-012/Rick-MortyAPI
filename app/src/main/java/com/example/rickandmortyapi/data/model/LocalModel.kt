package com.example.rickandmortyapi.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "local_table")
data class LocalModel(
    @PrimaryKey
    val localID: Int,
    val name: String,
    val species: String,
    val gender: String,
    val image: String
)