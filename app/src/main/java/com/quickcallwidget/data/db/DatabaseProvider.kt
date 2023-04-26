package com.quickcallwidget.data.db

import android.content.Context
import androidx.room.Room

class DatabaseProvider(context: Context) {
    private val database = Room.databaseBuilder(context, Database::class.java, "new_db").build()
    val myDao = database.dao()
}
