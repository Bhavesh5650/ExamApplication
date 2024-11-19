package com.example.examquizapp.view.activity

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.examquizapp.MainActivity
import com.example.examquizapp.R
import com.example.examquizapp.databinding.ActivityQuizResultBinding
import kotlin.math.log

class QuizResultActivity : AppCompatActivity() {
    lateinit var binding:ActivityQuizResultBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //enableEdgeToEdge()
        binding = ActivityQuizResultBinding.inflate(layoutInflater)
        setContentView(binding.root)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        initSetData()
        initClick()

    }

    @SuppressLint("SetTextI18n")
    fun initSetData()
    {
        val correct = intent.getIntExtra("correct",0)
        val incorrect = intent.getIntExtra("incorrect",0)

        binding.setCorrectAns.text = "$correct"
        binding.setIncorrectAns.text = "$incorrect"
        binding.setScore.text = "${correct * 10}"
    }

    private fun initClick()
    {
        binding.playAgain.setOnClickListener{
            val intent = Intent(this,MainActivity::class.java)
            startActivity(intent)
        }

        binding.viewAnswers.setOnClickListener{
            startActivity(Intent(this,ViewAnswerActivity::class.java))
        }
    }
}