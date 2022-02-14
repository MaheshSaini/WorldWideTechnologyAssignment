package com.backbase.assignment.ui.domain.adapter

import android.content.Context
import android.graphics.drawable.Drawable
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.RelativeLayout
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.backbase.assignment.R
import com.backbase.assignment.ui.data.model.albums.AlbumsResponse
import com.backbase.assignment.ui.data.model.albums.PhotosResponse
import com.bumptech.glide.Glide
import com.bumptech.glide.request.target.Target


class AlbumsBannerAdapter(
    context: Context,
    listItems: ArrayList<AlbumsResponse.AlbumsResponseItem>,
    photoList: ArrayList<PhotosResponse.PhotosResponseItem>
) :
    RecyclerView.Adapter<AlbumsBannerAdapter.MyViewHolder?>() {
    private var listItems: ArrayList<AlbumsResponse.AlbumsResponseItem>? = listItems
    private var photoList: ArrayList<PhotosResponse.PhotosResponseItem>? = photoList
    var mContext: Context = context
    private var clickListener: ItemClickListener? = null
    var viewHolder: MyViewHolder? = null
    override fun onCreateViewHolder(viewGroup: ViewGroup, i: Int): MyViewHolder {
        val view: View =
            LayoutInflater.from(viewGroup.context).inflate(R.layout.list_item_album, null)
        viewHolder = MyViewHolder(view)
        return viewHolder as MyViewHolder
    }

    override fun onBindViewHolder(customViewHolder: MyViewHolder, position: Int) {
        val movieBanner: AlbumsResponse.AlbumsResponseItem = listItems?.get(position)!!
        val photoUrl: PhotosResponse.PhotosResponseItem = photoList?.get(position)!!
        if (photoUrl != null) {
            val url: String =
                photoUrl.thumbnailUrl + ".jpeg"//"https://www.gstatic.com/webp/gallery/1.jpg"
            Glide.with(mContext)
                .load(url)//https://via.placeholder.com/150/92c952.jpeg
                .placeholder(R.drawable.circular_shape)
                .into(customViewHolder.ivBanner)
        } else {
            // make sure Glide doesn't load anything into this view until told otherwise
            Glide.with(mContext).clear(customViewHolder.ivBanner)
            // remove the placeholder (optional); read comments below
            customViewHolder.ivBanner.setImageDrawable(null)
        }
        customViewHolder.txtTitle.setText(movieBanner.title)
        /*customViewHolder.ivBanner.setAnimation(
            AnimationUtils.loadAnimation(
                mContext,
                R.anim.fade_scale_animation
            )
        )
        customViewHolder.clDisplayText.setAnimation(
            AnimationUtils.loadAnimation(
                mContext,
                R.anim.fade_transition_animation
            )
        )*/

    }

    override fun getItemCount(): Int {
        return if (null != listItems) listItems!!.size else 0
    }

    fun setClickListener(itemClickListener: ItemClickListener?) {
        clickListener = itemClickListener
    }

    // initializes some private fields to be used by RecyclerView.
    inner class MyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView),
        View.OnClickListener {

        val clDisplayText: RelativeLayout
        val ivBanner: ImageView
        val txtTitle: TextView
        override fun onClick(view: View) {
            if (clickListener != null) clickListener?.onClick(view, adapterPosition)
        }

        init {
            txtTitle = itemView.findViewById(R.id.txtTitle)
            ivBanner = itemView.findViewById(R.id.ivBanner)
            clDisplayText = itemView.findViewById(R.id.clDisplayText)
            clDisplayText.setOnClickListener(this)
            itemView.tag = itemView
            clDisplayText.setOnClickListener(this)
        }
    }

}
