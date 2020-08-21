package com.gojek.sample.presentation.adapter

import android.graphics.Color
import android.graphics.drawable.GradientDrawable
import android.view.View
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import com.cavista.sample.presentation.customeview.BaseViewHolder
import com.cavista.sample.presentation.customeview.OnRecyclerObjectClickListener
import com.gojek.sample.R
import com.gojek.sample.domain.model.UIGitHubRepoData
import com.gojek.sample.presentation.utils.AppUtils


class GitHubItemViewHolder(itemView: View, val adapter: GitHubItemAdapter) :
    BaseViewHolder<UIGitHubRepoData, OnRecyclerObjectClickListener<UIGitHubRepoData>>(itemView) {
    private val itemImageView = itemView.findViewById<ImageView>(R.id.itemImgView)
    private val authorName = itemView.findViewById<TextView>(R.id.tvAuthor)
    private val titleName = itemView.findViewById<TextView>(R.id.tvTitleName)
    private val language = itemView.findViewById<TextView>(R.id.tvLanguage)
    private val fork = itemView.findViewById<TextView>(R.id.tvFork)
    private val star = itemView.findViewById<TextView>(R.id.tvStar)
    private val description = itemView.findViewById<TextView>(R.id.tvDescreption)
    private val languageColor = itemView.findViewById<ImageView>(R.id.languageColor)

    private val detailView = itemView.findViewById<LinearLayout>(R.id.layoutDetailView)
    override fun onBind(
        item: UIGitHubRepoData,
        listener: OnRecyclerObjectClickListener<UIGitHubRepoData>,
        position: Int
    ) {
        val isExpanded = position == adapter.mExpandedPosition;
        detailView.visibility = if (isExpanded) View.VISIBLE else View.GONE
        itemView.isActivated = isExpanded
        authorName.text = item.author
        titleName.text = item.name
        language.text = item.language
        fork.text = item.forks.toString()
        star.text = item.star.toString()
        description.text = item.description
        val drawable = languageColor.background.mutate() as GradientDrawable
        drawable.setColor(Color.parseColor(item.languageColor))


        if (!item.imageUrl.isNullOrEmpty()) {
            AppUtils.displayImage(itemView.context, item.imageUrl, itemImageView)
        }
        itemView.setOnClickListener {
            //    listener.onRowClicked(item, position)
            adapter.mExpandedPosition = if (isExpanded) -1 else position
            adapter.notifyDataSetChanged()
        }
    }

}