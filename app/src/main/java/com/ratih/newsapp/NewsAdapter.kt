import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.ratih.newsapp.DetailActivity
import com.ratih.newsapp.R
import com.ratih.newsapp.model.Article
import kotlinx.android.synthetic.main.item_news.view.*
import org.jetbrains.anko.intentFor

class NewsAdapter(
    //parameter
    var context: Context, var listNews : List<Article?>?) :
    RecyclerView.Adapter<NewsAdapter.NewsViewHolder>() {


    inner   class NewsViewHolder(view : View) : RecyclerView.ViewHolder(view) {
        //untuk menggabungkan data dan view (tampilan)
        fun bind (news: Article){
            with(itemView){
                tv_title_item_news.text = news.title
                tv_date_item_news.text = news.publishedAt
                tv_time_item_news.text = news.author

                //buat get gambar bisa pakai glide sama picaso
                Glide.with(context).load(news.urlToImage).centerCrop().into(iv_item_news)
                itemView.setOnClickListener{
                    itemView.context?.startActivity(
                        itemView.context.intentFor<DetailActivity>("EXTRA_NEWS_DATA" to news)
                    )
                }

            }
        }
    }

    //layout mana yang mau di gabungin
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NewsAdapter.NewsViewHolder {
        val view =  LayoutInflater.from(context).inflate(R.layout.item_news,parent, false)
        return NewsViewHolder(view)
    }
    //buat menghitung jumlah datanya ada berapa
    override fun getItemCount(): Int = listNews!!.size

    //untuk mengatur data sesaui posisi list
    override fun onBindViewHolder(holder: NewsAdapter.NewsViewHolder, position: Int) {
        holder.bind(listNews?.get(position)!!)
    }
}