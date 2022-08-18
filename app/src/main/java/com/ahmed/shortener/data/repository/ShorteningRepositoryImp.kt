package com.ahmed.shortener.data.repository

import com.ahmed.shortener.data.model.ShorteningUrlApiResponse
import com.ahmed.shortener.data.ApiRequestState
import com.ahmed.shortener.data.services.BaseService
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn

class ShorteningRepositoryImp(private val baseService: BaseService) : ShorteningRepository {
    override fun shortenUrl(originalUrl: String): Flow<ApiRequestState<ShorteningUrlApiResponse>> {
        return flow {
            emit(ApiRequestState.Loading)
            emit(baseService.shortenUrl(originalUrl))
        }.flowOn(Dispatchers.Default)
    }
}