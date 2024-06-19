package com.example.tendable.test.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.example.tendable.test.db.dao.AnswerChoicesDao
import com.example.tendable.test.db.dao.AreaDao
import com.example.tendable.test.db.dao.CategoryDao
import com.example.tendable.test.db.dao.InspectionDao
import com.example.tendable.test.db.dao.QuestionsDao
import com.example.tendable.test.db.dao.SurveyDao
import com.example.tendable.test.db.dbmodels.AnswerChoicesDB
import com.example.tendable.test.db.dbmodels.AreaDB
import com.example.tendable.test.db.dbmodels.CategoryDB
import com.example.tendable.test.db.dbmodels.InspectionDB
import com.example.tendable.test.db.dbmodels.QuestionDB
import com.example.tendable.test.db.dbmodels.SurveyDB

@Database(
    entities = [AnswerChoicesDB::class, AreaDB::class,CategoryDB::class,
        InspectionDB::class, QuestionDB::class, SurveyDB::class], version = 1
)

@TypeConverters(Converters::class)
abstract class RoomDB : RoomDatabase(){
    abstract val areaDao : AreaDao
    abstract val surveyDao : SurveyDao
    abstract val categoryDao : CategoryDao
    abstract val inspectionDao : InspectionDao
    abstract val questionsDao : QuestionsDao
    abstract val answerChoicesDao : AnswerChoicesDao
}

private lateinit var INSTANCE: RoomDB

fun getDatabase(context: Context): RoomDB {
    synchronized(RoomDB::class.java) {
        if (!::INSTANCE.isInitialized) {
            INSTANCE = Room
                .databaseBuilder(
                    context.applicationContext,
                    RoomDB::class.java,
                    "RoomDb"
                )
                .build()
        }
    }
    return INSTANCE
}