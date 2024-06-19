package com.example.tendable.test.db.dbmodels

import androidx.room.Entity

@Entity(tableName = "Question", primaryKeys = ["inspectionId","categoryId", "id"])
data class QuestionDB(
    var inspectionId : Int,
    var categoryId : Int,
    var id : Int,
    var name : String,
    var selectedAnswerChoiceId : Int?
)
