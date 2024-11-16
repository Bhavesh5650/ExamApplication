package com.example.examquizapp.data.model

import com.google.gson.annotations.SerializedName

data class QuizModel(

	@field:SerializedName("response_code")
	val responseCode: Int? = null,

	@field:SerializedName("results")
	val results: List<ResultsItem?>? = null
)

data class ResultsItem(

	@field:SerializedName("difficulty")
	val difficulty: String? = null,

	@field:SerializedName("question")
	val question: String? = null,

	@field:SerializedName("correct_answer")
	val correctAnswer: String? = null,

	@field:SerializedName("incorrect_answers")
	val incorrectAnswers: List<String?>? = null,

	@field:SerializedName("type")
	val type: String? = null,

	@field:SerializedName("category")
	val category: String? = null
)
