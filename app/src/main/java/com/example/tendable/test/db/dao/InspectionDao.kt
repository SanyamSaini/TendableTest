package com.example.tendable.test.db.dao

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.tendable.test.db.dbmodels.AreaDB
import com.example.tendable.test.db.dbmodels.InspectionDB

@Dao
interface InspectionDao {
    @Query("select * from Inspection")
    fun getInspection(): InspectionDB

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(inspection: InspectionDB)

    @Query("delete from Inspection")
    suspend fun deleteAll()
}