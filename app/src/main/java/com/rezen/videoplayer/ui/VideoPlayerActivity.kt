package com.rezen.videoplayer.ui

import android.os.Bundle
import android.view.View
import android.widget.PopupMenu
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.google.android.exoplayer2.C
import com.google.android.exoplayer2.Format
import com.google.android.exoplayer2.MediaItem
import com.google.android.exoplayer2.Player
import com.google.android.exoplayer2.trackselection.MappingTrackSelector
import com.google.android.exoplayer2.trackselection.TrackSelectionOverride
import com.google.android.exoplayer2.util.MimeTypes
import com.rezen.videoplayer.databinding.ActivityVideoPlayerBinding
import com.rezen.videoplayer.ui.viewmodels.VideoPlayerViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class VideoPlayerActivity : AppCompatActivity() {

    private lateinit var binding: ActivityVideoPlayerBinding
    private val viewModel: VideoPlayerViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityVideoPlayerBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.qualityButton.setOnClickListener {
            // Show popup menu to select video quality
            showQualityPopup()
        }

    }

    override fun onResume() {
        super.onResume()
        initializePlayer()
    }

    /**
     * Initialize ExoPlayer with DRM and media source
     */
    private fun initializePlayer() {
        val context = application
        val player = viewModel.initializePlayerIfNeeded(context)

        binding.playerView.player = player

        val drmConfiguration = MediaItem.DrmConfiguration.Builder(C.WIDEVINE_UUID)
            .setLicenseUri(viewModel.drmLicenseUrl)
            .build()

        val mediaItem = MediaItem.Builder()
            .setUri(viewModel.manifestUrl)
            .setDrmConfiguration(drmConfiguration)
            .setMimeType(MimeTypes.APPLICATION_MPD)
            .build()

        if (player.mediaItemCount == 0) {
            player.setMediaItem(mediaItem)
            player.prepare()
        }

        viewModel.restorePlayerState()

        player.addListener(object : Player.Listener {
            override fun onPlaybackStateChanged(state: Int) {
                binding.progressBar.visibility =
                    if (state == Player.STATE_BUFFERING) View.VISIBLE else View.GONE
            }

            override fun onIsLoadingChanged(isLoading: Boolean) {
                binding.progressBar.visibility = if (isLoading) View.VISIBLE else View.GONE
            }
        })
    }


    /**
     * Shows a popup menu with available video qualities from the manifest
     */
    private fun showQualityPopup() {
        // Get current track info (what tracks are available)
        val mappedTrackInfo = viewModel.trackSelector?.currentMappedTrackInfo ?: return

        // Find the index of the video renderer (tracks related to video)
        val rendererIndex = getVideoRendererIndex(mappedTrackInfo) ?: return

        // Get all track groups (different qualities)
        val trackGroups = mappedTrackInfo.getTrackGroups(rendererIndex)

        val popupMenu = PopupMenu(this, binding.qualityButton)

        val qualityMap = mutableMapOf<String, TrackSelectionOverride>()
        popupMenu.menu.add("Auto")

        // Loop through all available tracks
        for (groupIndex in 0 until trackGroups.length) {
            val group = trackGroups[groupIndex]
            for (trackIndex in 0 until group.length) {
                val format = group.getFormat(trackIndex)
                val height = format.height
                val width = format.width

                // Only add qualities with valid resolution info
                if (height != Format.NO_VALUE && width != Format.NO_VALUE) {
                    val label = "${height}p"
                    if (!qualityMap.containsKey(label)) {
                        // Create override to select this quality track
                        val override = TrackSelectionOverride(group, listOf(trackIndex))
                        qualityMap[label] = override
                        popupMenu.menu.add(label)
                    }
                }
            }
        }

        // When user selects a quality option
        popupMenu.setOnMenuItemClickListener { item ->
            viewModel.trackSelector?.let { trackSelector ->
                val label = item.title.toString()
                val builder = trackSelector.buildUponParameters()

                if (label == "Auto") {
                    // Clear all overrides to enable adaptive streaming
                    builder.clearOverridesOfType(C.TRACK_TYPE_VIDEO)
                } else {
                    // Set override to selected quality track
                    val override = qualityMap[label]
                    if (override != null) {
                        builder.setOverrideForType(override)
                    }
                }
                trackSelector.setParameters(builder)
                true
            } ?: false
        }

        popupMenu.show()
    }

    /**
     * Finds the video renderer index from MappedTrackInfo
     */
    private fun getVideoRendererIndex(trackInfo: MappingTrackSelector.MappedTrackInfo): Int? {
        for (i in 0 until trackInfo.rendererCount) {
            if (trackInfo.getRendererType(i) == C.TRACK_TYPE_VIDEO) {
                return i
            }
        }
        return null
    }

    override fun onStop() {
        super.onStop()
        viewModel.releasePlayer()
    }

}