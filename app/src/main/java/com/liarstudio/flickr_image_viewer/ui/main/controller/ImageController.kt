package com.liarstudio.flickr_image_viewer.ui.main.controller

import android.view.ViewGroup
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.request.RequestOptions
import com.liarstudio.flickr_image_viewer.R
import com.liarstudio.flickr_image_viewer.domain.Image
import kotlinx.android.synthetic.main.list_item_image.view.*
import ru.surfstudio.android.easyadapter.controller.BaseItemController
import ru.surfstudio.android.easyadapter.controller.BindableItemController
import ru.surfstudio.android.easyadapter.holder.BindableViewHolder

class ImageController(
    private val onImageSelected: (Image) -> Unit
) : BindableItemController<Image, ImageController.Holder>() {

    override fun getItemId(data: Image): String = data.imageUrl

    override fun createViewHolder(parent: ViewGroup) = Holder(parent)

    inner class Holder(parent: ViewGroup) :
        BindableViewHolder<Image>(parent, R.layout.list_item_image) {

        val imageIv = itemView.list_image_iv
        val containerView = itemView

        override fun bind(data: Image) {
            containerView.setOnClickListener { onImageSelected(data) }
            Glide.with(imageIv)
                .load(data.imageUrl)
                .apply(RequestOptions().centerCrop())
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .into(imageIv)
        }
    }
}