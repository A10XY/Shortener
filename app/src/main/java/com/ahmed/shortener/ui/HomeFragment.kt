package com.ahmed.shortener.ui

import android.view.View
import androidx.annotation.RawRes
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.ahmed.shortener.R
import com.ahmed.shortener.data.ApiRequestState
import com.ahmed.shortener.data.model.ShorteningUrlApiResponse
import com.ahmed.shortener.data.repository.ShorteningRepositoryImp
import com.ahmed.shortener.data.services.ShorteningService
import com.ahmed.shortener.databinding.FragmentHomeBinding
import com.ahmed.shortener.utils.*
import com.airbnb.lottie.LottieAnimationView
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.onCompletion
import kotlinx.coroutines.launch

class HomeFragment : BaseFragment<FragmentHomeBinding>(FragmentHomeBinding::inflate) {
    override val logTag: String = Constants.MAIN_ACTIVITY_LOG_TAG

    override fun setUpOnCreateView() {
        hideShortenedLinkInfo()
    }

    override fun addCallbacks() {
        binding.shortenButton.setOnClickListener {
            val url = binding.linkEdittext.text.toString()
            if (isValidUrl(url)) shortenUrl(url) else showNotValidUrlWarning()
        }
        binding.settingsIcon.setOnClickListener {
            navigateToFragment(requireActivity() as AppCompatActivity, R.id.fragment_container_view_all, SettingsFragment())
        }
    }

    private fun shortenUrl(url: String) {
        val shorteningService = ShorteningService()
        val shorteningRepositoryImp = ShorteningRepositoryImp(shorteningService)
        val flow = shorteningRepositoryImp.shortenUrl(url)
        lifecycleScope.launch {
            flow.onCompletion {
                log("Flow Completed")
            }.catch {
                log("Flow Error happened!")
            }.collect {
                onApiResponse(it)
            }
        }
    }

    private fun onApiResponse(state: ApiRequestState<ShorteningUrlApiResponse>) {
        when (state) {
            is ApiRequestState.Loading -> onLoadingApiResponse()
            is ApiRequestState.Success -> onApiResponseSuccess(state.data)
            is ApiRequestState.Fail -> onApiResponseFail(state.message)
        }
    }

    private fun onLoadingApiResponse() {
        hideShortenedLinkInfo()
        showLoadingProgress()
    }

    private fun onApiResponseSuccess(shorteningUrlApiResponse: ShorteningUrlApiResponse) {
        hideLoadingProgress()
        prepareShorteningUrlApiResponseData(shorteningUrlApiResponse)
        showShortenedLinkInfo()
    }

    private fun onApiResponseFail(errorMessage: String) {
        hideLoadingProgress()
        if (errorMessage == Constants.NO_INTERNET_CONNECTION_EXCEPTION) showConnectionError() else showServerError()
    }

    private fun prepareShorteningUrlApiResponseData(shorteningUrlApiResponse: ShorteningUrlApiResponse) {
        binding.shortenedLink.text = shorteningUrlApiResponse.shortenedLink
        addShortenedLinkInfoListeners(shorteningUrlApiResponse.shortenedLink)
    }

    private fun addShortenedLinkInfoListeners(shortenedUrl: String) {
        binding.copyButton.setOnClickListener {
            copyShortenedUrlToClipboard(this.requireContext(), shortenedUrl)
        }
        binding.shareButton.setOnClickListener {
            shareShortenedUrl(this.requireContext(), shortenedUrl)
        }
    }

    private fun showShortenedLinkInfo() {
        binding.run {
            shortenedLinkDescription.visibility = View.VISIBLE
            shortenedLink.visibility = View.VISIBLE
            copyButton.visibility = View.VISIBLE
            shareButton.visibility = View.VISIBLE
            buttonsLinearLayout.visibility = View.VISIBLE
            playLottieAnimation(linkAnimationView, R.raw.shortener)
        }
    }

    private fun hideShortenedLinkInfo() {
        binding.run {
            shortenedLinkDescription.visibility = View.INVISIBLE
            shortenedLink.visibility = View.INVISIBLE
            copyButton.visibility = View.INVISIBLE
            shareButton.visibility = View.INVISIBLE
            buttonsLinearLayout.visibility = View.INVISIBLE
        }
    }

    private fun showLoadingProgress() {
        binding.loadingAnimationView.run {
            visibility = View.VISIBLE
            playAnimation()
        }
    }

    private fun hideLoadingProgress() {
        binding.loadingAnimationView.run {
            visibility = View.INVISIBLE
            cancelAnimation()
        }
    }

    private fun showConnectionError() {
        playLottieAnimation(binding.linkAnimationView, R.raw.no_internet)
        showDefaultLongSnackBar(this.requireContext(), binding.root, "Check your internet connection and try again!")
    }

    private fun showServerError() {
        playLottieAnimation(binding.linkAnimationView, R.raw.server_error)
        showDefaultLongSnackBar(this.requireContext(), binding.root, "Server error, nothing you can do!")
    }

    private fun showNotValidUrlWarning() {
        playLottieAnimation(binding.linkAnimationView, R.raw.warning)
        showDefaultLongSnackBar(this.requireContext(), binding.root, "Not a valid url, try again!")
    }

    private fun playLottieAnimation(lottieAnimationView: LottieAnimationView, @RawRes rawRes: Int) {
        lottieAnimationView.run {
            setAnimation(rawRes)
            playAnimation()
        }
    }
}