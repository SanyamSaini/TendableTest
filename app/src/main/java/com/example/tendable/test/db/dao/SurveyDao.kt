package com.example.tendable.test.db.dao

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.tendable.test.db.dbmodels.AreaDB
import com.example.tendable.test.db.dbmodels.SurveyDB

@Dao
interface SurveyDao {
    @Query("select * from Survey")
    fun getSurvey(): SurveyDB

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(survey: SurveyDB)

    @Query("delete from Survey")
    suspend fun deleteAll()
}