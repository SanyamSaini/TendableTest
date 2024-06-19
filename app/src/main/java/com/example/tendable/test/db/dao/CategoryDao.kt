package com.example.tendable.test.db.dao

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.tendable.test.db.dbmodels.AnswerChoicesDB
import com.example.tendable.test.db.dbmodels.AreaDB
import com.example.tendable.test.db.dbmodels.CategoryDB

@Dao
interface CategoryDao {
    @Query("select * from Category")
    fun getCategory(): List<CategoryDB>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(category: CategoryDB)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(categories: List<CategoryDB>)

    @Query("delete from Category")
    suspend fun deleteAll()
}