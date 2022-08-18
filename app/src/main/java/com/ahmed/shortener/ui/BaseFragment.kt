package com.ahmed.shortener.ui

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.viewbinding.ViewBinding

abstract class BaseFragment<VB: ViewBinding>(private val invokeMethod: (LayoutInflater, ViewGroup?, Boolean) -> VB) : Fragment() {
    abstract val logTag: String
    abstract fun setUpOnCreateView()
    abstract fun addCallbacks()

    private var _binding: VB? = null
    val binding: VB get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = invokeMethod.invoke(layoutInflater, container, false)
        setUpOnCreateView()
        addCallbacks()
        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    protected fun log(value: Any) {
        Log.d(logTag, value.toString())
    }
}