package com.example.mobilliumchallenge.view.adapters

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentStatePagerAdapter
import com.example.mobilliumchallenge.model.entities.common.Result
import com.example.mobilliumchallenge.utils.Constants
import com.example.mobilliumchallenge.view.HomeFragment
import com.example.mobilliumchallenge.view.NowPlayingMoviesFragment
import com.example.talentbase_android.utils.Listener

class HomeSliderViewPagerAdapter(fm: FragmentManager, private val results: List<Result?>? ) : FragmentStatePagerAdapter(fm) {
    private val TAG = "TutorialFragmentAdapter"


    override fun getCount(): Int {
        return results?.size ?: 0

    }

    override fun getItem(position: Int): Fragment {
        val nowPlayingMoviesFragment = NowPlayingMoviesFragment()
        val bundle = Bundle()
        if (results != null) {
            if (results.isNotEmpty() && results[position] != null) {
                bundle.putParcelable(Constants.SLIDER_INTENT_KEY, results[position])
            }
        }
        nowPlayingMoviesFragment.arguments = bundle
        return nowPlayingMoviesFragment
    }


}