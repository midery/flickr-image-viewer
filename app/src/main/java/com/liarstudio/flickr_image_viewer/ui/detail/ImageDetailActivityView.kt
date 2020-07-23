package com.liarstudio.flickr_image_viewer.ui.detail

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.liarstudio.flickr_image_viewer.R
import kotlinx.android.synthetic.main.activity_image_detail.*

class ImageDetailActivityView : AppCompatActivity() {

    companion object {
        const val IMAGE_DETAIL_URL_EXTRA = "IMAGE_DETAIL_URL"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_image_detail)

        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        val imageUrl = intent.getStringExtra(IMAGE_DETAIL_URL_EXTRA)
        Glide.with(image_detail_iv)
            .load(imageUrl)
            .diskCacheStrategy(DiskCacheStrategy.ALL)
            .into(image_detail_iv)

    }
}