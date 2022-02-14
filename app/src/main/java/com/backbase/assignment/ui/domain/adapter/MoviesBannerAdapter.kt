package com.backbase.assignment.ui.domain.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.AnimationUtils
import android.widget.ImageView
import android.widget.RelativeLayout
import androidx.recyclerview.widget.RecyclerView
import com.backbase.assignment.R
import com.backbase.assignment.ui.data.model.movie.Movie
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy

class MoviesBannerAdapter(context: Context, listItems: ArrayList<Movie>) :
    RecyclerView.Adapter<MoviesBannerAdapter.MyViewHolder?>() {
    private var listItems: ArrayList<Movie>?
    var mContext: Context
    private var clickListener: ItemClickListener? = null
    var viewHolder: MyViewHolder? = null
    override fun onCreateViewHolder(viewGroup: ViewGroup, i: Int): MyViewHolder {
        val view: View =
            LayoutInflater.from(viewGroup.context).inflate(R.layout.banner_list_item, null)
        viewHolder = MyViewHolder(view)
        return viewHolder as MyViewHolder
    }

    override fun onBindViewHolder(customViewHolder: MyViewHolder, position: Int) {
        val movieBanner: Movie = listItems?.get(position)!!
        val posterURL = "https://image.tmdb.org/t/p/w500" + movieBanner.posterPath
        customViewHolder.iv_banner.setAnimation(
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
        )
        Glide.with(mContext)
            .load(posterURL)
            .diskCacheStrategy(DiskCacheStrategy.RESOURCE)
            .skipMemoryCache(false)
            .into(customViewHolder.iv_banner)
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
        val iv_banner: ImageView
        override fun onClick(view: View) {
            if (clickListener != null) clickListener?.onClick(view, adapterPosition)
        }

        init {
            iv_banner = itemView.findViewById(R.id.iv_banner)
            clDisplayText = itemView.findViewById(R.id.clDisplayText)
            clDisplayText.setOnClickListener(this)
            itemView.tag = itemView
            clDisplayText.setOnClickListener(this)
        }
    }

    init {
        this.listItems = listItems
        mContext = context
    }

}
