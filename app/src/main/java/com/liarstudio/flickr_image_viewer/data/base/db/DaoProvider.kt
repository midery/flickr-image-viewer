package com.liarstudio.flickr_image_viewer.data.base.db

import android.content.Context
import androidx.room.Room
import com.liarstudio.flickr_image_viewer.data.image.db.dao.ImageDao

object DaoProvider {

    private var database: AppDatabase? = null


    fun obtainUserDao(context: Context): ImageDao {
        ensureDatabaseInitialized(context)
        return database!!.imageDao()
    }

    private fun ensureDatabaseInitialized(context: Context) {
        if (database == null) {
            database = Room.databaseBuilder(context, AppDatabase::class.java, APP_DATABASE_NAME)
                .build()
        }
    }

    val APP_DATABASE_NAME = "app_database"
    val DATABASE_UNITIALIZED_ERROR =
        "Database must be initialized! You should call DbProvider.create(context) before? "
}