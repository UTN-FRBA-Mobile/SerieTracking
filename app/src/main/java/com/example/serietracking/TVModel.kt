package com.example.serietracking

import java.io.Serializable

data class TVModel(val page: Int,
                   val totalResults: Int,
                   var totalPages: Int,
                   val results: MutableList<TVShow>): Serializable

data class TVShow(val posterPath: String,
                  val popularity: Double,
                  val id: Long,
                  val backdropPath: String,
                  val voteAverage: Double,
                  val overview: String,
                  val originCountry: List<String>,
                  val firstAirDate: String,
                  val genreIds: List<Int>,
                  val originalLanguage: String?,
                  val voteCount: Int,
                  val name: String,
                  val originalName: String): Serializable