package com.liarstudio.flickr_image_viewer.data.image.db.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.liarstudio.flickr_image_viewer.data.image.db.entity.ImageEntity
import io.reactivex.Completable
import io.reactivex.Single

@Dao
interface ImageDao {

    @Query("SELECT * FROM image")
    fun getImages(): Single<List<ImageEntity>>

    @Insert
    fun saveImages(vararg images: ImageEntity): Completable

    @Query("DELETE FROM image")
    fun removeAllImages(): Completable
}