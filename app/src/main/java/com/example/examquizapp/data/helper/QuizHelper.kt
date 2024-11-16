package com.example.examquizapp.data.helper

import android.util.Log
import com.example.examquizapp.data.model.QuizModel
import com.example.examquizapp.data.network.ApiClient.Companion.getApi
import com.example.examquizapp.data.network.ApiInterface
import retrofit2.Call
import retrofit2.Response
import retrofit2.awaitResponse

class QuizHelper {

    suspend fun quizApiCall(category: String) : QuizModel?
    {
        val apiInterface = getApi().create(ApiInterface::class.java)
        val res = apiInterface.getQuiz(category = category).awaitResponse()

        if(res.isSuccessful)
        {
            Log.d("Response", "quizApiCall ========= ${res.body()!!.results} ")
            return res.body()
        }
        return null
    }
}