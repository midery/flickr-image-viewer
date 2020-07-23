package com.liarstudio.flickr_image_viewer.data.image

import android.content.Context
import com.liarstudio.flickr_image_viewer.data.base.api.ApiProvider
import com.liarstudio.flickr_image_viewer.data.base.db.DaoProvider
import com.liarstudio.flickr_image_viewer.data.image.db.entity.ImageEntity
import com.liarstudio.flickr_image_viewer.domain.Image
import io.reactivex.Single
import java.net.UnknownHostException

class ImageRepository(context: Context) {

    private val imageApi by lazy { ApiProvider.imageApi }
    private val imageDao by lazy { DaoProvider.obtainUserDao(context) }

    fun getImages(): Single<List<Image>> {
        return imageApi.getImages().map { it.transform() }
            .flatMap(::saveImages)
            .onErrorResumeNext { error: Throwable -> loadSavedImagesWithoutNetwork(error) }
    }

    private fun saveImages(images: List<Image>): Single<List<Image>> {
        val imageEntities = images.map { ImageEntity(it.id, it.secret, it.farmId, it.serverId) }
        return imageDao.removeAllImages()
            .andThen(
                imageDao.saveImages(*imageEntities.toTypedArray())
            ).andThen(
                Single.just(images)
            )
    }

    private fun loadSavedImagesWithoutNetwork(error: Throwable): Single<List<Image>> {
        val userHasNoInternet = error is UnknownHostException
        return if (userHasNoInternet) {
            getSavedImages()
        } else {
            throw error
        }
    }

    private fun getSavedImages(): Single<List<Image>> {
        return imageDao.getImages().map { entities ->
            entities.map { imageEntity ->
                imageEntity.transform()
            }
        }
    }
}