package com.example.mobilliumchallenge.view

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.View
import com.example.mobilliumchallenge.base.BaseFragment
import com.example.mobilliumchallenge.databinding.FragmentNowPlayingMoviesBinding
import com.example.mobilliumchallenge.model.entities.common.Result
import com.example.mobilliumchallenge.utils.Constants
import com.example.mobilliumchallenge.utils.D.getYearFromDateString
import com.example.mobilliumchallenge.utils.ImageHelper


class NowPlayingMoviesFragment : BaseFragment<FragmentNowPlayingMoviesBinding>() {

    var nowPlayingResult: Result? = null

    override fun getViewBinding(): FragmentNowPlayingMoviesBinding {
        return FragmentNowPlayingMoviesBinding.inflate(layoutInflater)
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initNowPlayingMovies()
    }

    @SuppressLint("SetTextI18n")
    private fun initNowPlayingMovies(){
        arguments?.let {
            nowPlayingResult  = requireArguments().getParcelable(Constants.SLIDER_INTENT_KEY)
            ImageHelper.loadImage(nowPlayingResult?.backdrop_path,binding.image,ImageHelper.QUALITY.original)
            binding.infoTitle.text = nowPlayingResult?.original_title + " (" + nowPlayingResult?.release_date?.getYearFromDateString()+ ")"
            binding.info.text = nowPlayingResult?.overview
        }
    }


}