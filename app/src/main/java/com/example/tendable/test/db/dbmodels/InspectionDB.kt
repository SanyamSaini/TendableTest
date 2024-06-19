package com.example.tendable.test.db.dbmodels

import androidx.room.Entity

@Entity(tableName = "Inspection", primaryKeys = ["inspectionId","id"])
data class InspectionDB(
    var inspectionId : Int,
    var id : Int,
    var name : String,
    var access : String
)
