package com.ahmed.shortener.data.repository

import com.ahmed.shortener.data.model.ShorteningUrlApiResponse
import com.ahmed.shortener.data.ApiRequestState
import kotlinx.coroutines.flow.Flow

interface ShorteningRepository {
    fun shortenUrl(originalUrl: String): Flow<ApiRequestState<ShorteningUrlApiResponse>>
}