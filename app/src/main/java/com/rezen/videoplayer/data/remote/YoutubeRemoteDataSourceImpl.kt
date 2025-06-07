package com.rezen.videoplayer.data.remote

import com.rezen.videoplayer.common.NetworkResponse
import com.rezen.videoplayer.data.remote.dto.YoutubeVideoDataResponse
import com.rezen.videoplayer.data.remote.service.YoutubeService

internal class YoutubeRemoteDataSourceImpl(
    private val youtubeService: YoutubeService
) : YoutubeRemoteDataSource {
    override suspend fun getVideoMetaData(videoId: String): NetworkResponse<YoutubeVideoDataResponse> {
        val response = youtubeService.getVideoMetaData(videoId = videoId)
        val baseResponse = response.body() as YoutubeVideoDataResponse
        return if (response.isSuccessful) {
            NetworkResponse.Success(data = baseResponse)
        } else {
            NetworkResponse.Error(error = "Some unknown error occurred")
        }
    }
}
