package com.example.examquizapp.data.network

import com.example.examquizapp.data.model.QuizModel
import com.example.examquizapp.view.activity.QuizActivity
import retrofit2.Call
import retrofit2.Callback
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiInterface {

    @GET("api.php")
    fun getQuiz(
        @Query("amount") amount:String="10",
        @Query("category") category:String
    ) : Call<QuizModel>
}