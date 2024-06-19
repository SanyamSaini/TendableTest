package com.example.tendable.test.db.dbmodels

import androidx.room.Entity

@Entity(tableName = "AnswerChoices", primaryKeys = ["inspectionId","categoryId", "questionId" ,"id"])
data class AnswerChoicesDB(
    var inspectionId : Int,
    var categoryId : Int,
    var questionId : Int,
    var id : Int,
    var name : String,
    var score : Double
)
