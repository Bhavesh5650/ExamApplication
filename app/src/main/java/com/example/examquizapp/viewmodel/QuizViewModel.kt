package com.example.examquizapp.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.examquizapp.data.model.QuestionModel
import com.example.examquizapp.data.model.QuizModel
import com.example.examquizapp.domain.repository.DataRepository.Companion.repository
import kotlinx.coroutines.launch

class QuizViewModel : ViewModel() {

    var quizMutableData = MutableLiveData<MutableList<QuestionModel>>()
    val quizData:LiveData<MutableList<QuestionModel>> = quizMutableData

    private val countMutableData = MutableLiveData(0)
    var countQuiz : LiveData<Int> = countMutableData

    var category:String?=null
    val tempList = mutableListOf<QuestionModel>()
    var selectOption:String?=null

    var correctAnswer:Int = 0
    var wrongAnswer:Int=0

    fun getQuizApi()
    {
        viewModelScope.launch {
            val list = repository.quizApiCall(category = category!!)
            for(i in list?.results!!)
            {
                val opList = i!!.incorrectAnswers!!.toMutableList()
                opList.add(i.correctAnswer)
                opList.shuffle()
                val questionModel = QuestionModel(i.question!!,i.correctAnswer!!,opList)
                tempList.add(questionModel)
                quizMutableData.value = tempList
            }
            Log.e("TAG", "getQuizApi ============ $tempList ")

        }
    }

    fun changeQuiz()
    {
        if(countMutableData.value!! < 9)
        {
            resultCheck()
            Log.d("Selected ", "changeQuiz ======== Select Option : $selectOption ")
            Log.d("Answer", "changeQuiz ======== Answer : ${quizData.value?.get(countQuiz.value!!)?.answer} ")
            Log.i("Question", "changeQuiz:================ Question : ${quizData.value?.get(countQuiz.value!!)?.question} ")
            countMutableData.value = countMutableData.value!!+1
            Log.d("TAG", "changeQuiz:============================ Count : ${countQuiz.value} ")
        }
        else{
            resultCheck()
            Log.d("Selected ", "changeQuiz ======== Select Option : $selectOption ")
            Log.d("Answer", "changeQuiz ======== Answer : ${quizData.value?.get(countQuiz.value!!)?.answer} ")
            Log.i("Question", "changeQuiz:================ Question : ${quizData.value?.get(countQuiz.value!!)?.question} ")

        }
    }

    fun resultCheck()
    {
        if(quizData.value!![countQuiz.value!!].answer == selectOption)
        {
            correctAnswer++
        }
        else
        {
            wrongAnswer++
        }
    }
}