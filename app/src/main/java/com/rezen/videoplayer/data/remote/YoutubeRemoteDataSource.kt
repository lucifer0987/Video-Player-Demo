package com.rezen.videoplayer.data.remote

import com.rezen.videoplayer.common.NetworkResponse
import com.rezen.videoplayer.data.remote.dto.YoutubeVideoDataResponse

interface YoutubeRemoteDataSource {
    suspend fun getVideoMetaData(videoId: String): NetworkResponse<YoutubeVideoDataResponse>
}
