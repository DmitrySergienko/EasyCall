package com.quickcallwidget.data.db

import androidx.room.*
import kotlinx.coroutines.flow.Flow


@Dao
interface MyDao {
    @Query("SELECT * FROM new_db")
    fun readAll(): Flow<List<TestDB>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertItem(item: TestDB)

    @Delete
    fun deleteItem(item: TestDB)

    @Query("DELETE FROM new_db")
    fun deleteAll()

}