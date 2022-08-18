package com.ahmed.shortener.data.model

import kotlinx.serialization.Serializable

@Serializable
data class ApiRequestBodyParameter(
    val long_url: String,
)