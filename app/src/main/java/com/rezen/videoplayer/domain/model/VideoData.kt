package com.rezen.videoplayer.domain.model

import android.graphics.Typeface
import android.text.SpannableString
import android.text.Spanned
import android.text.style.StyleSpan
import java.time.ZoneId
import java.time.ZonedDateTime
import java.time.format.DateTimeFormatter

data class VideoData(
    val title: String? = null,
    val description: String? = null,
    val thumbnailUrl: String? = null,
    val publishedAt: String? = null,
    val viewCount: String? = null,
    val likeCount: String? = null,
    val commentCount: String? = null
) {
    fun toSpannableDisplayText(): SpannableString {
        val rawText = """
Title: ${title.orEmpty()}
Published At: ${publishedAt?.toLocalFormattedDate().orEmpty()}
Views: ${viewCount.orEmpty()}
Likes: ${likeCount.orEmpty()}
Comments: ${commentCount.orEmpty()}
Description: ${description.orEmpty()}
""".trimIndent()

        val spannable = SpannableString(rawText)

        val lines = rawText.lines()
        var start = 0

        for (line in lines) {
            val colonIndex = line.indexOf(":")
            if (colonIndex != -1) {
                spannable.setSpan(
                    StyleSpan(Typeface.BOLD),
                    start,
                    start + colonIndex + 1,
                    Spanned.SPAN_EXCLUSIVE_EXCLUSIVE
                )
            }
            start += line.length + 1
        }

        return spannable
    }

    fun String.toLocalFormattedDate(): String {
        return try {
            val parsedDate = ZonedDateTime.parse(this)
            val formatter = DateTimeFormatter.ofPattern("dd MMM yyyy, hh:mm a")
                .withZone(ZoneId.systemDefault())

            formatter.format(parsedDate)
        } catch (e: Exception) {
            this // fallback to original if parsing fails
        }
    }

}