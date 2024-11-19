package com.example.examquizapp.view.activity

import android.annotation.SuppressLint
import android.app.Dialog
import android.content.Intent
import android.os.Bundle
import android.os.CountDownTimer
import android.util.Log
import android.view.View
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.examquizapp.R
import com.example.examquizapp.data.helper.QuizHelper
import com.example.examquizapp.data.model.QuestionModel
import com.example.examquizapp.data.model.QuizModel
import com.example.examquizapp.databinding.ActivityQuizBinding
import com.example.examquizapp.viewmodel.QuizViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class QuizActivity : AppCompatActivity() {

    lateinit var dialog: Dialog
    lateinit var timer: CountDownTimer
    private lateinit var binding: ActivityQuizBinding
    val viewModel by viewModels<QuizViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityQuizBinding.inflate(layoutInflater)
        setContentView(binding.root)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }


        apiCall()
        initObserveData()
        selectOption()
        nextClickEvent()
        quizTimer()
        quizProgress()

    }

    private fun quizProgress() {
        dialog = Dialog(this)
        dialog.setContentView(R.layout.progess_layout)
        dialog.setCancelable(false)
        dialog.show()
    }

    @SuppressLint("SetTextI18n")
    private fun apiCall() {
        binding.setQuestionCount.text = "${viewModel.countQuiz.value?.plus(1)}"
        val category = intent.getStringExtra("category")
        viewModel.category = category
        viewModel.getQuizApi()
    }

    private fun initObserveData() {
        viewModel.quizData.observe(this) {
            if (it!=null) {
                binding.setQuestion.text = it[0].question
                binding.setAnswerOne.text = it[0].optionList!![0]
                binding.setAnswerTwo.text = it[0].optionList!![1]
                binding.setAnswerThree.text = it[0].optionList!![2]
                binding.setAnswerFour.text = it[0].optionList!![3]
                dialog.dismiss()
                timer.start()
            }
        }

        viewModel.countQuiz.observe(this) {
            binding.setQuestion.text = viewModel.quizData.value?.get(it)?.question
            binding.setAnswerOne.text = viewModel.quizData.value?.get(it)?.optionList?.get(0)
            binding.setAnswerTwo.text = viewModel.quizData.value?.get(it)?.optionList?.get(1)
            binding.setAnswerThree.text = viewModel.quizData.value?.get(it)?.optionList?.get(2)
            binding.setAnswerFour.text = viewModel.quizData.value?.get(it)?.optionList?.get(3)

        }
    }

    @SuppressLint("SetTextI18n")
    fun nextClickEvent() {
        binding.nextQuestionBtn.setOnClickListener {
            viewModel.changeQuiz()
            binding.setQuestionCount.text = "${viewModel.countQuiz.value?.plus(1)}"
            timer.cancel()
            timer.start()
            binding.nextQuestionBtn.visibility = View.INVISIBLE
            binding.selectOneImage.visibility = View.INVISIBLE
            binding.selectTwoImage.visibility = View.INVISIBLE
            binding.selectThreeImage.visibility = View.INVISIBLE
            binding.selectFourImage.visibility = View.INVISIBLE
            if (viewModel.countQuiz.value==9)
            {
                binding.nextBtnText.text = "Submit"
                binding.nextQuestionBtn.setOnClickListener {
                    viewModel.changeQuiz()
                    timer.cancel()
                    val intent = Intent(this,QuizResultActivity::class.java)
                    intent.putExtra("correct",viewModel.correctAnswer)
                    intent.putExtra("incorrect",viewModel.wrongAnswer)
                    startActivity(intent)
                }
            }
        }
    }

    private fun selectOption() {
        binding.optionOneCard.setOnClickListener {

            binding.nextQuestionBtn.visibility = View.VISIBLE
            binding.selectOneImage.visibility = View.VISIBLE
            binding.selectTwoImage.visibility = View.INVISIBLE
            binding.selectThreeImage.visibility = View.INVISIBLE
            binding.selectFourImage.visibility = View.INVISIBLE
            viewModel.selectOption =
                viewModel.quizData.value!![viewModel.countQuiz.value!!].optionList?.get(0)
        }


        binding.optionTwoCard.setOnClickListener {

            binding.nextQuestionBtn.visibility = View.VISIBLE
            binding.selectOneImage.visibility = View.INVISIBLE
            binding.selectTwoImage.visibility = View.VISIBLE
            binding.selectThreeImage.visibility = View.INVISIBLE
            binding.selectFourImage.visibility = View.INVISIBLE
            viewModel.selectOption =
                viewModel.quizData.value!![viewModel.countQuiz.value!!].optionList?.get(1)
        }

        binding.optionThreeCard.setOnClickListener {

            binding.nextQuestionBtn.visibility = View.VISIBLE
            binding.selectOneImage.visibility = View.INVISIBLE
            binding.selectTwoImage.visibility = View.INVISIBLE
            binding.selectThreeImage.visibility = View.VISIBLE
            binding.selectFourImage.visibility = View.INVISIBLE
            viewModel.selectOption =
                viewModel.quizData.value!![viewModel.countQuiz.value!!].optionList?.get(2)
        }

        binding.optionFourCard.setOnClickListener {

            binding.nextQuestionBtn.visibility = View.VISIBLE
            binding.selectOneImage.visibility = View.INVISIBLE
            binding.selectTwoImage.visibility = View.INVISIBLE
            binding.selectThreeImage.visibility = View.INVISIBLE
            binding.selectFourImage.visibility = View.VISIBLE
            viewModel.selectOption = viewModel.quizData.value!![viewModel.countQuiz.value!!].optionList?.get(3)
        }
    }

    private fun quizTimer()
    {
        timer = object : CountDownTimer(30000,1000) {
            @SuppressLint("SetTextI18n")
            override fun onTick(p0: Long) {
                binding.setTimer.text = "${p0/1000}"
            }

            @SuppressLint("SetTextI18n")
            override fun onFinish() {
                viewModel.selectOption = ""
                viewModel.changeQuiz()
                if (viewModel.countQuiz.value!! >=9)
                {
                    binding.nextBtnText.text = "Submit"
                    val intent = Intent(this@QuizActivity,QuizResultActivity::class.java)
                    intent.putExtra("correct",viewModel.correctAnswer)
                    intent.putExtra("incorrect",viewModel.wrongAnswer)
                    startActivity(intent)

                }
                binding.setQuestionCount.text = "${viewModel.countQuiz.value?.plus(1)}"
                timer.cancel()
                timer.start()
            }

        }
    }

}