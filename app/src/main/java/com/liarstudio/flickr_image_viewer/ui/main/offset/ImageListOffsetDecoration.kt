package com.liarstudio.flickr_image_viewer.ui.main.offset

import android.content.Context
import android.graphics.Rect
import android.view.View
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.liarstudio.flickr_image_viewer.ui.base.ScreenOrientation

class ImageListOffsetDecoration(
    context: Context,
    screenOrientation: ScreenOrientation
) : RecyclerView.ItemDecoration() {

    private val screenWidth: Int = context.resources.displayMetrics.widthPixels
    private val offsetCalculator = ImageOffsetCalculator(screenWidth, screenOrientation)

    override fun getItemOffsets(
        outRect: Rect,
        view: View,
        parent: RecyclerView,
        state: RecyclerView.State
    ) {
        val itemPosition = parent.getChildAdapterPosition(view)
        val layoutManager = parent.layoutManager
        val spanCount = (layoutManager as? GridLayoutManager)?.spanCount ?: 1

        val horizontalPosition = when {
            (itemPosition + 1) % spanCount == 1 -> ImageHorizontalPosition.FIRST
            (itemPosition + 1) % spanCount == 0 -> ImageHorizontalPosition.LAST
            else -> ImageHorizontalPosition.MIDDLE
        }
        val (startOffset, endOffset) = when (horizontalPosition) {
            ImageHorizontalPosition.FIRST ->
                offsetCalculator.firstElementStartOffset to offsetCalculator.firstElementEndOffset
            ImageHorizontalPosition.MIDDLE ->
                offsetCalculator.middleElementStartOffset to offsetCalculator.middleElementEndOffset
            ImageHorizontalPosition.LAST ->
                offsetCalculator.lastElementStartOffset to offsetCalculator.lastElementEndOffset

        }
        val verticalOffset = offsetCalculator.verticalOffset

        outRect.set(startOffset, verticalOffset, endOffset, verticalOffset)
    }

    private enum class ImageHorizontalPosition {
        FIRST,
        MIDDLE,
        LAST
    }
}