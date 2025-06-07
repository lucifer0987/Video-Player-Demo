package com.rezen.videoplayer.ui

import android.os.Bundle
import android.view.inputmethod.InputMethodManager
import android.widget.Toast
import androidx.activity.viewModels
import com.rezen.videoplayer.common.BaseActivity
import com.rezen.videoplayer.common.UiState
import com.rezen.videoplayer.databinding.ActivityBonusTaskBinding
import com.rezen.videoplayer.domain.repository.YoutubeDataRepository
import com.rezen.videoplayer.ui.viewmodels.BonusTaskViewModel
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class BonusTaskActivity : BaseActivity() {

    private lateinit var binding: ActivityBonusTaskBinding
    private val viewModel: BonusTaskViewModel by viewModels()

    @Inject
    lateinit var repo: YoutubeDataRepository

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityBonusTaskBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.submitBtn.setOnClickListener {
            hideKeyboard()
            val videoId = binding.videoIdEt.text.toString()
            if (videoId.isNotEmpty()) {
                viewModel.fetchYoutubeVideoData(videoId)
            } else {
                Toast.makeText(
                    this@BonusTaskActivity, "Please enter video id", Toast.LENGTH_LONG
                ).show()
            }
        }

        observeLivedata()
    }

    private fun observeLivedata() {
        viewModel.youtubeVideoDataLiveData.observe(this) {
            when (it) {
                is UiState.Error -> {
                    hideLoader()
                    Toast.makeText(
                        this@BonusTaskActivity, it.error.toString(), Toast.LENGTH_LONG
                    ).show()
                }

                is UiState.Loading -> {
                    showLoader()
                    binding.videoDataTv.text = ""
                }

                is UiState.Success -> {
                    hideLoader()
                    binding.videoDataTv.text = it.data?.toSpannableDisplayText() ?: ""
                }
            }
        }
    }

    private fun hideKeyboard() {
        binding.main.clearFocus()
        (binding.main.context.getSystemService(INPUT_METHOD_SERVICE) as? InputMethodManager)
            ?.hideSoftInputFromWindow(binding.main.windowToken, 0)
    }
}