package com.rezen.videoplayer.data.mapper

import com.rezen.videoplayer.data.remote.dto.YoutubeVideoDataResponse
import com.rezen.videoplayer.domain.model.VideoData

fun YoutubeVideoDataResponse.Item?.toVideoData(): VideoData? {
    if (this == null) return null
    return VideoData(
        title = snippet?.title,
        description = snippet?.description,
        thumbnailUrl = snippet?.thumbnails?.default?.url,
        publishedAt = snippet?.publishedAt,
        viewCount = statistics?.viewCount,
        likeCount = statistics?.likeCount,
        commentCount = statistics?.commentCount
    )
}