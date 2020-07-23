package com.liarstudio.flickr_image_viewer.ui.base

import android.content.Context
import android.util.AttributeSet
import android.view.View
import androidx.cardview.widget.CardView

open class SquareCardView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : CardView(context, attrs, defStyleAttr) {

    private val ratio: Float = 1.0f

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        super.onMeasure(widthMeasureSpec, findFixedMeasureSpec(widthMeasureSpec))
    }

    private fun findFixedMeasureSpec(widthMeasureSpec: Int): Int {
        val originalWidth = MeasureSpec.getSize(widthMeasureSpec)
        val newHeight = originalWidth * ratio
        return MeasureSpec.makeMeasureSpec(newHeight.toInt(), MeasureSpec.EXACTLY)
    }
}
