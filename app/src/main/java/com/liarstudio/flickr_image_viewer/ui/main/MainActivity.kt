package com.liarstudio.flickr_image_viewer.ui.main

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.LinearLayout
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.liarstudio.flickr_image_viewer.R
import com.liarstudio.flickr_image_viewer.data.image.ImageRepository
import com.liarstudio.flickr_image_viewer.domain.Image
import com.liarstudio.flickr_image_viewer.ui.detail.ImageDetailActivityView
import com.liarstudio.flickr_image_viewer.ui.main.controller.ImageController
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.activity_main.*
import ru.surfstudio.android.easyadapter.EasyAdapter
import ru.surfstudio.android.easyadapter.ItemList

class MainActivity : AppCompatActivity() {

    private val viewDisposable = CompositeDisposable()

    private val repository = ImageRepository()

    private val adapter = EasyAdapter()
    private val imageController = ImageController(onImageSelected = ::openImageDetailScreen)

    private var images: List<Image> = listOf()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        initViews()
        loadImages()
    }

    private fun initViews() {
        main_recycler.layoutManager = StaggeredGridLayoutManager(2, RecyclerView.VERTICAL)
        main_recycler.addItemDecoration(ImageListOffsetDecoration(this))
        main_recycler.adapter = adapter
        main_swipe_refresh.setOnRefreshListener { reloadImages() }
    }

    private fun reloadImages() {
        main_swipe_refresh.isRefreshing = true
        loadImages()
    }

    private fun loadImages() {
        val loadImagesDisposable = repository.getImages()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(
                { images -> showImages(images) },
                { error -> showErrorMessage() }
            )
        viewDisposable.add(loadImagesDisposable)
    }

    private fun showImages(images: List<Image>) {
        this.images = images
        main_progress_bar.isVisible = false
        main_swipe_refresh.isRefreshing = false
        adapter.setItems(ItemList.create(images, imageController))
    }

    private fun showErrorMessage() {
        main_progress_bar.isVisible = false
        main_swipe_refresh.isRefreshing = false
        Toast.makeText(this, R.string.common_error_message, Toast.LENGTH_LONG).show()
    }

    private fun openImageDetailScreen(image: Image) {
        val imageDetailIntent = Intent(this, ImageDetailActivityView::class.java)
            .apply { putExtra(ImageDetailActivityView.IMAGE_DETAIL_URL_EXTRA, image.imageUrl) }
        startActivity(imageDetailIntent)
    }

    override fun onDestroy() {
        viewDisposable.dispose()
        super.onDestroy()
    }
}