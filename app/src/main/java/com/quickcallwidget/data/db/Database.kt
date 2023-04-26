package com.quickcallwidget.data.db


import androidx.room.Database
import androidx.room.RoomDatabase

@Database(entities = [TestDB::class], version = 1, exportSchema = true)
abstract class Database: RoomDatabase() {
    abstract fun dao(): MyDao
}