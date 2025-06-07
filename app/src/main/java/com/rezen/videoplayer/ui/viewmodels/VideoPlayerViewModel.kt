package com.rezen.videoplayer.ui.viewmodels

import android.app.Application
import androidx.lifecycle.ViewModel
import com.google.android.exoplayer2.ExoPlayer
import com.google.android.exoplayer2.trackselection.DefaultTrackSelector
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class VideoPlayerViewModel @Inject constructor() : ViewModel() {
    // DRM license server URL (Widevine DRM)
    val drmLicenseUrl = "https://cwip-shaka-proxy.appspot.com/no_auth"

    // Manifest URL (MPD for MPEG-DASH streaming)
    val manifestUrl = "https://bitmovin-a.akamaihd.net/content/art-of-motion_drm/mpds/11331.mpd"

    private var player: ExoPlayer? = null
    var trackSelector: DefaultTrackSelector? = null

    private var playbackPosition: Long = 0
    private var currentWindow: Int = 0
    private var playWhenReady = true

    fun initializePlayerIfNeeded(context: Application): ExoPlayer {
        if (trackSelector == null) {
            trackSelector = DefaultTrackSelector(context)
        }

        if (player == null) {
            player = ExoPlayer.Builder(context)
                .setTrackSelector(trackSelector ?: DefaultTrackSelector(context))
                .build()
        }

        return player ?: throw IllegalStateException("Player cannot be null")
    }

    fun releasePlayer() {
        player?.let {
            playbackPosition = it.currentPosition
            currentWindow = it.currentMediaItemIndex
            playWhenReady = it.playWhenReady
            it.release()
        }
        player = null
        trackSelector = null
    }

    fun restorePlayerState() {
        player?.seekTo(currentWindow, playbackPosition)
        player?.playWhenReady = playWhenReady
    }

}