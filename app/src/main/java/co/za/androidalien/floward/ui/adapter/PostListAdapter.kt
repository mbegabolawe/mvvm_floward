package co.za.androidalien.floward.ui.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import co.za.androidalien.floward.R
import co.za.androidalien.floward.data.models.Post
import co.za.androidalien.floward.ui.adapter.PostListAdapter.PostViewHolder

class PostListAdapter(): ListAdapter<Post, PostViewHolder>(PostDiffCallback) {

    class PostViewHolder(itemView: View):
        RecyclerView.ViewHolder(itemView) {

        private val title: TextView = itemView.findViewById(R.id.title)
        private val body: TextView = itemView.findViewById(R.id.body)
        private var selectedPost: Post? = null

        fun bind(post: Post) {
            selectedPost = post
            title.text = post.title
            body.text = post.body

        }
    }

    object PostDiffCallback : DiffUtil.ItemCallback<Post>() {
        override fun areItemsTheSame(oldItem: Post, newItem: Post): Boolean {
            return oldItem == newItem
        }

        override fun areContentsTheSame(oldItem: Post, newItem: Post): Boolean {
            return oldItem.id == newItem.id
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PostViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.post_list_item, parent, false)
        return PostViewHolder(view)
    }

    override fun onBindViewHolder(holder: PostViewHolder, position: Int) {
        val Post = getItem(position)
        holder.bind(Post)
    }
}