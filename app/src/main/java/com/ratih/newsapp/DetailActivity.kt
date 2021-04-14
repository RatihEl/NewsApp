package com.ratih.newsapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.bumptech.glide.Glide
import com.ratih.newsapp.databinding.ActivityDetailBinding
import com.ratih.newsapp.model.Article
import kotlinx.android.synthetic.main.activity_detail.*

class DetailActivity : AppCompatActivity() {
    companion object {
        const val EXTRA_NEWS_DATA = "extra_news_data"
    }
    private lateinit var detailBinding: ActivityDetailBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        detailBinding = ActivityDetailBinding.inflate(layoutInflater)
        setContentView(detailBinding.root)
        supportActionBar?.hide()
        detailBinding.fbBack.setOnClickListener{startActivity(MainActivity.getLauncService(this))}

        val news =intent.getParcelableExtra<Article>("EXTRA_NEWS_DATA") as Article
        Glide.with(this).load(news.urlToImage).into(detailBinding.ivDetail)
        detailBinding.tvTitileDetail.text = news.title.toString()
        detailBinding.tvDateDetail.text = news.publishedAt.toString()
        detailBinding.tvAuthorDetail.text = news.author.toString()
        detailBinding.tvDescDetail.text = news.description.toString()
        detailBinding.tvContentDetail.text = news.content.toString()


    }
}