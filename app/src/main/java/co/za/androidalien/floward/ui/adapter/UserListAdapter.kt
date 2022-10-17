package co.za.androidalien.floward.ui.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import co.za.androidalien.floward.R
import co.za.androidalien.floward.data.models.User
import co.za.androidalien.floward.ui.adapter.UserListAdapter.UserViewHolder
import com.bumptech.glide.Glide
import de.hdodenhof.circleimageview.CircleImageView

class UserListAdapter(private val onClick: (User) -> Unit): ListAdapter<User, UserViewHolder>(UserDiffCallback) {

    class UserViewHolder(itemView: View, private val onClick: (User) -> Unit):
        RecyclerView.ViewHolder(itemView) {

        private val profileImage: CircleImageView = itemView.findViewById(R.id.image)
        private val name: TextView = itemView.findViewById(R.id.name)
        private val count: TextView = itemView.findViewById(R.id.postcount)
        private var selectedUser: User? = null


        init {
            itemView.setOnClickListener {
                selectedUser?.let {
                    onClick(it)
                }
            }
        }

            fun bind(user: User) {
                selectedUser = user

                Glide.with(itemView).load(user.thumbnailUrl)
                    .centerCrop().into(profileImage)
                name.text = user.name
                count.text = "Post count: ${user.postCount}"

            }
    }

    object UserDiffCallback : DiffUtil.ItemCallback<User>() {
        override fun areItemsTheSame(oldItem: User, newItem: User): Boolean {
            return oldItem == newItem
        }

        override fun areContentsTheSame(oldItem: User, newItem: User): Boolean {
            return oldItem.userId == newItem.userId
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UserViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_list, parent, false)
        return UserViewHolder(view, onClick)
    }

    override fun onBindViewHolder(holder: UserViewHolder, position: Int) {
        val user = getItem(position)
        holder.bind(user)
    }

    override fun getItemCount(): Int {
//        Log.d("ADAPTER", "Item count: $getItemCount()")
        return super.getItemCount()
    }
}