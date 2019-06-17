package com.example.serietracking.network

data class AddToFavoriteRequest(val mediaType: String, val mediaId: Long, val favorite: Boolean)

