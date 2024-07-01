package com.example.tendable.test.repository

import android.util.Log
import com.example.tendable.test.api.ApiResult
import com.example.tendable.test.api.Network
import com.example.tendable.test.db.RoomDB
import com.example.tendable.test.db.dbmodels.AnswerChoicesDB
import com.example.tendable.test.db.dbmodels.AreaDB
import com.example.tendable.test.db.dbmodels.CategoryDB
import com.example.tendable.test.db.dbmodels.InspectionDB
import com.example.tendable.test.db.dbmodels.QuestionDB
import com.example.tendable.test.db.dbmodels.SurveyDB
import com.example.tendable.test.model.AnswerChoiceModel
import com.example.tendable.test.model.ApiResponse
import com.example.tendable.test.model.AreaModel
import com.example.tendable.test.model.CategoryModel
import com.example.tendable.test.model.InspectionModel
import com.example.tendable.test.model.InspectionResponse
import com.example.tendable.test.model.InspectionType
import com.example.tendable.test.model.QuestionModel
import com.example.tendable.test.model.SurveyModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import retrofit2.HttpException
import java.io.IOException
import kotlin.Exception

class MainRepository(private val db : RoomDB) {
    companion object {
        private const val TAG = "MainRepository"
    }

    suspend fun getDraftedInspection(): InspectionResponse? {
        return try {
            withContext(Dispatchers.IO) {
                InspectionResponse(dbToInspection())
            }
        } catch (e : Exception) {
            e.printStackTrace()
            null
        }

    }

    private fun dbToInspection() : InspectionModel {
        val area = db.areaDao.getArea()
        val inspectionType = db.inspectionDao.getInspection()

        val categoryModel = ArrayList<CategoryModel>()
        val surveyDB = db.surveyDao.getSurvey()
        val categoryDb = db.categoryDao.getCategory()

        Log.d(TAG, "surveyDB : $surveyDB")
        Log.d(TAG, "categoryDB : $categoryDb")

        for(i in 0 until categoryDb.size) {
            val questionDb = db.questionsDao.getQuestionsById(area.inspectionId,
                categoryDb[i].id)
            Log.d(TAG, "questionDb : $questionDb")
            val questionModel = ArrayList<QuestionModel>()

            for(j in 0 until questionDb.size) {
                val answerChoicesDB = db.answerChoicesDao.getAnswerChoicesById(area.inspectionId,
                    categoryDb[i].id, questionDb[j].id)
                Log.d(TAG, "answerChoicesDB : $answerChoicesDB")
                val answerChoiceModel  = ArrayList<AnswerChoiceModel>()

                for(k in 0 until answerChoicesDB.size) {
                    val answerModel = AnswerChoiceModel(answerChoicesDB[k].id,
                        answerChoicesDB[k].name, answerChoicesDB[k].score)
                    answerChoiceModel.add(answerModel)
                }

                val question = QuestionModel(questionDb[j].id, questionDb[j].name,
                    questionDb[j].selectedAnswerChoiceId, answerChoiceModel)

                questionModel.add(question)
            }

            val category = CategoryModel(categoryDb[i].id,categoryDb[i].name,questionModel)
            categoryModel.add(category)
        }

        val surveyModel = SurveyModel(surveyDB.id, categoryModel)

        return InspectionModel(area.inspectionId,
            AreaModel(area.id, area.name),
            InspectionType(inspectionType.id, inspectionType.name,inspectionType.access),
            surveyModel
        )
    }

    suspend fun callStartInspection(): InspectionResponse {
        return withContext(Dispatchers.IO) {

            return@withContext when (val response = callStartInspectionImpl()) {
                is ApiResult.Success -> {
                    response.data
                }
                is ApiResult.Error -> {
                    InspectionResponse()
                }
            }
        }
    }

    private suspend fun callStartInspectionImpl(): ApiResult<InspectionResponse> {
        return try {
            val defaultResponse = Network.network.startInspection()
            saveInspection(defaultResponse.inspectionModel)

            ApiResult.Success(data = defaultResponse)
        } catch (e: HttpException) {
            //handles exception with the request
            ApiResult.Error(exception = e)
        } catch (e: IOException) {
            //handles no internet exception
            ApiResult.Error(exception = e)
        } catch (e : Exception) {
            ApiResult.Error(exception = e)
        }
    }

