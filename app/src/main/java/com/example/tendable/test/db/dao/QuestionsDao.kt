package com.example.tendable.test.db.dao

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.tendable.test.db.dbmodels.AnswerChoicesDB
import com.example.tendable.test.db.dbmodels.AreaDB
import com.example.tendable.test.db.dbmodels.QuestionDB

@Dao
interface QuestionsDao {
    @Query("select * from Question")
    fun getAllQuestions(): List<QuestionDB>

    @Query("select * from Question where inspectionId = :inspectionId and categoryId = :categoryId")
    fun getQuestionsById(inspectionId : Int, categoryId : Int): List<QuestionDB>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(question: QuestionDB)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(questions: List<QuestionDB>)

    @Query("delete from Question")
    suspend fun deleteAll()
}