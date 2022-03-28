package com.example.mobilliumchallenge.view

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.hilt.navigation.fragment.hiltNavGraphViewModels
import com.example.mobilliumchallenge.R
import com.example.mobilliumchallenge.base.BaseFragment
import com.example.mobilliumchallenge.databinding.FragmentMovieDetailBinding
import com.example.mobilliumchallenge.model.entities.details.MovieDetailsResponse
import com.example.mobilliumchallenge.utils.Constants
import com.example.mobilliumchallenge.utils.D.getDateFromDateString
import com.example.mobilliumchallenge.utils.D.getYearFromDateString
import com.example.mobilliumchallenge.utils.ImageHelper
import com.example.mobilliumchallenge.utils.Status
import com.example.mobilliumchallenge.viewmodel.HomeFragmentViewModel
import com.example.mobilliumchallenge.viewmodel.MovieDetailViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MovieDetailFragment : BaseFragment<FragmentMovieDetailBinding>() {

    private val viewModel: MovieDetailViewModel by hiltNavGraphViewModels(R.id.nav_graph)


    override fun getViewBinding(): FragmentMovieDetailBinding {
        return FragmentMovieDetailBinding.inflate(layoutInflater)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        observers()
        arguments?.let {
            val movieId = MovieDetailFragmentArgs.fromBundle(it).movieId
            viewModel.getMovieDetails(movieId, Constants.API_KEY)
        }


    }

    private fun observers() {
        viewModel.movieDetails.observe(viewLifecycleOwner, {
            when (it.status) {
                Status.SUCCESS -> {
                    hideProgress()
                    initMovieDetails(it.data)
                }
                Status.ERROR -> {
                    hideProgress()
                }
                Status.LOADING -> {
                    showProgress()
                }
            }
        })

    }

    private fun initMovieDetails(data: MovieDetailsResponse?) {
        data?.let {data->
            ImageHelper.loadImage(data.backdrop_path,binding.movieImage,ImageHelper.QUALITY.original)
            binding.rate.text = data.vote_average.toString()
            binding.date.text = data.release_date.getDateFromDateString()
            binding.movieName.text = data.original_title
            binding.movieDetails.text = data.overview
        }

    }
}