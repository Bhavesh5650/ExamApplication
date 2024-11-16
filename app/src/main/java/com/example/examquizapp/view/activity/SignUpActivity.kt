package com.example.examquizapp.view.activity

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.examquizapp.R
import com.example.examquizapp.data.helper.AuthenticationHelper.Companion.authHelper
import com.example.examquizapp.databinding.ActivitySignUpBinding
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import kotlin.math.log10

class SignUpActivity : AppCompatActivity() {

    private lateinit var binding:ActivitySignUpBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //enableEdgeToEdge()
        binding = ActivitySignUpBinding.inflate(layoutInflater)
        setContentView(binding.root)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        initSignUp()
        initTextClick()
    }

    private fun initSignUp()
    {
        binding.signUpBtn.setOnClickListener{

            val email = binding.edtEmailUp.text.toString()
            val password = binding.edtPasswordUp.text.toString()

            if(email.isEmpty())
            {
                binding.emailLayoutUp.error = "Email Is Required"
            }
            else if(password.isEmpty())
            {
                binding.passwordLayoutUp.error = "Password Is Required"
            }
            else{

                Log.i("TAG", "initSignUp:==== email=$email password=$password ")

                GlobalScope.launch {
                    val response = authHelper.signUp(email = email, password = password)
                    withContext(Dispatchers.Main)
                    {
                        Log.d("Response", "initSignUp:============ $response ")

                        if(response=="Success")
                        {
                            startActivity(Intent(this@SignUpActivity,SignInActivity::class.java))
                            finish()
                        }
                        else
                        {
                            Toast.makeText(this@SignUpActivity, response , Toast.LENGTH_SHORT).show()
                            Log.e("Fail", "initSignUp--------------------------- $response ", )
                        }
                    }
                }

            }
        }
    }

    private fun initTextClick()
    {
        binding.signInBtnTxt.setOnClickListener {
            startActivity(Intent(this,SignInActivity::class.java))
            finish()
        }
    }
}