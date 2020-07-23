package com.liarstudio.flickr_image_viewer.ui.main

import android.content.Context
import android.graphics.Rect
import android.view.View
import androidx.recyclerview.widget.RecyclerView
import kotlin.math.roundToInt

class ImageListOffsetDecoration(context: Context) : RecyclerView.ItemDecoration() {

    private val screenWidth: Int = context.resources.displayMetrics.widthPixels
    private val firstElementStartOffset: Double = screenWidth / 10.0
    private val firstElementEndOffset: Double = screenWidth / 20.0

    private val lastElementStartOffset: Double = screenWidth / 20.0
    private val lastElementEndOffset: Double = screenWidth / 10.0

    private val verticalOffset: Double = screenWidth / 20.0

    override fun getItemOffsets(
        outRect: Rect,
        view: View,
        parent: RecyclerView,
        state: RecyclerView.State
    ) {
        val itemPosition = parent.getChildAdapterPosition(view)
        val horizontalPosition = when {
            itemPosition % 2 == 0 -> ImageHorizontalPosition.FIRST
            else -> ImageHorizontalPosition.LAST
        }
        val (startOffset, endOffset) = when (horizontalPosition) {
            ImageHorizontalPosition.FIRST -> firstElementStartOffset to firstElementEndOffset
            ImageHorizontalPosition.MIDDLE -> 0.0 to 0.0
            ImageHorizontalPosition.LAST -> lastElementStartOffset to lastElementEndOffset
        }

        outRect.set(
            startOffset.roundToInt(),
            verticalOffset.roundToInt(),
            endOffset.roundToInt(),
            verticalOffset.roundToInt()
        )
    }

    private enum class ImageHorizontalPosition {
        FIRST,
        MIDDLE,
        LAST
    }
}