package com.backbase.assignment.ui.presentation.ui.album
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.backbase.assignment.databinding.ActivityAlbumsMainBinding
import com.backbase.assignment.ui.data.model.albums.AlbumsResponse
import com.backbase.assignment.ui.data.model.albums.PhotosResponse
import com.backbase.assignment.ui.domain.adapter.*
import kotlinx.coroutines.launch
import org.koin.androidx.viewmodel.ext.android.viewModel
import java.util.ArrayList

class ScrollingActivity : AppCompatActivity(), ItemClickListener {
    private val viewModel: AlbumViewModel by viewModel()
    private lateinit var mainBinding: ActivityAlbumsMainBinding
    private val albumsResponseList: ArrayList<AlbumsResponse.AlbumsResponseItem> = ArrayList()
    private val photoResponseList: ArrayList<PhotosResponse.PhotosResponseItem> = ArrayList()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mainBinding = ActivityAlbumsMainBinding.inflate(layoutInflater)
        setContentView(mainBinding.root)
        displayMoviesNowPlaying()
        lifecycleScope.launch {
            mainBinding.progressBar.visibility = View.VISIBLE
            val getPhoto: PhotosResponse = viewModel.repository.getPhotoData()
            if (getPhoto.isNotEmpty()) {
                photoResponseList.addAll(getPhoto)
                if (photoResponseList != null) {
                    val getAlbumsData: AlbumsResponse = viewModel.repository.getAlbumsData()
                    if (getAlbumsData.isNotEmpty()) {
                        albumsResponseList.addAll(getAlbumsData)
                        setAdapterForPlayingNow(albumsResponseList, photoResponseList)
                    }
                }


            }
        }

    }

    private fun displayMoviesNowPlaying() {
        mainBinding.bannerRecyclerView.setHasFixedSize(true)
        val layoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
        mainBinding.bannerRecyclerView.layoutManager = layoutManager
        val dividerItemDecoration = DividerItemDecoration(this, DividerItemDecoration.VERTICAL)
        mainBinding.bannerRecyclerView.addItemDecoration(dividerItemDecoration)
        /*  val resId = R.anim.layout_animation_fall_down
          val animation = AnimationUtils.loadLayoutAnimation(this, resId)
          mainBinding.bannerRecyclerView.setLayoutAnimation(animation)*/
        mainBinding.bannerRecyclerView.layoutManager =
            LinearLayoutManager(this, LinearLayoutManager.VERTICAL, true)
    }

    private fun setAdapterForPlayingNow(
        albumsList: ArrayList<AlbumsResponse.AlbumsResponseItem>,
        photoList: ArrayList<PhotosResponse.PhotosResponseItem>
    ) {
        if (albumsList.size > 0 && photoList.size > 0) {
            mainBinding.progressBar.visibility = View.GONE
            val adapter = AlbumsBannerAdapter(this, albumsList, photoList)
            mainBinding.bannerRecyclerView.adapter = adapter
            adapter.setClickListener(this)
            adapter.notifyDataSetChanged()
        }
    }

    override fun onClick(view: View?, position: Int) {

    }

}