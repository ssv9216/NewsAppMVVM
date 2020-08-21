package samples.droidproject.newsstories.adapters

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.navigation.NavHostController
import androidx.navigation.Navigation
import androidx.navigation.findNavController
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import kotlinx.android.synthetic.main.item_breaking_news.view.*
import samples.droidproject.newsstories.models.Article
import samples.droidproject.newsstories.R
import samples.droidproject.newsstories.ui.ArticleFragment
import samples.droidproject.newsstories.ui.BreakingNewsFragmentDirections
import javax.xml.transform.ErrorListener

class NewsAdapter :RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    inner class ArticleViewHolder(itemView: View):RecyclerView.ViewHolder(itemView)

    private val differCallback = object :DiffUtil.ItemCallback<Article>(){
        override fun areItemsTheSame(oldItem: Article, newItem: Article): Boolean {
            return oldItem.url == newItem.url
        }

        override fun areContentsTheSame(oldItem: Article, newItem: Article): Boolean {
            return oldItem == newItem
        }
    }

    val differ = AsyncListDiffer(this,differCallback)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return ArticleViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.item_breaking_news, parent, false)
        )
    }

    override fun getItemCount(): Int {
        return differ.currentList.size
    }


    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val article = differ.currentList[position]
        holder.itemView.apply {
            Glide.with(this).load(article.urlToImage).centerCrop().into(ibnImageView)
            ibnTitle.text = article.title
            ibnDesc.text = article.description
            ibnPublishAt.text = article.publishedAt

            holder.itemView.setOnClickListener {
//                val action = BreakingNewsFragmentDirections.actionBreakingNewsFragmentToArticleFragment2(article)
//                Navigation.findNavController(it).navigate(action)
                val bundle = Bundle().apply {
                    putSerializable("article",article)
                }
                findNavController().navigate(R.id.articleFragment,bundle)
            }
            }

        }



    }
//    private var onItemClickListener: ((Article)->Unit)? =null


//    fun setOnItemClickListener(listener: (Article)->Unit){
//        onItemClickListener = listener
//    }
