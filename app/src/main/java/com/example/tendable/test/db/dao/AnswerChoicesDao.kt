package com.example.tendable.test.db.dao

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.tendable.test.db.dbmodels.AnswerChoicesDB
import com.example.tendable.test.db.dbmodels.AreaDB

@Dao
interface AnswerChoicesDao {
    @Query("select * from AnswerChoices")
    fun getAllAnswerChoices(): List<AnswerChoicesDB>

    @Query("select * from AnswerChoices where inspectionId = :inspectionId and categoryId = :categoryId and questionId = :questionId")
    fun getAnswerChoicesById(inspectionId : Int, categoryId : Int, questionId : Int): List<AnswerChoicesDB>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(answerChoices: AnswerChoicesDB)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(answerChoices: List<AnswerChoicesDB>)

    @Query("delete from AnswerChoices")
    suspend fun deleteAll()
}