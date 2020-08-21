package com.gojek.sample.presentation.adapter

import android.content.Context
import android.view.ViewGroup
import com.cavista.sample.presentation.customeview.BaseViewHolder
import com.cavista.sample.presentation.customeview.CustomRecyclerViewAdapter
import com.cavista.sample.presentation.customeview.OnRecyclerObjectClickListener
import com.gojek.sample.R
import com.gojek.sample.domain.model.UIGitHubRepoData

class GitHubItemAdapter(context: Context) :
    CustomRecyclerViewAdapter<UIGitHubRepoData, OnRecyclerObjectClickListener<UIGitHubRepoData>, BaseViewHolder<UIGitHubRepoData, OnRecyclerObjectClickListener<UIGitHubRepoData>>>(
        context
    ) {

    override fun getId(position: Int): String {
        return getItemAt(position).name
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): BaseViewHolder<UIGitHubRepoData, OnRecyclerObjectClickListener<UIGitHubRepoData>> {
        return GitHubItemViewHolder(
            itemView = inflate(
                R.layout.github_repo_item,
                parent,
                false
            )
        )
    }


}
