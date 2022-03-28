package com.example.mobilliumchallenge.view

import android.os.Bundle
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import androidx.core.content.ContextCompat
import androidx.core.widget.NestedScrollView
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

    private var isUpdate: Boolean = false
    private val viewModel: HomeFragmentViewModel by hiltNavGraphViewModels(R.id.nav_graph)
    private var nowPlayingPageCounter: Int? = 1
    private var upcomingPageCounter: Int? = 1
    private var position: Int = 0

    private var isLoading: Boolean = true
    private var isLastPage: Boolean = false
    var adapter: UpcomingMoviesAdapter? = null

    override fun getViewBinding(): FragmentHomeBinding {
        return FragmentHomeBinding.inflate(layoutInflater)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        observers()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.getNowPlayingMovies(nowPlayingPageCounter ?: 1, Constants.API_KEY)
        isUpdate = false
        viewModel.getUpcomingMovies(upcomingPageCounter ?: 1, Constants.API_KEY)

        binding.swipeRefreshLayout.setOnRefreshListener {
            binding.swipeRefreshLayout.isRefreshing = false
            isUpdate = false
            viewModel.getNowPlayingMovies(nowPlayingPageCounter ?: 1, Constants.API_KEY)
            viewModel.getUpcomingMovies(upcomingPageCounter ?: 1, Constants.API_KEY)
        }
        observers()
    }
    private fun observers() {

        viewModel.nowPlayingMovies.observe(viewLifecycleOwner, {
            when (it.status) {
                Status.SUCCESS -> {
                    hideProgress()
                    initNowPlayingMovies(it.data?.results ?: arrayListOf())
                    viewModel.removeObserving()
                }
                Status.ERROR -> {
                    hideProgress()
                }
                Status.LOADING -> {
                    showProgress()
                }
            }
        })


        viewModel.upComingMovies.observe(viewLifecycleOwner, {
            when (it.status) {
                Status.SUCCESS -> {
                    isLoading = false
                    hideProgress()
                    if (!isUpdate)
                        initUpComingEvents(it.data?.results ?: arrayListOf())
                    else
                        updateUpComingEvents(it.data?.results ?: arrayListOf())

                }
                Status.ERROR -> {
                    isLoading = false
                    hideProgress()
                }
                Status.LOADING -> {
                    isLoading = true
                    showProgress()
                }
            }

        })
    }

    private fun initUpComingEvents(list: List<Result>) {

        adapter = UpcomingMoviesAdapter(context, list.toMutableList())
        adapter?.listener?.observe(viewLifecycleOwner, {
            navigateAction(HomeFragmentDirections.actionHomeFragmentToMovieDetailFragment(it.id))
        })
        binding.recyclerView.layoutManager = LinearLayoutManager(context)
        binding.recyclerView.adapter = adapter
        binding.recyclerView.isNestedScrollingEnabled = false




        binding.scrollView.setOnScrollChangeListener { v: NestedScrollView?, scrollX: Int, scrollY: Int, oldScrollX: Int, oldScrollY: Int ->

            val nestedScrollView = checkNotNull(v) {
                return@setOnScrollChangeListener
            }

            val lastChild = nestedScrollView.getChildAt(nestedScrollView.childCount - 1)

            if (lastChild != null) {

                if ((scrollY >= (lastChild.measuredHeight - nestedScrollView.measuredHeight)) && scrollY > oldScrollY && !isLoading && !isLastPage) {
                    upcomingPageCounter = upcomingPageCounter?.plus(1)
                    isUpdate = true
                    viewModel.getUpcomingMovies(upcomingPageCounter ?: -1, Constants.API_KEY)
                }
            }
        }
    }

    private fun updateUpComingEvents(list: List<Result>) {
        adapter?.addItems(list)
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
            params.height = 15
            params.width = 15
            params.setMargins(4, 0, 4, 0)

            binding.dotsLayout.addView(dots[i], params)
        }
    }

}