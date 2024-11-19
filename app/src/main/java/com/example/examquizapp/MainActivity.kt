package com.example.examquizapp

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.examquizapp.databinding.ActivityMainBinding
import com.example.examquizapp.view.activity.QuizActivity

class MainActivity : AppCompatActivity() {

    lateinit var binding:ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //enableEdgeToEdge()
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        initClick()

    }

    private fun initClick()
    {
        binding.animalCategory.setOnClickListener{
            val intent = Intent(this,QuizActivity::class.java)
            intent.putExtra("category","27")
            startActivity(intent)
        }

        binding.sportsCategory.setOnClickListener{
            val intent = Intent(this,QuizActivity::class.java)
            intent.putExtra("category","21")
            startActivity(intent)
        }
    }
}