package com.rezen.videoplayer.domain.usecase

import com.rezen.videoplayer.common.NetworkResponse
import com.rezen.videoplayer.common.UiState
import com.rezen.videoplayer.domain.model.VideoData
import com.rezen.videoplayer.domain.repository.YoutubeDataRepository
import javax.inject.Inject

class GetVideoDataUseCase @Inject constructor(
    private val youtubeDataRepository: YoutubeDataRepository
) {
    suspend operator fun invoke(videoId: String): UiState<VideoData> {
        return when (val result = youtubeDataRepository.getVideoMetaData(videoId)) {
            is NetworkResponse.Success -> UiState.Success(result.data)
            is NetworkResponse.Error -> UiState.Error(result.error)
        }
    }
}