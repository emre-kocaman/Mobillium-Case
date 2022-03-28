package com.example.mobilliumchallenge.view

import android.os.Bundle
import com.example.mobilliumchallenge.R
import com.example.mobilliumchallenge.base.BaseActivity
import com.example.mobilliumchallenge.databinding.ActivityMainBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : BaseActivity<ActivityMainBinding>() {

    override fun getViewBinding(): ActivityMainBinding {
        return ActivityMainBinding.inflate(layoutInflater)
    }
}