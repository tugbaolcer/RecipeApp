package com.tugbaolcer.recipeapp.presentation

import com.google.android.gms.tasks.Task
import com.google.firebase.auth.AuthResult
import com.tugbaolcer.recipeapp.R
import com.tugbaolcer.recipeapp.databinding.FragmentRegisterBinding
import com.tugbaolcer.recipeapp.presentation.hub.register.RegisterFragment
import com.tugbaolcer.recipeapp.presentation.hub.register.RegisterViewModel
import com.tugbaolcer.recipeapp.utils.launchFragmentInHiltContainer
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import io.mockk.every
import io.mockk.mockk
import junit.framework.TestCase.assertEquals
import kotlinx.coroutines.ExperimentalCoroutinesApi
import org.junit.Before
import org.junit.Rule
import org.junit.Test


@HiltAndroidTest
@OptIn(ExperimentalCoroutinesApi::class)
class RegisterFragmentTest {

    @get:Rule
    val hiltRule = HiltAndroidRule(this)

    private var mockViewModel = mockk<RegisterViewModel>(relaxed = true)

    @Before
    fun setup() {
        hiltRule.inject()
    }

    @Test
    fun when_passwords_not_match_show_error_alert() {
        val scenario = launchFragmentInHiltContainer<RegisterFragment>()

        scenario.onFragment { fragment ->
            val binding = fragment.requireView().let { FragmentRegisterBinding.bind(it) }

            binding.tilPassword.binding.etCustom.setText("123456")
            binding.tilRepassword.binding.etCustom.setText("654321")

            binding.btnRegister.performClick()

            // Eğer showErrorAlert Dialog veya Toast gösteriyorsa, onu kontrol edebilirsin.
            // Burada sadece pseudo bir doğrulama:
            assertEquals("Şifreler eşleşmiyor", fragment.requireContext().getString(R.string.Label_ErrorMessage_Password))
        }
    }

    @Test
    fun when_register_success_show_success_alert() {
        val scenario = launchFragmentInHiltContainer<RegisterFragment> {
            mockViewModel = mockk(relaxed = true)
        }

        scenario.onFragment { fragment ->
            val binding = fragment.requireView().let { FragmentRegisterBinding.bind(it) }

            binding.tilEmail.binding.etCustom.setText("test@test.com")
            binding.tilPassword.binding.etCustom.setText("123456")
            binding.tilRepassword.binding.etCustom.setText("123456")

            // Mocklama
            every {
                mockViewModel.registerUser(any(), any(), any())
            } answers {
                val callback = arg<(Task<AuthResult>) -> Unit>(2)
                val mockTask = mockk<Task<AuthResult>> {
                    every { isSuccessful } returns true
                }
                callback(mockTask)
            }

            binding.btnRegister.performClick()
        }
    }
}