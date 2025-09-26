package com.tugbaolcer.recipeapp.base

import android.os.Bundle
import android.view.View
import android.widget.ProgressBar
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.tugbaolcer.recipeapp.R
import com.tugbaolcer.recipeapp.utils.showErrorAlert
import kotlinx.coroutines.launch


abstract class BaseRecipeActivity <VM: BaseRecipeViewModel, B: ViewDataBinding>(): AppCompatActivity() {

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

    private var progressDialog: ProgressBar? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = DataBindingUtil.setContentView(this, layoutResourceId)

        progressDialog = ProgressBar(this)

        enableEdgeToEdge()
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        observeUiState()

        bindingData()
        init()

    }

    private fun observeUiState() {
        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.uiState.collect { state ->
                    when (state) {
                        is UiState.Loading -> {
                            progressDialog?.visibility = View.VISIBLE
                        }
                        is UiState.Success<*> -> {
                            progressDialog?.visibility = View.GONE
                            retrieveNewData()
//                            onDataLoaded(state.data)
                        }
                        is UiState.Error -> {
                            progressDialog?.visibility = View.GONE
                            showErrorAlert(state.message)
                        }
                    }
                }
            }
        }
    }

}