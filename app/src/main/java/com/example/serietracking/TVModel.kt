package com.example.serietracking
import com.google.gson.annotations.SerializedName

class TVModel {
    var page: Int = 0
    var total_results: Int = 0
    var total_pages: Int = 0
    var results: List<ResultsEntity>? = null

    class ResultsEntity {
        @SerializedName("poster_path") var posterPath: String? = null
        var popularity: Double = 0.toDouble()
        var id: Int = 0
        var backdrop_path: String? = null
        var vote_average: Double = 0.toDouble()
        var overview: String? = null
        var origin_country: List<String>? = null
        var first_air_date: String? = null
        var genre_ids: List<Int>? = null
        var original_language: String? = null
        var vote_count: Int = 0
        var name: String? = null
        var original_name: String? = null
    }
}