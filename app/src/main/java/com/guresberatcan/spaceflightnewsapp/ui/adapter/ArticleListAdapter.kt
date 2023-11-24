package com.guresberatcan.spaceflightnewsapp.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.guresberatcan.spaceflightnewsapp.data.model.Article
import com.guresberatcan.spaceflightnewsapp.databinding.ItemArticleListBinding

class ArticleListAdapter :
    ListAdapter<Article, ArticleListAdapter.CustomViewHolder>(SampleItemDiffCallback()) {

    var itemClickListener: ((item: Article) -> Unit)? = null


    override fun onCreateViewHolder(parent: ViewGroup, position: Int): CustomViewHolder {
        val binding =
            ItemArticleListBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return CustomViewHolder(binding)

    }

    override fun onBindViewHolder(holder: CustomViewHolder, position: Int) {
        holder.bindTo(getItem(position))
    }

    inner class CustomViewHolder(private val itemBinding: ItemArticleListBinding) :
        RecyclerView.ViewHolder(itemBinding.root) {
        fun bindTo(article: Article) {
            with(itemBinding) {
                textView.text = article.title
                Glide.with(root.context).load(article.imageUrl)
                    .into(imageview)
            }
            itemBinding.root.setOnClickListener {
                itemClickListener?.invoke(article)
            }
        }
    }

    class SampleItemDiffCallback : DiffUtil.ItemCallback<Article>() {
        override fun areItemsTheSame(oldItem: Article, newItem: Article): Boolean =
            oldItem == newItem

        override fun areContentsTheSame(oldItem: Article, newItem: Article): Boolean =
            oldItem == newItem

    }
}