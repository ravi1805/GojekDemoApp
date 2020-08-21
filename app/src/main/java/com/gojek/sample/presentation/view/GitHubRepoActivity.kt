package com.gojek.sample.presentation.view

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import com.cavista.sample.presentation.customeview.OnRecyclerObjectClickListener
import com.gojek.sample.R
import com.gojek.sample.domain.model.UIGitHubRepoData
import com.gojek.sample.domain.request.GitHubRepoReq
import com.gojek.sample.presentation.adapter.GitHubItemAdapter
import com.gojek.sample.presentation.utils.Resource
import com.gojek.sample.presentation.utils.ResourceState
import com.gojek.sample.presentation.utils.ViewModelFactory
import com.gojek.sample.presentation.viewmodel.GitHubViewModel
import dagger.android.AndroidInjection
import kotlinx.android.synthetic.main.activity_main.*
import javax.inject.Inject

class GitHubRepoActivity : AppCompatActivity() {
    private lateinit var gitHubItemAdapter: GitHubItemAdapter
    lateinit var gitHubViewModel: GitHubViewModel
    @Inject
    lateinit var viewModelFactory: ViewModelFactory

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        AndroidInjection.inject(this)
        setContentView(R.layout.activity_main)
        initView()
        gitHubViewModel = ViewModelProviders.of(this, viewModelFactory)
            .get(GitHubViewModel::class.java)
        gitHubViewModel.githubRepoLiveData.observe(this, Observer { handleResponse(it) })
//        gitHubViewModel.errorMsgLiveData.observe(this, Observer { showNoNetworkError(it) })

    }

    private fun initView() {
        gitHubItemAdapter = GitHubItemAdapter(this)
        rvGitHubRepo.apply {
            layoutManager = LinearLayoutManager(this@GitHubRepoActivity)
            setHasFixedSize(true)
            adapter = gitHubItemAdapter
        }
        gitHubItemAdapter.setListener(object : OnRecyclerObjectClickListener<UIGitHubRepoData> {
            override fun onItemClicked(item: UIGitHubRepoData, position: Int, operationId: Int) {
                //Implementation not needed
            }

            override fun onRowClicked(item: UIGitHubRepoData, position: Int) {
            }
        })

    }

    override fun onResume() {
        super.onResume()
        gitHubViewModel.getGitHubRepo(GitHubRepoReq("","daily"))
    }

    /**
     * Handle response from view model observer
     */
    private fun handleResponse(resource: Resource<List<UIGitHubRepoData>>) {
        hideProgress()
        resource.let { resourceList ->
            when (resourceList.state) {
                ResourceState.LOADING -> {
                    showProgress()
                }
                ResourceState.SUCCESS -> {
                    resourceList.data?.let { itemList ->
                        gitHubItemAdapter.setItems(itemList)
                    }
                }
                ResourceState.ERROR -> {
                    Toast.makeText(
                        this@GitHubRepoActivity,
                        resourceList.message,
                        Toast.LENGTH_LONG
                    ).show()
                }
            }
        }
    }

    private fun showProgress() {
        progressbar.visibility = View.VISIBLE
    }

    private fun hideProgress() {
        progressbar.visibility = View.GONE
    }

}
