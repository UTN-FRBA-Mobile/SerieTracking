package com.example.serietracking

data class TVCompleteModel(
    val id: Long,
    val inProduction: Boolean,
    val name: String,
    val seasons: List<TVCompleteSeasonModel>
)

data class TVCompleteSeasonModel(
    val id: String,
    val name: String,
    val seasonNumber: Long,
    val airDate: String
)