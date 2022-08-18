package com.ahmed.shortener.data.services

import com.ahmed.shortener.data.model.ApiRequestBodyParameter
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import okhttp3.HttpUrl
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.Request
import okhttp3.RequestBody
import okhttp3.RequestBody.Companion.toRequestBody

object ApiRequestHelper {
    private const val SCHEME = "https"
    private const val HOST = "api-ssl.bitly.com"
    private const val FIRST_PATH_SEGMENT = "v4"
    private const val SECOND_PATH_SEGMENT = "shorten"
    private const val HEADER_NAME = "Authorization"
    private const val HEADER_VALUE = "Bearer 03aaef6007d8af8613a4621cbb1a79452a957e36"
    private const val BODY_PARAMETER = "long_url"
    private const val MEDIA_TYPE = "application/json"

    fun makeApiShortenLinkRequest(originalUrl: String): Request {
        return Request.Builder().apply {
            header(HEADER_NAME, HEADER_VALUE)
            url(createApiShortenUrl())
            post(createApiRequestBody(originalUrl))
        }.build()
    }

    private fun createApiShortenUrl(): HttpUrl {
        return HttpUrl.Builder().run {
            scheme(SCHEME)
            host(HOST)
            addPathSegment(FIRST_PATH_SEGMENT)
            addPathSegment(SECOND_PATH_SEGMENT)
        }.build()
    }

    private fun createApiRequestBody(originalUrl: String): RequestBody {
        return createApiRequestBodyParameter(originalUrl).toRequestBody(MEDIA_TYPE.toMediaTypeOrNull())
    }

    private fun createApiRequestBodyParameter(originalUrl: String): String {
        return Json.encodeToString(ApiRequestBodyParameter(originalUrl))
    }
}