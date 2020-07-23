package com.liarstudio.flickr_image_viewer.domain

import java.io.Serializable

/**
 *                 "id": "50142909148",
"owner": "186935608@N06",
"secret": "e80e93cae4",
"server": "65535",
"farm": 66,
"title": "Api",
"ispublic": 1,
"isfriend": 0,
"isfamily": 0

 */
data class Image(
    private val id: String = "",
    private val farmId: String = "",
    private val serverId: String = "",
    private val secret: String = ""
) : Serializable {

    val imageUrl: String
        get() = "https://farm${farmId}.staticflickr.com/$serverId/${id}_${secret}.jpg"


}