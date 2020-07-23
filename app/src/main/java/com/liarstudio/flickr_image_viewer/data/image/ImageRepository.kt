package com.liarstudio.flickr_image_viewer.data.image

import com.liarstudio.flickr_image_viewer.data.base.RetrofitService
import com.liarstudio.flickr_image_viewer.domain.Image
import io.reactivex.Observable

class ImageRepository {

    private val imageApi by lazy { RetrofitService.imageApi }

    fun getImages(): Observable<List<Image>> {
        return imageApi.getImages().map { it.transform() }
    }
}