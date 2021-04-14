package com.ratih.newsapp

import NewsAdapter
import android.app.ProgressDialog
import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.ContactsContract.CommonDataKinds.StructuredPostal.COUNTRY
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.ratih.newsapp.BuildConfig.API_KEY
import com.ratih.newsapp.databinding.ActivityMainBinding
import com.ratih.newsapp.model.ResponseNews
import com.ratih.newsapp.network.RetrofitConfig
import kotlinx.android.synthetic.main.activity_main.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.text.SimpleDateFormat
import java.util.*


class MainActivity : AppCompatActivity(), View.OnClickListener {
    private lateinit var mainBinding: ActivityMainBinding
    private lateinit var newsAdapter: NewsAdapter



    //buat ngambil tanggal sekarang
    val date = getCurrentDateTime()
    fun getCurrentDateTime(): Date {
        //berisi tanggal sekarang
        return Calendar.getInstance().time




    }

    //buat nge format tanggalnya
    fun Date.toString(format: String, locale: Locale = Locale.getDefault()): String {
        val formatter = SimpleDateFormat(format, locale)
        return formatter.format(this)
    }

//    //get launch supaya backnya langsung ke home jadi ngga ketumpuk-tumpuk atau mengclear suatu activity supaya nga numpuk
//    companion object {
//        fun getLaunchService (form : Context ) = Intent(form, MainActivity::class.java).apply {
//            addFlags(Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK)
//
//        }
//    }

    companion object{
        fun getLauncService(from : Context) = Intent(from, MainActivity::class.java).apply{
            addFlags(Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //tambahan view binding
        mainBinding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(mainBinding.root)
        setContentView(R.layout.activity_main)
        //untuk menyembunyikan action bar
        supportActionBar?.hide()
        //maggil main binding
        mainBinding.rvMain
        mainBinding.ivProfileMain.setOnClickListener(this)

        //penggunakan this yang ngga ada kurawalnya biar lebih aman untuk button yang banyak
        //kan biasanya ada yang this {
        iv_profile_setting.setOnClickListener(this)

        //ini buat ngatur format tanggalnya
        tv_date_main.text = date.toString("dd/MM/yyyy")
        getNews()
    }

    private fun getNews() {
        val loading = ProgressDialog.show(this, "Request Data", "Loading...")

        //enqueue itu baris
        //disini bagian retrofit, apiservis dll bekerja
        RetrofitConfig.getInstance().getTopHeadlinesNews(BuildConfig.COUNTRY, BuildConfig.API_KEY)
            .enqueue(
                object : Callback<ResponseNews> {

                    //jika datanya ngga gagal
                    //jika respone di server
                    override fun onResponse(
                        call: Call<ResponseNews>,
                        response: Response<ResponseNews>
                    ) {
                        //log.d itu untuk mengecek data
                        //mengecek di debug
                        Log.d("Response", "Success" + response.body()?.articles)
                        loading.dismiss()

                        if (response.isSuccessful) {
                            val status = response.body()?.status
                            if (status.equals("ok")) {
                                //toas itu tampilan dibagian bawah
                                Toast.makeText(
                                    this@MainActivity,
                                    "Data Succes !",
                                    Toast.LENGTH_SHORT
                                ).show()

                                //untuk menampung data article
                                val newsData = response.body()?.articles

//                            //untuk mengatur data yang yang paling besar
                                Glide.with(this@MainActivity)
                                    .load(newsData?.component1()?.urlToImage)
                                    .centerCrop().into(iv_breaking_iteam)
                                tv_title_breaking_item.text =
                                    newsData?.component1()?.title.toString()
                                tv_date_release_breaking_item.text =
                                    newsData?.component1()?.publishedAt.toString()
                                tv_author_breaking_news.text =
                                    newsData?.component1()?.author.toString()

                                //menyambungkan pada adapter
                                val newsAdapter = NewsAdapter(this@MainActivity, newsData)
                                rv_main.adapter = newsAdapter
                                rv_main.layoutManager = LinearLayoutManager(this@MainActivity)
                            } else {
                                //kalo gagal dilocal
                                Toast.makeText(this@MainActivity, "Data Failed", Toast.LENGTH_SHORT)
                                    .show()
                            }
                        }
                    }


                    //proses gagal pada server
                    //data failed berarti dari data
                    //selain failed error dari server
                    override fun onFailure(call: Call<ResponseNews>, t: Throwable) {
                        Log.d("Response", "Failed" + t.localizedMessage)
                        loading.dismiss()

                    }
                }
            )
    }

    override fun onClick(v: View) {
        when (v.id) {
            R.id.iv_profile_setting -> startActivity(Intent(ProfileActivity.getLaunchService(this)))
        }
    }
}