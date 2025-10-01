package com.tugbaolcer.recipeapp.utils

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleCoroutineScope
import androidx.lifecycle.repeatOnLifecycle
import com.tugbaolcer.recipeapp.base.UiState
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

fun <T> LifecycleCoroutineScope.observeUiState(
    uiState: StateFlow<UiState<T>>,
    lifecycle: Lifecycle,
    onLoading: () -> Unit,
    onSuccess: (T) -> Unit,
    onError: (String) -> Unit
) {
    launch {
        lifecycle.repeatOnLifecycle(Lifecycle.State.STARTED) {
            uiState.collect { state ->
                when (state) {
                    is UiState.Loading -> if (state.showProgress) onLoading()
                    is UiState.Success<T> -> onSuccess(state.data)
                    is UiState.Error -> onError(state.message)
                }
            }
        }
    }
}

fun View.setOnSingleClickListener(block: () -> Unit) {
    setOnClickListener(OnSingleClickListener(block))
}

inline fun <T : Fragment> T.withArgs(argsBuilder: Bundle.() -> Unit): T =
    this.apply {
        arguments = Bundle().apply(argsBuilder)
    }
