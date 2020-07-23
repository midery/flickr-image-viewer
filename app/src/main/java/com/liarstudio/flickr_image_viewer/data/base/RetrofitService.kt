package com.liarstudio.flickr_image_viewer.data.base

import com.liarstudio.flickr_image_viewer.data.image.api.ImageApi
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitService {

    private val retrofit: Retrofit by lazy {
        Retrofit.Builder()
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .addConverterFactory(GsonConverterFactory.create())
            .baseUrl(ServerUrls.BASE_URL)
            .build()
    }

    val imageApi by lazy { retrofit.create(ImageApi::class.java) }
}