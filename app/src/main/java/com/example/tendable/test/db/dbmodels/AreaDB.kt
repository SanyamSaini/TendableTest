package com.example.tendable.test.db.dbmodels

import androidx.room.Entity

@Entity(tableName = "Area", primaryKeys = ["inspectionId","id"])
data class AreaDB(
    var inspectionId : Int,
    var id : Int,
    var name : String
)
