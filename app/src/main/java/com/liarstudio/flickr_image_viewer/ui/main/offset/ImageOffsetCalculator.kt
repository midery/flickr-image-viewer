package com.liarstudio.flickr_image_viewer.ui.main.offset

import com.liarstudio.flickr_image_viewer.ui.base.ScreenOrientation
import kotlin.math.roundToInt

class ImageOffsetCalculator(
    screenWidth: Int,
    screenOrientation: ScreenOrientation
) {

    private val edgeMultiplier =
        if (screenOrientation == ScreenOrientation.PORTRAIT) PORTRAIT_EDGE_MULTIPLIER else LANDSCAPE_EDGE_MULTIPLIER

    private val middleMultiplier =
        if (screenOrientation == ScreenOrientation.PORTRAIT) PORTRAIT_MIDDLE_MULTIPLIER else LANDSCAPE_MIDDLE_MULTIPLIER

    val firstElementStartOffset: Int = (screenWidth * edgeMultiplier).roundToInt()
    val firstElementEndOffset: Int = (screenWidth * middleMultiplier).roundToInt()

    val middleElementStartOffset: Int = (screenWidth * middleMultiplier).roundToInt()
    val middleElementEndOffset: Int = (screenWidth * middleMultiplier).roundToInt()

    val lastElementStartOffset: Int = (screenWidth * middleMultiplier).roundToInt()
    val lastElementEndOffset: Int = (screenWidth * edgeMultiplier).roundToInt()

    val verticalOffset: Int = (screenWidth * middleMultiplier).roundToInt()

    companion object {
        const val PORTRAIT_EDGE_MULTIPLIER = 0.1 //10% от экрана
        const val PORTRAIT_MIDDLE_MULTIPLIER = 0.05 //5% от экрана

        const val LANDSCAPE_EDGE_MULTIPLIER = 0.04 //4% от экрана
        const val LANDSCAPE_MIDDLE_MULTIPLIER = 0.02 //2% от экрана
    }
}