package com.rezen.videoplayer.domain.repository

import com.rezen.videoplayer.common.NetworkResponse
import com.rezen.videoplayer.data.remote.dto.YoutubeVideoDataResponse
import com.rezen.videoplayer.domain.model.VideoData

interface YoutubeDataRepository {
    suspend fun getVideoMetaData(videoId: String): NetworkResponse<VideoData>
}