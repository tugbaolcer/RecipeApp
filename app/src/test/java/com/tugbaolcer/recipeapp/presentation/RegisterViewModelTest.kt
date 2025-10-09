package com.tugbaolcer.recipeapp.presentation

import com.google.android.gms.tasks.OnCompleteListener
import com.google.android.gms.tasks.Task
import com.google.firebase.auth.AuthResult
import com.google.firebase.auth.FirebaseAuth
import com.tugbaolcer.recipeapp.util.MainDispatcherRule
import com.tugbaolcer.recipeapp.presentation.hub.register.RegisterViewModel
import io.mockk.every
import io.mockk.mockk
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.junit.Assert
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import kotlin.test.assertEquals

@OptIn(ExperimentalCoroutinesApi::class)
class RegisterViewModelTest {

    @get:Rule
    val mainDispatcherRule = MainDispatcherRule()

    private lateinit var viewModel: RegisterViewModel
    private val firebaseAuth: FirebaseAuth = mockk()

    @Before
    fun setup() {
        viewModel = RegisterViewModel(firebaseAuth)
    }

    @Test
    fun `registerUser should call handleResult with success`() = runTest {
        // Given
        val email = "test@test.com"
        val password = "123456"

        val mockTask = mockk<Task<AuthResult>>(relaxed = true)
        every { mockTask.isSuccessful } returns true

        // FirebaseAuth çağrısını taklit et
        every {
            firebaseAuth.createUserWithEmailAndPassword(email, password)
        } returns mockTask

        every {
            mockTask.addOnCompleteListener(any())
        } answers {
            val listener = arg<OnCompleteListener<AuthResult>>(0)
            listener.onComplete(mockTask)
            mockTask
        }

        var callbackTriggered = false

        // When
        viewModel.registerUser(email, password) { result ->
            callbackTriggered = result.isSuccessful
        }

        // Then
        Assert.assertTrue(callbackTriggered)
    }

    @Test
    fun `registerUser should call handleResult with error`() = runTest {
        // Given
        val email = "wrong@test.com"
        val password = "123456"

        val exception = Exception("Registration failed")
        val mockTask = mockk<Task<AuthResult>>(relaxed = true)
        every { mockTask.isSuccessful } returns false
        every { mockTask.exception } returns exception

        every {
            firebaseAuth.createUserWithEmailAndPassword(email, password)
        } returns mockTask

        every {
            mockTask.addOnCompleteListener(any())
        } answers {
            val listener = arg<OnCompleteListener<AuthResult>>(0)
            listener.onComplete(mockTask)
            mockTask
        }

        var errorMessage: String? = null

        // When
        viewModel.registerUser(email, password) { result ->
            errorMessage = result.exception?.message
        }

        // Then
        assertEquals("Registration failed", errorMessage)
    }
}
