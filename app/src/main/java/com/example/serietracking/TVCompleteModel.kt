package com.example.serietracking

data class TVCompleteModel(
    val id: String,
    val inProduction: Boolean,
    val seasons: List<TVCompleteSeasonModel>
)

data class TVCompleteSeasonModel(
    val id: String,
    val name: String,
    val seasonNumber: Int,
    val airDate: String
)