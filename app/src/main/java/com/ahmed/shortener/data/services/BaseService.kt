package com.ahmed.shortener.data.services

import com.ahmed.shortener.data.ApiRequestState
import com.ahmed.shortener.data.model.ShorteningUrlApiResponse

interface BaseService {
    fun shortenUrl(url: String): ApiRequestState<ShorteningUrlApiResponse>
}