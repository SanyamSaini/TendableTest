package com.example.tendable.test.db.dbmodels

import androidx.room.Entity

@Entity(tableName = "Category", primaryKeys = ["inspectionId","id"])
data class CategoryDB(
    var inspectionId : Int,
    var id : Int,
    var name : String
)
