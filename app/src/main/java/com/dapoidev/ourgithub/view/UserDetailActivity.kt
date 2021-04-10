package com.dapoidev.ourgithub.view

import android.annotation.SuppressLint
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.ViewModelProvider
import com.bumptech.glide.Glide
import com.dapoidev.ourgithub.R
import com.dapoidev.ourgithub.databinding.ActivityUserDetailBinding
import com.dapoidev.ourgithub.view.adapter.SectionPagerAdapter
import com.dapoidev.ourgithub.viewModel.UserDetailViewModel

class UserDetailActivity : AppCompatActivity() {
    companion object{
        const val USERNAME_PACKAGE = "username"
    }

    private lateinit var binding: ActivityUserDetailBinding
    private lateinit var detailViewModel: UserDetailViewModel

    @SuppressLint("SetTextI18n")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityUserDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // language setting
        val followers = resources.getString(R.string.followers)
        val following = resources.getString(R.string.following)
        val repo = resources.getString(R.string.repository)
        binding.apply {
            followingDetail.text = followers
            followingDetail.text = following
            repoDetail.text = repo
        }

        val gitUsername = intent.getStringExtra(USERNAME_PACKAGE)
        val saveData = Bundle()
        saveData.putString(USERNAME_PACKAGE, gitUsername)

        detailViewModel = ViewModelProvider(this, ViewModelProvider.NewInstanceFactory())
            .get(UserDetailViewModel::class.java)
        detailViewModel.setUserDetail(gitUsername.toString())
        detailViewModel.getUserDetail().observe(this, {
            if (it != null) {
                binding.apply {
                    nameDetail.text = it.name
                    usernameDetail.text = it.login
                    followersDetail.text = it.followers.toString()
                    followingDetail.text = it.following.toString()
                    repoDetail.text = it.public_repos.toString()
                    companyDetail.text = it.company ?: "-"
                    locationDetail.text = it.location ?: "-"
                    Glide.with(this@UserDetailActivity)
                        .load(it.avatar_url)
                        .into(imgDetail)
                }
            }
        })

        val sectionPagerAdapter = SectionPagerAdapter(this, supportFragmentManager, saveData)

            binding.viewPager.adapter = sectionPagerAdapter
            binding.tabs.setupWithViewPager(binding.viewPager)
    }

}
