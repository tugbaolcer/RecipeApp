package com.tugbaolcer.recipeapp.base

import android.os.Bundle
import android.util.Log
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import com.tugbaolcer.recipeapp.R
import com.tugbaolcer.recipeapp.utils.ProgressDialog
import com.tugbaolcer.recipeapp.utils.observeUiState
import com.tugbaolcer.recipeapp.utils.showErrorAlert
import kotlinx.coroutines.flow.StateFlow


abstract class BaseRecipeActivity<VM : BaseRecipeViewModel, B : ViewDataBinding>() :
    AppCompatActivity() {

    protected abstract val layoutResourceId: Int

    abstract val viewModelClass: Class<VM>

    protected val viewModel: VM by lazy {
        ViewModelProvider(this)[viewModelClass]
    }

    protected lateinit var binding: B
    protected abstract fun init()
    abstract fun initTopBar(title: Int? = null)
    abstract fun retrieveNewData()
    abstract fun bindingData()

    private var progressDialog: ProgressDialog? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = DataBindingUtil.setContentView(this, layoutResourceId)

        progressDialog = ProgressDialog(this)

        enableEdgeToEdge()
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        setupUiObserver(
            uiState = viewModel.uiState,
            onLoading = {
                progressDialog?.show()
            },
            onSuccess = {
                progressDialog?.dismiss()
                Log.d("LOG_STATE", "onSuccess worked !")
            },
            onError = { message ->
                progressDialog?.dismiss()
                showErrorAlert(message = message)
                Log.d("LOG_STATE", "onError worked !")
            }
        )

        bindingData()
        init()

    }

    protected fun <T> setupUiObserver(
        uiState: StateFlow<UiState<T>>,
        onLoading: () -> Unit,
        onSuccess: (T) -> Unit,
        onError: (String) -> Unit
    ) {
        lifecycleScope.observeUiState(
            uiState = uiState,
            lifecycle = lifecycle,
            onLoading = onLoading,
            onSuccess = onSuccess,
            onError = onError
        )
    }

}