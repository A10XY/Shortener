package com.ahmed.shortener.ui

import android.view.LayoutInflater
import com.ahmed.shortener.R
import com.ahmed.shortener.databinding.ActivityMainBinding
import com.ahmed.shortener.utils.Constants
import com.ahmed.shortener.utils.navigateToFragment

class MainActivity : BaseActivity<ActivityMainBinding>() {
    override val logTag: String = Constants.MAIN_ACTIVITY_LOG_TAG
    override val bindingInflater: (LayoutInflater) -> ActivityMainBinding = ActivityMainBinding::inflate

    override fun setUp() {
        navigateToFragment(this, R.id.fragment_container_view_all, HomeFragment())
    }

    override fun addCallbacks() {
        log("Add Callbacks")
    }
}