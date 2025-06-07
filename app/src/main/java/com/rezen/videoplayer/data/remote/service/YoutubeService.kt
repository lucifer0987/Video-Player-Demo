package com.rezen.videoplayer.data.remote.service

import com.rezen.videoplayer.common.ApiEndPointConstants.API_KEY
import com.rezen.videoplayer.common.ApiEndPointConstants.HEADER_CONTENT_TYPE
import com.rezen.videoplayer.common.ApiEndPointConstants.PART
import com.rezen.videoplayer.common.ApiEndPointConstants.QUOTA_USER
import com.rezen.videoplayer.data.remote.dto.YoutubeVideoDataResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.Query

interface YoutubeService {

    @Headers(HEADER_CONTENT_TYPE)
    @GET("videos")
    suspend fun getVideoMetaData(
        @Query("key") apiKey: String = API_KEY,
        @Query("quotaUser") quotaUser: String = QUOTA_USER,
        @Query("part") part: String = PART,
        @Query("id") videoId: String
    ): Response<YoutubeVideoDataResponse>

}