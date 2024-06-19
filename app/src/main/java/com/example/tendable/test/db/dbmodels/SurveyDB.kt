package com.example.tendable.test.db.dbmodels

import androidx.room.Entity

@Entity(tableName = "Survey", primaryKeys = ["inspectionId","id"])
data class SurveyDB(
    var inspectionId : Int,
    var id : Int,
)
