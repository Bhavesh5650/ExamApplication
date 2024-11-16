package com.example.examquizapp.view.activity

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.examquizapp.MainActivity
import com.example.examquizapp.R
import com.example.examquizapp.data.helper.AuthenticationHelper.Companion.authHelper
import com.example.examquizapp.databinding.ActivityMainBinding
import com.example.examquizapp.databinding.ActivitySplashScreenBinding

class SplashScreen : AppCompatActivity() {

    private lateinit var binding: ActivitySplashScreenBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySplashScreenBinding.inflate(layoutInflater)
        enableEdgeToEdge()
        setContentView(binding.root)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        authHelper.getCurrentUser()
        Handler(Looper.getMainLooper()).postDelayed({
            if(authHelper.user==null)
            {
                startActivity(Intent(this,SignInActivity::class.java))
                finish()
            }
            else{
                startActivity(Intent(this,MainActivity::class.java))
                finish()
            }
        },2000)

    }
}