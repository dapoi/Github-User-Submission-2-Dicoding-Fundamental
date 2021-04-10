package com.dapoidev.ourgithub.view.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.View
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.dapoidev.ourgithub.R
import com.dapoidev.ourgithub.databinding.FragmentFollowersBinding
import com.dapoidev.ourgithub.view.UserDetailActivity
import com.dapoidev.ourgithub.view.adapter.UserListAdapter
import com.dapoidev.ourgithub.viewModel.FollowersViewModel

@Suppress("DEPRECATION")
class FollowersFragment : Fragment(R.layout.fragment_followers) {
    private var _binding: FragmentFollowersBinding? = null
    private val binding get() = _binding!!
    private lateinit var userListAdapter: UserListAdapter
    private lateinit var gitUsername: String
    private lateinit var followersViewModel: FollowersViewModel

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        gitUsername = arguments?.getString(UserDetailActivity.USERNAME_PACKAGE).toString()

        _binding = FragmentFollowersBinding.bind(view)

        userListAdapter = UserListAdapter()
        userListAdapter.notifyDataSetChanged()

        binding.rvGithubFollowers.setHasFixedSize(true)
        binding.rvGithubFollowers.layoutManager = LinearLayoutManager(activity)
        binding.rvGithubFollowers.adapter = userListAdapter

        showData(true)

        followersViewModel = ViewModelProvider(this, ViewModelProvider.NewInstanceFactory())
            .get(FollowersViewModel::class.java)
        followersViewModel.setListFollowers(gitUsername)
        followersViewModel.getListFollowers().observe(viewLifecycleOwner,{
            if (it != null) {
                userListAdapter.setterList(it)
                showData(false)
            }
        })
    }

    private fun showData(state: Boolean) {
        if (state) {
            binding.progressBar.visibility = View.VISIBLE
        } else {
            binding.progressBar.visibility = View.GONE
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

}