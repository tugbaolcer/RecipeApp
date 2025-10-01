package com.tugbaolcer.recipeapp.presentation.hub.login

import android.content.Intent
import androidx.lifecycle.lifecycleScope
import com.tugbaolcer.recipeapp.base.BaseRecipeFragment
import com.tugbaolcer.recipeapp.databinding.FragmentLoginBinding
import com.tugbaolcer.recipeapp.presentation.main.MainActivity
import com.tugbaolcer.recipeapp.utils.setOnSingleClickListener
import com.tugbaolcer.recipeapp.utils.showErrorAlert
import com.tugbaolcer.recipeapp.utils.withArgs
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class LoginFragment : BaseRecipeFragment<LoginViewModel, FragmentLoginBinding>() {

    companion object {
        fun newInstance() = LoginFragment().withArgs {}
    }

    override fun init() {
        binding.apply {
            btnLogin.setOnSingleClickListener {
                viewModel.loginUser(
                    email = tilEmail.inputText,
                    password = tilPassword.inputText
                ) { result ->
                    lifecycleScope.launch {
                        if (result.isSuccessful) {
                            startActivity(Intent(requireContext(), MainActivity::class.java))
                            requireActivity().finish()
                        } else {
                            requireContext().showErrorAlert(
                                result.exception?.localizedMessage ?: "Hataaa !!"
                            )
                        }
                    }
                }

            }
        }
    }

    override fun retrieveData() {}

    override fun bindingData() {
        binding.vm = viewModel
        binding.lifecycleOwner = viewLifecycleOwner
    }


    override fun initTopBar() {}

    override val viewModelClass: Class<LoginViewModel>
        get() = LoginViewModel::class.java

    override fun getViewBinding() = FragmentLoginBinding.inflate(layoutInflater)

}