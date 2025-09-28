package com.tugbaolcer.recipeapp.base

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.ViewDataBinding
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import com.tugbaolcer.recipeapp.utils.ProgressDialog
import com.tugbaolcer.recipeapp.utils.observeUiState
import com.tugbaolcer.recipeapp.utils.showErrorAlert
import kotlinx.coroutines.flow.StateFlow

abstract class BaseRecipeFragment<VM : BaseRecipeViewModel, B : ViewDataBinding>() : Fragment() {

    abstract val viewModelClass: Class<VM>

    protected val viewModel: VM by lazy {
        ViewModelProvider(this)[viewModelClass]
    }

    protected abstract fun init()

    abstract fun retrieveData()

    abstract fun initTopBar()

    protected lateinit var binding: B

    abstract fun getViewBinding(): B

    private var progressDialog: ProgressDialog? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = getViewBinding()
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        if (isAdded) {
            Log.d("Fragment", "$javaClass Fragment is added .")
            initTopBar()
            init()

            progressDialog = ProgressDialog(requireContext())

            setupUiObserver(
                uiState = viewModel.uiState,
                onLoading = {
                    progressDialog?.show()
                },
                onSuccess = {
                    progressDialog?.dismiss()
                },
                onError = { message ->
                    progressDialog?.dismiss()
                    requireContext().showErrorAlert(message = message)
                }
            )

        } else {
            Log.d("Fragment", "$javaClass Fragment is not added.")
        }
    }

    protected fun <T> setupUiObserver(
        uiState: StateFlow<UiState<T>>,
        onLoading: () -> Unit,
        onSuccess: (T) -> Unit,
        onError: (String) -> Unit
    ) {
        viewLifecycleOwner.lifecycleScope.observeUiState(
            uiState = uiState,
            lifecycle = viewLifecycleOwner.lifecycle,
            onLoading = onLoading,
            onSuccess = onSuccess,
            onError = onError
        )
    }

}