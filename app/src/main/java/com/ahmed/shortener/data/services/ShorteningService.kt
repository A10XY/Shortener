package com.ahmed.shortener.data.services

import com.ahmed.shortener.data.ApiRequestState
import com.ahmed.shortener.data.model.ShorteningUrlApiResponse
import com.google.gson.Gson
import okhttp3.Callback
import okhttp3.OkHttpClient

class ShorteningService : BaseService {
    private val client = OkHttpClient()
    private val gson = Gson()

    override fun shortenUrl(url: String): ApiRequestState<ShorteningUrlApiResponse> {
        var state: ApiRequestState<ShorteningUrlApiResponse>
        try {
            val response = client.newCall(ApiRequestHelper.makeApiShortenLinkRequest(url)).execute()
            if (response.isSuccessful) {
                gson.fromJson(response.body?.string(), ShorteningUrlApiResponse::class.java).run {
                    state = ApiRequestState.Success(this)
                }
            } else state = ApiRequestState.Fail(response.message)
        } catch (e: Exception) {
            state = ApiRequestState.Fail(e.message!!)
        }
        return state
    }
}