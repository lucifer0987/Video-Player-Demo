package com.rezen.videoplayer.data.remote.dto

import com.google.gson.annotations.SerializedName

data class YoutubeVideoDataResponse(
    @SerializedName("etag")
    val etag: String? = null,
    @SerializedName("items")
    val items: List<Item?>? = null,
    @SerializedName("kind")
    val kind: String? = null,
    @SerializedName("pageInfo")
    val pageInfo: PageInfo? = null
) {
    data class Item(
        @SerializedName("etag")
        val etag: String? = null,
        @SerializedName("id")
        val id: String? = null,
        @SerializedName("kind")
        val kind: String? = null,
        @SerializedName("localizations")
        val localizations: Localizations? = null,
        @SerializedName("snippet")
        val snippet: Snippet? = null,
        @SerializedName("statistics")
        val statistics: Statistics? = null
    ) {
        data class Localizations(
            @SerializedName("en")
            val en: En? = null
        ) {
            data class En(
                @SerializedName("description")
                val description: String? = null,
                @SerializedName("title")
                val title: String? = null
            )
        }

        data class Snippet(
            @SerializedName("categoryId")
            val categoryId: String? = null,
            @SerializedName("channelId")
            val channelId: String? = null,
            @SerializedName("channelTitle")
            val channelTitle: String? = null,
            @SerializedName("defaultAudioLanguage")
            val defaultAudioLanguage: String? = null,
            @SerializedName("defaultLanguage")
            val defaultLanguage: String? = null,
            @SerializedName("description")
            val description: String? = null,
            @SerializedName("liveBroadcastContent")
            val liveBroadcastContent: String? = null,
            @SerializedName("localized")
            val localized: Localized? = null,
            @SerializedName("publishedAt")
            val publishedAt: String? = null,
            @SerializedName("thumbnails")
            val thumbnails: Thumbnails? = null,
            @SerializedName("title")
            val title: String? = null
        ) {
            data class Localized(
                @SerializedName("description")
                val description: String? = null,
                @SerializedName("title")
                val title: String? = null
            )

            data class Thumbnails(
                @SerializedName("default")
                val default: Default? = null,
                @SerializedName("high")
                val high: High? = null,
                @SerializedName("maxres")
                val maxres: Maxres? = null,
                @SerializedName("medium")
                val medium: Medium? = null,
                @SerializedName("standard")
                val standard: Standard? = null
            ) {
                data class Default(
                    @SerializedName("height")
                    val height: Int? = null,
                    @SerializedName("url")
                    val url: String? = null,
                    @SerializedName("width")
                    val width: Int? = null
                )

                data class High(
                    @SerializedName("height")
                    val height: Int? = null,
                    @SerializedName("url")
                    val url: String? = null,
                    @SerializedName("width")
                    val width: Int? = null
                )

                data class Maxres(
                    @SerializedName("height")
                    val height: Int? = null,
                    @SerializedName("url")
                    val url: String? = null,
                    @SerializedName("width")
                    val width: Int? = null
                )

                data class Medium(
                    @SerializedName("height")
                    val height: Int? = null,
                    @SerializedName("url")
                    val url: String? = null,
                    @SerializedName("width")
                    val width: Int? = null
                )

                data class Standard(
                    @SerializedName("height")
                    val height: Int? = null,
                    @SerializedName("url")
                    val url: String? = null,
                    @SerializedName("width")
                    val width: Int? = null
                )
            }
        }

        data class Statistics(
            @SerializedName("commentCount")
            val commentCount: String? = null,
            @SerializedName("favoriteCount")
            val favoriteCount: String? = null,
            @SerializedName("likeCount")
            val likeCount: String? = null,
            @SerializedName("viewCount")
            val viewCount: String? = null
        )
    }

    data class PageInfo(
        @SerializedName("resultsPerPage")
        val resultsPerPage: Int? = null,
        @SerializedName("totalResults")
        val totalResults: Int? = null
    )
}
