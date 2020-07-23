package com.liarstudio.flickr_image_viewer.ui.main

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.recyclerview.widget.GridLayoutManager
import com.liarstudio.flickr_image_viewer.R
import com.liarstudio.flickr_image_viewer.data.image.ImageRepository
import com.liarstudio.flickr_image_viewer.domain.Image
import com.liarstudio.flickr_image_viewer.ui.base.ScreenOrientation
import com.liarstudio.flickr_image_viewer.ui.detail.ImageDetailActivityView
import com.liarstudio.flickr_image_viewer.ui.main.controller.ImageController
import com.liarstudio.flickr_image_viewer.ui.main.offset.ImageListOffsetDecoration
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.activity_main.*
import ru.surfstudio.android.easyadapter.EasyAdapter
import ru.surfstudio.android.easyadapter.ItemList

class MainActivity : AppCompatActivity() {


    private var images: List<Image> = listOf()
    private val adapter = EasyAdapter().apply { isFirstInvisibleItemEnabled = false }
    private val imageController = ImageController(onImageSelected = ::openImageDetailScreen)

    private val viewDisposable = CompositeDisposable()
    private lateinit var repository: ImageRepository

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        repository = ImageRepository(applicationContext)
        initViews()
        restoreImages(savedInstanceState)
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putSerializable(IMAGES_EXTRA, ArrayList(images))
    }

    override fun onDestroy() {
        viewDisposable.dispose()
        super.onDestroy()
    }

    private fun initViews() {
        val screenOrientation = ScreenOrientation.getByValue(resources.configuration.orientation)
        val spanCount = when (screenOrientation) {
            ScreenOrientation.PORTRAIT -> 2
            ScreenOrientation.LANDSCAPE -> 4
        }

        main_recycler.layoutManager = GridLayoutManager(this, spanCount)
        main_recycler.addItemDecoration(
            ImageListOffsetDecoration(
                this,
                screenOrientation
            )
        )
        main_recycler.adapter = adapter
        main_swipe_refresh.setOnRefreshListener { reloadImages() }
    }

    private fun restoreImages(savedInstanceState: Bundle?) {
        if (savedInstanceState != null && savedInstanceState.containsKey(IMAGES_EXTRA)) {
            val images = savedInstanceState.getSerializable(IMAGES_EXTRA) as ArrayList<Image>
            if (images.isEmpty()) {
                loadImages()
            } else {
                showImages(images)
            }
        } else {
            loadImages()
        }
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
                { error -> showErrorMessage(error) }
            )
        viewDisposable.add(loadImagesDisposable)
    }

    private fun showImages(images: List<Image>) {
        this.images = images
        hideLoaders()
        adapter.setItems(ItemList.create(images, imageController))
    }

    private fun showErrorMessage(error: Throwable) {
        hideLoaders()
        Toast.makeText(this, R.string.common_error_message, Toast.LENGTH_LONG).show()
    }

    private fun hideLoaders() {
        main_progress_bar.isVisible = false
        main_swipe_refresh.isRefreshing = false
    }

    private fun openImageDetailScreen(image: Image) {
        val imageDetailIntent = Intent(this, ImageDetailActivityView::class.java)
            .apply { putExtra(ImageDetailActivityView.IMAGE_DETAIL_URL_EXTRA, image.imageUrl) }
        startActivity(imageDetailIntent)
    }

    companion object {
        const val IMAGES_EXTRA = "IMAGES_EXTRA"
    }
}