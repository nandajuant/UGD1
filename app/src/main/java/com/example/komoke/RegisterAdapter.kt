package com.example.komoke

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.RecyclerView
import com.example.komoke.roomUser.User
import kotlinx.android.synthetic.main.fragment_account.view.*

class RegisterAdapter (private val users: ArrayList<User>, private val
listener: OnAdapterListener) :
    RecyclerView.Adapter<RegisterAdapter.UserViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int):
            UserViewHolder {
        return UserViewHolder(

            LayoutInflater.from(parent.context).inflate(R.layout.fragment_account,parent, false)
        )
    }
    override fun onBindViewHolder(holder: UserViewHolder, position:
    Int) {
        val user = users[position]
        holder.view.icon_edit.setOnClickListener {
            listener.onUpdate(user)
        }
    }
    override fun getItemCount() = users.size
    inner class UserViewHolder( val view: View) :
        RecyclerView.ViewHolder(view)
    @SuppressLint("NotifyDataSetChanged")
    fun setData(list: List<User>){
        users.clear()
        users.addAll(list)
        notifyDataSetChanged()
    }
    interface OnAdapterListener {
        fun onClick(user: User)
        fun onUpdate(user: User)
        fun onDelete(user: User)
    }
}
