package com.example.mobilliumchallenge.base

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.NavDirections
import androidx.navigation.fragment.findNavController
import androidx.viewbinding.ViewBinding
import com.example.mobilliumchallenge.utils.ProgressPanel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

abstract class BaseFragment<VB : ViewBinding> : Fragment() {

    private var _binding: VB? = null
    lateinit var progress: ProgressPanel
    val binding get() = _binding!!

    private val _validEditTexts = MutableStateFlow<Boolean>(false)
    val validEditTexts: StateFlow<Boolean>
        get() = _validEditTexts

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = getViewBinding()
        progress= ProgressPanel(this)
        return binding.root
    }

    abstract fun getViewBinding(): VB

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
    fun showProgress(){
        progress.showProgress()
    }

    fun hideProgress(){
        progress.hideProgress()
    }
    fun navigateAction(actionId: NavDirections) {
        findNavController().navigate(actionId)
    }


}