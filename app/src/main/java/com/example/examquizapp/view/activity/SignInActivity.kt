package com.example.examquizapp.view.activity

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.examquizapp.MainActivity
import com.example.examquizapp.R
import com.example.examquizapp.data.helper.AuthenticationHelper.Companion.authHelper
import com.example.examquizapp.databinding.ActivitySignInBinding
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseAuthException
import com.google.firebase.auth.GoogleAuthProvider
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class SignInActivity : AppCompatActivity() {

    private lateinit var binding:ActivitySignInBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //enableEdgeToEdge()
        binding = ActivitySignInBinding.inflate(layoutInflater)
        setContentView(binding.root)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        initSignIn()
        initTextClick()
        googleSignIn()
    }

    private fun initSignIn()
    {
        binding.signInBtn.setOnClickListener{

            authHelper.getCurrentUser()
            val email = binding.edtEmailIn.text.toString()
            val password = binding.edtPasswordIn.text.toString()

            if(email.isEmpty())
            {
                binding.emailLayoutIn.error = "Email Is Required"
            }
            else if(password.isEmpty())
            {
                binding.passwordLayoutIn.error = "Password Is Required"
            }
            else{

                GlobalScope.launch {
                    val response = authHelper.signIn(email,password)
                    withContext(Dispatchers.Main)
                    {
                        if(response=="Success")
                        {
                            startActivity(Intent(this@SignInActivity,MainActivity::class.java))
                            finish()
                        }
                        else
                        {
                            Toast.makeText(this@SignInActivity, response, Toast.LENGTH_SHORT).show()
                        }
                    }
                }

            }
        }
    }

    private fun initTextClick()
    {
        binding.signUpBtnTxt.setOnClickListener {
            startActivity(Intent(this,SignUpActivity::class.java))
            finish()
        }
    }

    fun googleSignIn()
    {
        val googleSignInOption = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestEmail()
            .requestIdToken(getString(R.string.token))
            .build()

        val googleClient = GoogleSignIn.getClient(this,googleSignInOption)

        val registerGoogle = registerForActivityResult(ActivityResultContracts.StartActivityForResult()){

            val googleId = GoogleSignIn.getSignedInAccountFromIntent(it.data)

            val credential = GoogleAuthProvider.getCredential(googleId.result.idToken,null)

            try {

                FirebaseAuth.getInstance().signInWithCredential(credential).addOnSuccessListener {
                    authHelper.getCurrentUser()
                    startActivity(Intent(this,MainActivity::class.java))
                    finish()
                }.addOnFailureListener {
                    Toast.makeText(this, it.message, Toast.LENGTH_SHORT).show()
                }
            }
            catch (e:FirebaseAuthException)
            {
                Toast.makeText(this, e.message, Toast.LENGTH_SHORT).show()
            }
        }

        binding.googleSignInBtn.setOnClickListener{
            val intent =googleClient.signInIntent
            registerGoogle.launch(intent)
        }
    }

}