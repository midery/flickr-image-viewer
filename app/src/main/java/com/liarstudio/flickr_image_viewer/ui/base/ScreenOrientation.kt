package com.liarstudio.flickr_image_viewer.ui.base

import android.content.res.Configuration

enum class ScreenOrientation(val value: Int) {
    PORTRAIT(Configuration.ORIENTATION_PORTRAIT),
    LANDSCAPE(Configuration.ORIENTATION_LANDSCAPE);

    companion object {
        fun getByValue(orientation: Int): ScreenOrientation =
            values().find { it.value == orientation } ?: PORTRAIT
    }
}