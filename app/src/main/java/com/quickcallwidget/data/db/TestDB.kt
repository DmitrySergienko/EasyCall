package com.quickcallwidget.data.db

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "new_db")
data class TestDB(
    @PrimaryKey(autoGenerate = true)
    val id: Int =0,
    val name: String,
    val phone: String
)
