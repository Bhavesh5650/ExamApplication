package com.example.examquizapp.view.activity

import android.os.Bundle
import android.util.Log
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.examquizapp.R
import com.example.examquizapp.data.model.QuestionModel
import com.example.examquizapp.databinding.ActivityViewAnswerBinding
import com.example.examquizapp.view.adapter.AnswerAdapter
import com.example.examquizapp.viewmodel.QuizViewModel
import kotlin.math.log

class ViewAnswerActivity : AppCompatActivity() {

    private lateinit var binding:ActivityViewAnswerBinding
    val viewModel by viewModels<QuizViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityViewAnswerBinding.inflate(layoutInflater)
        setContentView(binding.root)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        val answerAdapter = AnswerAdapter(viewModel.tempList,viewModel)
        binding.answerRecycleView.adapter = answerAdapter

        Log.d("TAG", "onCreate --------------------- ${viewModel.quizData.value} ")
        Log.d("TAG", "onCreate --------------------- ${viewModel.tempList} ")

        binding.backBtn.setOnClickListener {
            finish()
        }

    }
}