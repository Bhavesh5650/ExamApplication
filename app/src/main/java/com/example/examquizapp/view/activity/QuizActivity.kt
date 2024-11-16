package com.example.examquizapp.view.activity

import android.os.Bundle
import android.util.Log
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.examquizapp.R
import com.example.examquizapp.data.helper.QuizHelper
import com.example.examquizapp.data.model.QuestionModel
import com.example.examquizapp.data.model.QuizModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class QuizActivity : AppCompatActivity() {

    val tempList = mutableListOf<QuestionModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_quiz)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        val category = intent.getStringExtra("category")

        val quizHelper = QuizHelper()
        GlobalScope.launch {
            withContext(Dispatchers.Main)
            {
                val list = quizHelper.quizApiCall(category = category.toString())
                Log.d("List", "onCreate ---------------- $list ")
                for(i in list?.results!!)
                {
                    val optionList = i!!.incorrectAnswers as MutableList
                    optionList.add(i.correctAnswer)
                    optionList.shuffle()
                    tempList.add(QuestionModel(i.question!!,i.correctAnswer!!,optionList))
                    Log.d("TAG", "onCreate =========$tempList ")

                }

            }
        }
    }
}