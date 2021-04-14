package com.ratih.newsapp.model


import android.annotation.SuppressLint
import com.google.gson.annotations.SerializedName
import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@SuppressLint("ParcelCreator")
@Parcelize
data class ResponseNews(
    @SerializedName("status")
    val status: String?, // ok
    @SerializedName("totalResults")
    val totalResults: Int?, // 20
    @SerializedName("articles")
    val articles: List<Article>?
) : Parcelable