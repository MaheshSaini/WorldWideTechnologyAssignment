package com.backbase.assignment.ui.data.model.albums


import com.google.gson.annotations.SerializedName

class AlbumsResponse : ArrayList<AlbumsResponse.AlbumsResponseItem>(){
    data class AlbumsResponseItem(
        @SerializedName("id")
        val id: Int, // 100
        @SerializedName("title")
        val title: String, // enim repellat iste
        @SerializedName("userId")
        val userId: Int // 10
    )
}