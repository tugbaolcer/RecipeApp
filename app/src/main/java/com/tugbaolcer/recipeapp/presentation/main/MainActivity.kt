package com.tugbaolcer.recipeapp.presentation.main

import com.tugbaolcer.recipeapp.R
import com.tugbaolcer.recipeapp.base.BaseRecipeActivity
import com.tugbaolcer.recipeapp.databinding.ActivityMainBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : BaseRecipeActivity<MainViewModel, ActivityMainBinding>() {

    override val layoutResourceId: Int
        get() = R.layout.activity_main

//    override val viewModel: MainViewModel by viewModels()

    override fun init() {
        retrieveNewData()
    }

    override fun initTopBar(title: Int?) {}

    override fun retrieveNewData() {
        viewModel.fetchCategories()
    }

    override fun bindingData() {
        binding.lifecycleOwner = this
        binding.vm = viewModel
    }

    override val viewModelClass: Class<MainViewModel>
        get() = MainViewModel::class.java
}
