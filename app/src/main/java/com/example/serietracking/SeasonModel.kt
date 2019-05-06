package com.example.serietracking

class SeasonModel {
    var _id: String? = null
    var airDate: String? = null
    var id: Int = 0
    var posterPath: String? = null
    var seasonNumber: Int = 0

    var episodes: List<Episode>? = null

    class Episode {
        var airDate: String? = null
        var episodeNumber: Int = 0
        var name: String? = null
        var id: Int = 0
        var seasonNumber: Int = 0
        var voteCount: Int = 0
    }
}