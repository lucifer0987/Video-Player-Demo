package com.rezen.videoplayer.ui

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.rezen.videoplayer.databinding.ActivityMainBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.videoPlayer.setOnClickListener {
            startActivity(Intent(this, VideoPlayerActivity::class.java))
        }

        binding.bonusTask.setOnClickListener {
            startActivity(Intent(this, BonusTaskActivity::class.java))
        }

    }
}