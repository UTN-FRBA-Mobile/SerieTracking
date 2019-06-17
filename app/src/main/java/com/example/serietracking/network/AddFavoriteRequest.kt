package com.example.serietracking.network

data class AddToFavoriteRequest(val media_type: String, val media_id: Long, val favorite: Boolean)

