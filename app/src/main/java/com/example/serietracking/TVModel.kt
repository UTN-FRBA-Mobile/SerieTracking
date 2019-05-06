package com.example.serietracking

import com.google.gson.annotations.SerializedName

class TVModel {
    var page: Int = 0
    var total_results: Int = 0
    var total_pages: Int = 0
    var results: List<TVShow>? = null

    class TVShow {
        @SerializedName("poster_path") var posterPath: String? = null
        var popularity: Double = 0.toDouble()
        var id: Int = 0
        var backdropPath: String? = null
        var voteAverage: Double = 0.toDouble()
        var overview: String? = null
        var originCountry: List<String>? = null
        var firstAirDate: String? = null
        var genreIds: List<Int>? = null
        var originalLanguage: String? = null
        var voteCount: Int = 0
        var name: String? = null
        var original_name: String? = null
    }
}