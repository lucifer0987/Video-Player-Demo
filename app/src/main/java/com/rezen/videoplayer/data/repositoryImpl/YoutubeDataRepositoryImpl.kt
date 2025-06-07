package com.rezen.videoplayer.data.repositoryImpl

import android.util.Log
import com.rezen.videoplayer.common.NetworkResponse
import com.rezen.videoplayer.data.mapper.toVideoData
import com.rezen.videoplayer.data.remote.YoutubeRemoteDataSource
import com.rezen.videoplayer.domain.model.VideoData
import com.rezen.videoplayer.domain.repository.YoutubeDataRepository

class YoutubeDataRepositoryImpl(
    private val youtubeRemoteDataSource: YoutubeRemoteDataSource
) : YoutubeDataRepository {
    override suspend fun getVideoMetaData(videoId: String): NetworkResponse<VideoData> {
        return when (val response = youtubeRemoteDataSource.getVideoMetaData(videoId)) {
            is NetworkResponse.Success -> {
                response.data?.items?.getOrNull(0)?.toVideoData()?.let {
                    NetworkResponse.Success(data = it)
                } ?: run {
                    Log.e("VideoDataMapper", "Error occurred during mapping")
                    NetworkResponse.Error(error = "Some error occurred, please try again later")
                }
            }

            is NetworkResponse.Error -> {
                NetworkResponse.Error(error = response.error.toString())
            }
        }
    }
}