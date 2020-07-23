package com.liarstudio.flickr_image_viewer.data.base.db

import androidx.room.Database
import androidx.room.RoomDatabase
import com.liarstudio.flickr_image_viewer.data.image.db.dao.ImageDao
import com.liarstudio.flickr_image_viewer.data.image.db.entity.ImageEntity

@Database(entities = [ImageEntity::class], version = 1)
abstract class AppDatabase : RoomDatabase() {
    abstract fun imageDao(): ImageDao
}
