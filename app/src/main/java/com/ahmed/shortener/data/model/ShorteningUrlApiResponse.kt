package com.ahmed.shortener.data.model

import com.google.gson.annotations.SerializedName

data class ShorteningUrlApiResponse(
    @SerializedName("link")
    val shortenedLink: String,
)
