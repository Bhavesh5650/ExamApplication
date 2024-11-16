package com.example.examquizapp.data.helper

import android.util.Log
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseAuthException
import com.google.firebase.auth.FirebaseUser
import kotlinx.coroutines.tasks.await

class AuthenticationHelper  {

    companion object {
        val authHelper = AuthenticationHelper()
    }

    var user: FirebaseUser? = null
    val auth = FirebaseAuth.getInstance()

    suspend fun signIn(email:String,password:String): String {

        var msg=""

        try {
            auth.signInWithEmailAndPassword(email,password).addOnSuccessListener {
                msg = "Success"
            }.addOnFailureListener {
                msg = it.message.toString()
            }.await()
        }
        catch (e:FirebaseAuthException)
        {
            msg = e.message.toString()
        }

        return msg
    }

    suspend fun signUp(email: String,password: String): String {

        var msg=""

        try {
            auth.createUserWithEmailAndPassword(email,password).addOnSuccessListener {
                msg = "Success"

            }.addOnFailureListener {
                msg = it.message.toString()
            }.await()
        }
        catch (e:FirebaseAuthException)
        {
            msg = e.message.toString()
        }

        Log.d("TAG", "signUp ======== $msg ")
        return msg
    }

    fun getCurrentUser()
    {
        user = auth.currentUser
    }
}