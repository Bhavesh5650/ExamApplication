package com.example.examquizapp.domain.repository

import com.example.examquizapp.data.helper.QuizHelper

class DataRepository {

    companion object {
        val repository = DataRepository()
    }

    private val quizHelper = QuizHelper()
    suspend fun quizApiCall(category: String) = quizHelper.quizApiCall(category = category)
}