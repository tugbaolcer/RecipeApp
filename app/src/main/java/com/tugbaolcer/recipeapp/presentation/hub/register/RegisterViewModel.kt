package com.tugbaolcer.recipeapp.presentation.hub.register

import com.google.android.gms.tasks.Task
import com.google.firebase.auth.AuthResult
import com.google.firebase.auth.FirebaseAuth
import com.tugbaolcer.recipeapp.base.BaseRecipeViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class RegisterViewModel @Inject constructor(private val firebaseAuth: FirebaseAuth) :
    BaseRecipeViewModel() {

    fun registerUser(email: String, password: String, handleResult: (Task<AuthResult>) -> Unit) {
        firebaseAuth.createUserWithEmailAndPassword(email, password)
            .addOnCompleteListener { task ->
                handleResult(task)
            }
    }
}