package com.example.tendable.test.interfaces

import com.example.tendable.test.model.AnswerChoiceModel

fun interface AnswerClickListener {
    fun answerClick(answerChoice: AnswerChoiceModel, questionId : Int)
}