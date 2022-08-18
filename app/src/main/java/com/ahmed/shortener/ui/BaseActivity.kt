package com.ahmed.shortener.ui

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.viewbinding.ViewBinding
import com.ahmed.shortener.data.services.LocaleService

abstract class BaseActivity<VB : ViewBinding> : AppCompatActivity() {
    abstract val logTag: String
    abstract val bindingInflater : (LayoutInflater) -> VB
    private var _binding : ViewBinding? = null

    val binding get() = _binding as VB

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = bindingInflater(layoutInflater)
        setContentView(requireNotNull(_binding).root)
        setUp()
        addCallbacks()
    }

    override fun attachBaseContext(newBase: Context?) {
        super.attachBaseContext(newBase)
        LocaleService.updateBaseContextLocale(newBase!!)
    }

    abstract fun setUp()
    abstract fun addCallbacks()

    protected fun log(value: Any){
        Log.v(logTag, value.toString())
    }

    protected fun showToast(message: String){
        Toast.makeText(
            this,
            message,
            Toast.LENGTH_SHORT
        ).show()
    }
}