package com.guresberatcan.spaceflightnewsapp.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import android.view.animation.AnimationUtils
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.guresberatcan.spaceflightnewsapp.R
import com.guresberatcan.spaceflightnewsapp.data.model.Article
import com.guresberatcan.spaceflightnewsapp.databinding.ItemArticleListBinding

class ArticleListAdapter :
    ListAdapter<Article, ArticleListAdapter.CustomViewHolder>(SampleItemDiffCallback()) {

    var itemClickListener: ((item: Article) -> Unit)? = null

    var favouriteClickedListener: ((item: Article) -> Unit)? = null

    override fun onCreateViewHolder(parent: ViewGroup, position: Int): CustomViewHolder {
        val binding =
            ItemArticleListBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return CustomViewHolder(binding)
    }

    override fun onBindViewHolder(holder: CustomViewHolder, position: Int) {
        holder.bindTo(getItem(position))
        holder.itemView.animation =
            AnimationUtils.loadAnimation(holder.itemView.context, R.anim.recylerview_anim)
    }

    inner class CustomViewHolder(private val itemBinding: ItemArticleListBinding) :
        RecyclerView.ViewHolder(itemBinding.root) {
        fun bindTo(article: Article) {
            with(itemBinding) {
                articleTitle.text = article.title
                articleSummary.text = article.summary
                newsSite.text = article.newsSite
                articlePublishedAt.text = article.publishedAt
                Glide.with(root.context).load(article.imageUrl)
                    .into(articleImage)
                if (article.isFavourite) {
                    favouriteButton.text = "Remove from favourites"
                } else {
                    favouriteButton.text = "Add to favourites"
                }

                favouriteButton.setOnClickListener {
                    favouriteClickedListener?.invoke(article)

                }
            }
            itemBinding.root.setOnClickListener {
                itemClickListener?.invoke(article)
            }
        }
    }

    class SampleItemDiffCallback : DiffUtil.ItemCallback<Article>() {
        override fun areItemsTheSame(oldItem: Article, newItem: Article): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: Article, newItem: Article): Boolean =
            oldItem == newItem

    }
}