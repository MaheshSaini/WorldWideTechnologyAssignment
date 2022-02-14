package com.backbase.assignment.ui.data.model.albums


import com.google.gson.annotations.SerializedName

class PhotosResponse : ArrayList<PhotosResponse.PhotosResponseItem>() {
    data class PhotosResponseItem(
        @SerializedName("albumId")
        val albumId: Int, // 100
        @SerializedName("id")
        val id: Int, // 5000
        @SerializedName("thumbnailUrl")
        val thumbnailUrl: String, // https://via.placeholder.com/150/6dd9cb
        @SerializedName("title")
        val title: String, // error quasi sunt cupiditate voluptate ea odit beatae
        @SerializedName("url")
        val url: String // https://via.placeholder.com/600/6dd9cb
    )
}