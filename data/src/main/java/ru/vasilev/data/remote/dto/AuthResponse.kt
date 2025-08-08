package ru.vasilev.data.remote.dto

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class AuthResponse(
    @Json(name = "success")
    val success: Boolean,
    @Json(name = "token")
    val token: String? = null
)