package com.tugbaolcer.recipeapp.presentation.hub

import android.content.Context
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import com.google.android.material.tabs.TabLayout
import com.tugbaolcer.recipeapp.R
import com.tugbaolcer.recipeapp.base.BaseRecipeActivity
import com.tugbaolcer.recipeapp.databinding.ActivityHubBinding
import com.tugbaolcer.recipeapp.presentation.hub.login.LoginFragment
import com.tugbaolcer.recipeapp.presentation.hub.register.RegisterFragment
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class HubActivity : BaseRecipeActivity<HubViewModel, ActivityHubBinding>() {
    override val layoutResourceId: Int
        get() = R.layout.activity_hub

    override fun init() {
        binding.apply {
            val pagerAdapter = HubPagerAdapter(this@HubActivity, supportFragmentManager)
            viewPagerHub.adapter = pagerAdapter
            tabLayoutHub.setupWithViewPager(viewPagerHub)

            tabLayoutHub.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener {
                override fun onTabSelected(tab: TabLayout.Tab?) {
                    when (tab?.position) {
                        0 -> {
                            tabLayoutHub.getTabAt(0)!!.setText(R.string.selected_hub_login_tab)
                            tabLayoutHub.getTabAt(1)!!.text = getString(R.string.Tab_Button_SignUp)
                        }

                        1 -> {
                            tabLayoutHub.getTabAt(0)!!.text = getString(R.string.Tab_Button_SignIn)
                            tabLayoutHub.getTabAt(1)!!.setText(R.string.selected_hub_register_tab)
                        }
                    }
                }

                override fun onTabUnselected(tab: TabLayout.Tab?) {}

                override fun onTabReselected(tab: TabLayout.Tab?) {}

            })

            viewPagerHub.currentItem = intent?.getIntExtra("INITIAL_TAB", 0) ?: 0
        }
    }

    @Suppress("DEPRECATION")
    inner class HubPagerAdapter(val context: Context, fm: FragmentManager) :
        FragmentPagerAdapter(fm, BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT) {

        private val tabTitles = arrayOf(R.string.selected_hub_login_tab, R.string.Tab_Button_SignUp)

        override fun getItem(position: Int): Fragment {
            val fragment = when (position) {
                0 -> LoginFragment.newInstance()
                1 -> RegisterFragment.newInstance()
                else -> LoginFragment.newInstance()
            }

            return fragment
        }

        override fun getCount(): Int {
            return 2
        }

        override fun getPageTitle(position: Int): CharSequence {
            return context.resources.getText(tabTitles[position])
        }

        override fun getItemPosition(`object`: Any): Int {
            return POSITION_NONE
        }
    }

    fun updateViewPagerItem(position: Int) {
        binding.viewPagerHub.currentItem = position
    }


    override fun initTopBar(title: Int?) {}

    override fun retrieveNewData() {}

    override fun bindingData() {
        binding.lifecycleOwner = this
        binding.vm = viewModel
    }

    override val viewModelClass: Class<HubViewModel>
        get() = HubViewModel::class.java

}