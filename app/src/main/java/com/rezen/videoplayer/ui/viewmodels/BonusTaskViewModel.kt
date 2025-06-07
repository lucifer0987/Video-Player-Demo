package com.rezen.videoplayer.ui.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.rezen.videoplayer.common.UiState
import com.rezen.videoplayer.domain.model.VideoData
import com.rezen.videoplayer.domain.usecase.GetVideoDataUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class BonusTaskViewModel @Inject constructor(
    private val getVideoDataUseCase: GetVideoDataUseCase
) : ViewModel() {

    private val _youtubeVideoDataLiveData = MutableLiveData<UiState<VideoData>>()
    val youtubeVideoDataLiveData: LiveData<UiState<VideoData>> = _youtubeVideoDataLiveData

    fun fetchYoutubeVideoData(videoId: String) {
        _youtubeVideoDataLiveData.value = UiState.Loading()
        viewModelScope.launch(Dispatchers.IO) {
            _youtubeVideoDataLiveData.postValue(getVideoDataUseCase.invoke(videoId = videoId))
        }
    }

}