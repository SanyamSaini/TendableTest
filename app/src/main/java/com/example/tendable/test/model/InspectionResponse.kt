package com.example.tendable.test.model

import com.google.gson.annotations.SerializedName

class InspectionResponse (
    @SerializedName("inspection")
    var inspectionModel : InspectionModel? = null
)

class InspectionModel (
    @SerializedName("id")
    var id : Int? = null,
    @SerializedName("area")
    var area : AreaModel? = null,
    @SerializedName("inspectionType")
    var inspectionType : InspectionType? = null,
    @SerializedName("survey")
    var survey : SurveyModel? = null
)

class AreaModel (
    @SerializedName("id")
    var id : Int? = null,
    @SerializedName("name")
    var name : String? = null
)

class InspectionType (
    @SerializedName("id")
    var id : Int? = null,
    @SerializedName("name")
    var name : String? = null,
    @SerializedName("access")
    var access : String? = null
)

class SurveyModel (
    @SerializedName("id")
    var id : Int? = null,
    @SerializedName("categories")
    var categories : ArrayList<CategoryModel>? = null

)

class CategoryModel (
    @SerializedName("id")
    var id : Int? = null,
    @SerializedName("name")
    var name : String? = null,
    @SerializedName("questions")
    var questions : ArrayList<QuestionModel>? = null
)

class QuestionModel (
    @SerializedName("id")
    var id : Int? = null,
    @SerializedName("name")
    var name : String? = null,
    @SerializedName("selectedAnswerChoiceId")
    var selectedAnswerChoiceId : Int? = null,
    @SerializedName("answerChoices")
    var answerChoices : ArrayList<AnswerChoiceModel>? = null
)

data class AnswerChoiceModel (
    @SerializedName("id")
    var id : Int? = null,
    @SerializedName("name")
    var name : String? = null,
    @SerializedName("score")
    var score : Double? = null
)

/*
* Data class for Inspection
* {
  "inspection": {
    "id": 1,
    "inspectionType": {
      "id": 1,
      "name": "Clinical",
      "access": "write"
    },
    "area": {
      "id": 1,
      "name": "Emergency ICU"
    },
    "survey": {
      "id": 1,
      "categories": [
        {
          "id": 1,
          "name": "Drugs",
          "questions": [
            {
              "id": 1,
              "name": "Is the drugs trolley locked?",
              "answerChoices": [
                {
                  "id": 1,
                  "name": "Yes",
                  "score": 1.0
                },
                {
                  "id": 2,
                  "name": "No",
                  "score": 0.0
                }
              ],
              "selectedAnswerChoiceId": 1
            }
          ]
        }
      ]
    }
  }
}
**/