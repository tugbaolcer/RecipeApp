package com.tugbaolcer.recipeapp.utils

import android.os.Bundle
import androidx.annotation.StyleRes
import androidx.fragment.app.Fragment
import androidx.fragment.app.testing.FragmentScenario
import androidx.fragment.app.testing.launchFragmentInContainer
import com.tugbaolcer.recipeapp.R

inline fun <reified F : Fragment> launchFragmentInHiltContainer(
    fragmentArgs: Bundle? = null,
    @StyleRes themeResId: Int = R.style.Theme_RecipeApp,
    crossinline action: F.() -> Unit = {}
): FragmentScenario<F> { // 👈 dönüş tipi eklendi
    val scenario = launchFragmentInContainer<F>(fragmentArgs, themeResId = themeResId)
    scenario.onFragment { fragment ->
        action(fragment)
    }
    return scenario
}
