package com.backbase.assignment.ui.data.model.movie

import com.google.gson.annotations.SerializedName


data class MovieDetails(
    @SerializedName("adult")
    var adult: Boolean?, // false
    @SerializedName("backdrop_path")
    var backdropPath: String?, // /xXHZeb1yhJvnSHPzZDqee0zfMb6.jpg
    @SerializedName("belongs_to_collection")
    var belongsToCollection: BelongsToCollection?,
    @SerializedName("budget")
    var budget: Int?, // 200000000
    @SerializedName("genres")
    var genres: List<Genre?>?,
    @SerializedName("homepage")
    var homepage: String?, // https://www.thefastsaga.com
    @SerializedName("id")
    var id: Int?, // 385128
    @SerializedName("imdb_id")
    var imdbId: String?, // tt5433138
    @SerializedName("original_language")
    var originalLanguage: String?, // en
    @SerializedName("original_title")
    var originalTitle: String?, // F9
    @SerializedName("overview")
    var overview: String?, // Dominic Toretto and his crew battle the most skilled assassin and high-performance driver they've ever encountered: his forsaken brother.
    @SerializedName("popularity")
    var popularity: Double?, // 2980.038
    @SerializedName("poster_path")
    var posterPath: String?, // /bOFaAXmWWXC3Rbv4u4uM9ZSzRXP.jpg
    @SerializedName("production_companies")
    var productionCompanies: List<ProductionCompany?>?,
    @SerializedName("production_countries")
    var productionCountries: List<ProductionCountry?>?,
    @SerializedName("release_date")
    var releaseDate: String?, // 2021-05-19
    @SerializedName("revenue")
    var revenue: Int?, // 405594165
    @SerializedName("runtime")
    var runtime: Int?, // 145
    @SerializedName("spoken_languages")
    var spokenLanguages: List<SpokenLanguage?>?,
    @SerializedName("status")
    var status: String?, // Released
    @SerializedName("tagline")
    var tagline: String?, // Justice is coming.
    @SerializedName("title")
    var title: String?, // F9
    @SerializedName("video")
    var video: Boolean?, // false
    @SerializedName("vote_average")
    var voteAverage: Double?, // 7.8
    @SerializedName("vote_count")
    var voteCount: Int? // 329
) {
    data class BelongsToCollection(
        @SerializedName("backdrop_path")
        var backdropPath: String?, // /gC9BUFiROWtaMsluGYziZ6lR4OJ.jpg
        @SerializedName("id")
        var id: Int?, // 9485
        @SerializedName("name")
        var name: String?, // The Fast and the Furious Collection
        @SerializedName("poster_path")
        var posterPath: String? // /zQdytnqfsWKJlqazqfMBL2L7aql.jpg
    )

    data class Genre(
        @SerializedName("id")
        var id: Int?, // 28
        @SerializedName("name")
        var name: String? // Action
    )

    data class ProductionCompany(
        @SerializedName("id")
        var id: Int?, // 333
        @SerializedName("logo_path")
        var logoPath: String?, // /5xUJfzPZ8jWJUDzYtIeuPO4qPIa.png
        @SerializedName("name")
        var name: String?, // Original Film
        @SerializedName("origin_country")
        var originCountry: String? // US
    )

    data class ProductionCountry(
        @SerializedName("iso_3166_1")
        var iso31661: String?, // US
        @SerializedName("name")
        var name: String? // United States of America
    )

    data class SpokenLanguage(
        @SerializedName("english_name")
        var englishName: String?, // English
        @SerializedName("iso_639_1")
        var iso6391: String?, // en
        @SerializedName("name")
        var name: String? // English
    )
}