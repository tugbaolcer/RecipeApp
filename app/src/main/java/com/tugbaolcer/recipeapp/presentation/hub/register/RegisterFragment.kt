package com.tugbaolcer.recipeapp.presentation.hub.register


import androidx.lifecycle.lifecycleScope
import com.tugbaolcer.recipeapp.R
import com.tugbaolcer.recipeapp.base.BaseRecipeFragment
import com.tugbaolcer.recipeapp.databinding.FragmentRegisterBinding
import com.tugbaolcer.recipeapp.presentation.hub.HubActivity
import com.tugbaolcer.recipeapp.utils.setOnSingleClickListener
import com.tugbaolcer.recipeapp.utils.showErrorAlert
import com.tugbaolcer.recipeapp.utils.showSuccessAlert
import com.tugbaolcer.recipeapp.utils.withArgs
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class RegisterFragment : BaseRecipeFragment<RegisterViewModel, FragmentRegisterBinding>() {

    companion object {
        fun newInstance() = RegisterFragment().withArgs {}
    }

    override fun init() {
        binding.apply {
            btnRegister.setOnSingleClickListener {
                if (tilPassword.inputText == binding.tilRepassword.inputText) {
                    viewModel.registerUser(email = tilEmail.inputText, password = tilPassword.inputText) { result ->
                        lifecycleScope.launch {
                            if (result.isSuccessful) {
                                requireContext().showSuccessAlert(getString(R.string.Label_Success))
                                (requireActivity() as HubActivity).updateViewPagerItem(0)
                            } else {
                                requireContext().showErrorAlert(result.exception?.localizedMessage ?: "Hataaa !!")
                            }
                        }
                    }
                } else {
                    lifecycleScope.launch {
                        requireContext().showErrorAlert(getString(R.string.Label_ErrorMessage_Password))
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

    override val viewModelClass: Class<RegisterViewModel>
        get() = RegisterViewModel::class.java

    override fun getViewBinding() = FragmentRegisterBinding.inflate(layoutInflater)

}