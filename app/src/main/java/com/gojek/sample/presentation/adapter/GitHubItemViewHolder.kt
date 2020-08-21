package com.gojek.sample.presentation.adapter

import android.view.View
import android.widget.ImageView
import android.widget.TextView
import com.cavista.sample.presentation.customeview.BaseViewHolder
import com.cavista.sample.presentation.customeview.OnRecyclerObjectClickListener
import com.gojek.sample.presentation.utils.AppUtils
import com.gojek.sample.R
import com.gojek.sample.domain.model.UIGitHubRepoData


class GitHubItemViewHolder(itemView: View) :
    BaseViewHolder<UIGitHubRepoData, OnRecyclerObjectClickListener<UIGitHubRepoData>>(itemView) {

    private val itemImageView = itemView.findViewById<ImageView>(R.id.itemImgView)
    private val itemTitleName = itemView.findViewById<TextView>(R.id.titleName)

    override fun onBind(
        item: UIGitHubRepoData,
        listener: OnRecyclerObjectClickListener<UIGitHubRepoData>,
        position: Int
    ) {
        itemTitleName.text = item.name
        if (!item.imageUrl.isNullOrEmpty()) {
            AppUtils.displayImage(itemView.context, item.imageUrl, itemImageView)
        }
        itemView.setOnClickListener {
            listener.onRowClicked(item, position)
        }
    }

}