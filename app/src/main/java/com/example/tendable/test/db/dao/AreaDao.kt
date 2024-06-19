package com.example.tendable.test.db.dao

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.tendable.test.db.dbmodels.AreaDB

@Dao
interface AreaDao {
    @Query("select * from Area")
    fun getArea(): AreaDB

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(area: AreaDB)

    @Query("delete from Area")
    suspend fun deleteAll()
}