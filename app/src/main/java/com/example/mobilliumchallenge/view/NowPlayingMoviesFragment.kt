package com.example.mobilliumchallenge.view

import android.os.Bundle
import android.view.View
import com.example.mobilliumchallenge.base.BaseFragment
import com.example.mobilliumchallenge.databinding.FragmentNowPlayingMoviesBinding
import com.example.mobilliumchallenge.model.entities.common.Result
import com.example.mobilliumchallenge.utils.Constants
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

    private fun initNowPlayingMovies(){
        arguments?.let {
            nowPlayingResult  = requireArguments().getParcelable(Constants.SLIDER_INTENT_KEY)
            ImageHelper.loadImage(nowPlayingResult?.poster_path,binding.image)
            binding.infoTitle.text = nowPlayingResult?.original_title
            binding.info.text = nowPlayingResult?.title
        }
    }


}