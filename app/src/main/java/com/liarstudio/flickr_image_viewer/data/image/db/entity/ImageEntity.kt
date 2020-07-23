package com.liarstudio.flickr_image_viewer.data.image.db.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.liarstudio.flickr_image_viewer.data.base.Transformable
import com.liarstudio.flickr_image_viewer.domain.Image

@Entity(tableName = "image")
class ImageEntity(
    @PrimaryKey(autoGenerate = false) val id: String,
    @ColumnInfo(name = "secret") val secret: String,
    @ColumnInfo(name = "farm") val farm: String,
    @ColumnInfo(name = "server") val server: String
) : Transformable<Image> {

    override fun transform(): Image = Image(id, farm, server, secret)
}
