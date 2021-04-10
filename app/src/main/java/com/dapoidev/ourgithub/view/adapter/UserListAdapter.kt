package com.dapoidev.ourgithub.view.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.dapoidev.ourgithub.databinding.ItemListUserBinding
import com.dapoidev.ourgithub.model.UserModel

class UserListAdapter : RecyclerView.Adapter<UserListAdapter.UserViewHolder>() {
    private var onItemClickCallback: OnItemClickCallback? = null

    fun setOnItemClickCallback(onItemClickCallback: OnItemClickCallback) {
        this.onItemClickCallback = onItemClickCallback
    }

    private val list = ArrayList<UserModel>()
    fun setterList(users: ArrayList<UserModel>) {
        list.clear()
        list.addAll(users)
        notifyDataSetChanged()
    }

    inner class UserViewHolder(private val binding: ItemListUserBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(user: UserModel) {
            binding.apply {
                Glide.with(itemView)
                    .load(user.avatar_url)
                    .into(imgGitAva)

                tvGitUsername.text = user.login
            }

            itemView.setOnClickListener {
                onItemClickCallback?.onItemClicked(user)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UserViewHolder {
        val view = ItemListUserBinding.inflate(
            LayoutInflater.from(parent.context),
            parent, false
        )
        return UserViewHolder(view)
    }

    override fun onBindViewHolder(holder: UserViewHolder, position: Int) {
        holder.bind(list[position])
    }

    override fun getItemCount(): Int = list.size

    interface OnItemClickCallback {
        fun onItemClicked(data: UserModel)
    }
}