package com.example.mobilliumchallenge.view

import android.os.Bundle
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import androidx.core.content.ContextCompat
import androidx.hilt.navigation.fragment.hiltNavGraphViewModels
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.viewpager.widget.ViewPager
import com.example.mobilliumchallenge.R
import com.example.mobilliumchallenge.base.BaseFragment
import com.example.mobilliumchallenge.databinding.FragmentHomeBinding
import com.example.mobilliumchallenge.model.entities.common.Result
import com.example.mobilliumchallenge.utils.Constants
import com.example.mobilliumchallenge.utils.Status
import com.example.mobilliumchallenge.view.adapters.HomeSliderViewPagerAdapter
import com.example.mobilliumchallenge.view.adapters.UpcomingMoviesAdapter
import com.example.mobilliumchallenge.viewmodel.HomeFragmentViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class HomeFragment : BaseFragment<FragmentHomeBinding>() {

    private val viewModel: HomeFragmentViewModel by hiltNavGraphViewModels(R.id.nav_graph)
    private var nowPlayingPageCounter: Int? = 1
    private var upcomingPageCounter: Int? = 1
    private var position: Int = 1


    override fun getViewBinding(): FragmentHomeBinding {
        return FragmentHomeBinding.inflate(layoutInflater)
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.getNowPlayingMovies(nowPlayingPageCounter ?: 1, Constants.API_KEY)
        viewModel.getUpcomingMovies(upcomingPageCounter ?: 1, Constants.API_KEY)
        observers()
    }

    private fun observers() {
        viewModel.nowPlayingMovies.observe(viewLifecycleOwner, {
            when (it.status) {
                Status.SUCCESS -> {
                    initNowPlayingMovies(it.data?.results ?: arrayListOf())
                }
                Status.ERROR -> {

                }
                Status.LOADING -> {

                }
            }
        })

        viewModel.upComingMovies.observe(viewLifecycleOwner, {
            when (it.status) {
                Status.SUCCESS -> {
                    initUpComingEvents(it.data?.results ?: arrayListOf())
                }
                Status.ERROR -> {

                }
                Status.LOADING -> {

                }
            }
        })
    }

    private fun initUpComingEvents(list:List<Result>){
        val adapter = UpcomingMoviesAdapter(context,list.toMutableList())
        binding.recyclerView.layoutManager = LinearLayoutManager(context)
        binding.recyclerView.adapter = adapter

    }

    private fun pageChangeListener(mPager: ViewPager, size: Int) {
        mPager.addOnPageChangeListener(object : ViewPager.SimpleOnPageChangeListener() {
            override fun onPageSelected(pos: Int) {
                position = pos
                createDots(position, size)
            }
        })
    }

    private fun initNowPlayingMovies(list: List<Result>) {
        val adapter = HomeSliderViewPagerAdapter(childFragmentManager, list)
        binding.viewPager.adapter = adapter
        createDots(position, list.size)
        pageChangeListener(binding.viewPager, list.size)
    }

    private fun createDots(current_position: Int, size: Int) {
        binding.dotsLayout.removeAllViews()
        val dots = arrayOfNulls<ImageView>(size)
        for (i in 0 until size) {
            dots[i] = ImageView(context)
            if (i == current_position)
                dots[i]?.setImageDrawable(
                    ContextCompat.getDrawable(
                        requireContext(),
                        R.drawable.active_dots
                    )
                )
            else
                dots[i]?.setImageDrawable(
                    ContextCompat.getDrawable(
                        requireContext(),
                        R.drawable.default_dots
                    )
                )
            val params = LinearLayout.LayoutParams(
                ViewGroup.LayoutParams.WRAP_CONTENT,
                ViewGroup.LayoutParams.WRAP_CONTENT
            )
            params.height = 30
            params.width = 30

            val lineparams = LinearLayout.LayoutParams(
                ViewGroup.LayoutParams.WRAP_CONTENT,
                ViewGroup.LayoutParams.WRAP_CONTENT
            )
            lineparams.height = 3
            lineparams.width = 75
            lineparams.setMargins(16, 0, 16, 0)


            binding.dotsLayout.addView(dots[i], params)
        }
    }

}