    suspend fun saveDraftInspection(inspectionModel: InspectionModel) : Boolean {
        return withContext(Dispatchers.IO) {
            return@withContext saveInspection(inspectionModel)
        }
    }

    private suspend fun saveInspection(inspectionModel: InspectionModel?) : Boolean {
        val areaDB = AreaDB(inspectionModel?.id ?: 0,
            inspectionModel?.area?.id ?: 0,
            inspectionModel?.area?.name ?: "")
        db.areaDao.insert(areaDB)

        val inspectionDB = InspectionDB(inspectionModel?.id ?: 0,
            inspectionModel?.inspectionType?.id ?: 0,
            inspectionModel?.inspectionType?.name ?: "",
            inspectionModel?.inspectionType?.access ?: "")
        db.inspectionDao.insert(inspectionDB)

        val categories = ArrayList<CategoryDB>()
        val questions = ArrayList<QuestionDB>()
        val answerChoices = ArrayList<AnswerChoicesDB>()
        for (i in 0 until inspectionModel?.survey?.categories?.size!!) {
            val category = inspectionModel.survey?.categories!![i]
            val catDb = CategoryDB(inspectionModel.id ?: 0,
                category.id ?: 0, category.name ?: "")

            for(j in 0 until category.questions?.size!!) {
                val question = category.questions!![j]
                val quesDb = QuestionDB(inspectionModel.id ?: 0,
                    category.id ?: 0,
                    question.id ?: 0,
                    question.name ?:"",
                    question.selectedAnswerChoiceId)

                for(k in 0 until question.answerChoices?.size!!) {
                    val answerChoice = question.answerChoices!![k]
                    val ansdb = AnswerChoicesDB(inspectionModel.id ?: 0,
                        category.id ?: 0,
                        question.id ?: 0,
                        answerChoice.id ?: 0,
                        answerChoice.name ?:"",
                        answerChoice.score ?: 0.0,)

                    answerChoices.add(ansdb)
                }
                questions.add(quesDb)
            }
            categories.add(catDb)
        }

        db.surveyDao.insert(SurveyDB(inspectionModel.id ?: 0, inspectionModel.survey?.id ?: 0))
        db.categoryDao.insertAll(categories)
        db.questionsDao.insertAll(questions)
        db.answerChoicesDao.insertAll(answerChoices)

        return true

    }

    suspend fun submitInspection(inspectionResponse: InspectionResponse): Boolean {
        return withContext(Dispatchers.IO) {

            return@withContext when (submitInspectionImpl(inspectionResponse)) {
                is ApiResult.Success -> {
                    true
                }
                is ApiResult.Error -> {
                    false
                }
            }
        }
    }

    private suspend fun submitInspectionImpl(inspectionResponse: InspectionResponse): ApiResult<ApiResponse> {
        return try {
            val defaultResponse = Network.network.submitInspection(inspectionResponse)

            Log.d(TAG, "submitInspectionImpl : $defaultResponse")

            if (defaultResponse.error.isEmpty()) {
                deleteDb()
                ApiResult.Success(data = defaultResponse)
            } else {
                ApiResult.Error(Exception(), defaultResponse)
            }
        } catch (e: HttpException) {
            //handles exception with the request
            e.printStackTrace()
            ApiResult.Error(exception = e)
        } catch (e: IOException) {
            //handles no internet exception
            e.printStackTrace()
            ApiResult.Error(exception = e)
        } catch (e : Exception) {
            e.printStackTrace()
            ApiResult.Error(exception = e)
        }
    }

    private suspend fun deleteDb() {
        db.areaDao.deleteAll()
        db.categoryDao.deleteAll()
        db.surveyDao.deleteAll()
        db.inspectionDao.deleteAll()
        db.answerChoicesDao.deleteAll()
        db.inspectionDao.deleteAll()
    }
}
