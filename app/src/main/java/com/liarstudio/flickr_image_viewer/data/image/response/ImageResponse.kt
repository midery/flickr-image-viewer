package com.liarstudio.flickr_image_viewer.data.image.response

import com.google.gson.annotations.SerializedName
import com.liarstudio.flickr_image_viewer.data.base.Transformable
import com.liarstudio.flickr_image_viewer.domain.Image

class ImageResponse(
    @SerializedName("id") val id: String?,
    @SerializedName("secret") val secret: String?,
    @SerializedName("farm") val farm: String?,
    @SerializedName("server") val server: String?
) : Transformable<Image> {
    override fun transform(): Image = Image(
        id ?: "",
        farm ?: "",
        server ?: "",
        secret ?: ""
    )
}