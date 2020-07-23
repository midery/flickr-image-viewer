package com.liarstudio.flickr_image_viewer.data.image.response

import com.google.gson.annotations.SerializedName
import com.liarstudio.flickr_image_viewer.data.base.Transformable
import com.liarstudio.flickr_image_viewer.domain.Image

class GetImagesResponse(
    @SerializedName("photos") val photos: ImageListResponse?
) : Transformable<List<Image>> {
    override fun transform(): List<Image> {
        return photos?.transform()?.take(20) ?: emptyList()
    }
}