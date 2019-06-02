package com.example.serietracking

data class SeasonModel(val _id: String,
                       val airDate: String,
                       val id: Int,
                       val posterPath: String,
                       val seasonNumber: Int,
                       val episodes: List<Episode>)

data class Episode(val airDate: String,
                   var episodeNumber: Int,
                   var name: String,
                   var id: Int,
                   var seasonNumber: Int,
                   var voteCount: Int)