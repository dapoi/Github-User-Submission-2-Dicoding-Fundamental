package com.dapoidev.ourgithub.view

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.Settings
import android.view.KeyEvent
import android.view.Menu
import android.view.MenuItem
import android.view.View
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.dapoidev.ourgithub.R
import com.dapoidev.ourgithub.databinding.ActivityMainBinding
import com.dapoidev.ourgithub.model.UserModel
import com.dapoidev.ourgithub.view.adapter.UserListAdapter
import com.dapoidev.ourgithub.viewModel.UserViewModel

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private lateinit var userListAdapter: UserListAdapter
    private lateinit var userViewModel: UserViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // language setting
        val nameSearch = resources.getString(R.string.find_your_connection)
        val textHome = resources.getString(R.string.greet)
        binding.etSearchQuery.hint = nameSearch
        binding.textHome.text = textHome

        userListAdapter = UserListAdapter()
        userListAdapter.notifyDataSetChanged()

        binding.apply {
            rvGithubUser.setHasFixedSize(true)
            rvGithubUser.layoutManager = LinearLayoutManager(this@MainActivity)
            rvGithubUser.adapter = userListAdapter

            etSearchQuery.setOnKeyListener { _, keyCode, event ->
                if (event.action == KeyEvent.ACTION_DOWN && keyCode == KeyEvent.KEYCODE_ENTER) {
                    userSearch()
                    showBuffer(true)
                    return@setOnKeyListener true
                }
                return@setOnKeyListener false
            }

            btnIcSearch.setOnClickListener {
                userSearch()
            }
        }

        userListAdapter.setOnItemClickCallback(object : UserListAdapter.OnItemClickCallback{
            override fun onItemClicked(data: UserModel) {
                val intent = Intent(this@MainActivity, UserDetailActivity::class.java)
                intent.putExtra(UserDetailActivity.USERNAME_PACKAGE, data.login)
                startActivity(intent)
            }
        })

        userViewModel = ViewModelProvider(this, ViewModelProvider.NewInstanceFactory())
            .get(UserViewModel::class.java)
        userViewModel.getSearchUser().observe(this, {
            if (it != null) {
                userListAdapter.setterList(it)
                showBuffer(false)
            }
        })
    }

    private fun userSearch() {
        binding.apply {
            val query = etSearchQuery.text.toString()

            if (query.isEmpty())
                showBuffer(false)
            userViewModel.setUserSearch(query)
        }
    }

    private fun showBuffer(state: Boolean) {
        if (state) {
            binding.apply {
                progressBar.visibility = View.VISIBLE
                imageHome.visibility = View.VISIBLE
                textHome.visibility = View.VISIBLE
                rvGithubUser.visibility = View.INVISIBLE
            }
        } else {
            binding.apply {
                progressBar.visibility = View.INVISIBLE
                imageHome.visibility = View.INVISIBLE
                textHome.visibility = View.INVISIBLE
                rvGithubUser.visibility = View.VISIBLE
            }
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.switch_lang_menu, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == R.id.switch_menu) {
            val mIntent = Intent(Settings.ACTION_LOCALE_SETTINGS)
            startActivity(mIntent)
        }
        return super.onOptionsItemSelected(item)
    }
}