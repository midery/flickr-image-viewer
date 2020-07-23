package com.liarstudio.flickr_image_viewer.data.base

interface Transformable<T> {

    fun transform(): T
}