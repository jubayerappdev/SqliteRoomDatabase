package com.creativeitinstitute.sqlite2402

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.creativeitinstitute.sqlite2402.databinding.ItemUserBinding
import com.creativeitinstitute.sqlite2402.db.User
import com.creativeitinstitute.sqlite2402.views.MainActivity

class UserAdapter( val listener:HandleUserClick,val userList:List<User>) :RecyclerView.Adapter<UserAdapter.UserVH>(){

   interface HandleUserClick{
       fun onEditClick(user:User)
       fun onLongDeleteClick(user: User)
   }




    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UserVH {

        return UserVH(ItemUserBinding.inflate(LayoutInflater.from(parent.context),parent,false))
    }

    override fun getItemCount(): Int = userList.size

    override fun onBindViewHolder(holder: UserVH, position: Int) {
        userList[position].let { user->

            holder.binding.apply {
                tvUserName.text = "Name : ${user.name}"
                tvUserMobile.text = "Phone : ${user.mobile}"
                tvUserAge.text = "Age : ${user.age}"

                btnEdit.setOnClickListener {
                    listener.onEditClick(user)

                }
                root.setOnClickListener{
                    listener.onLongDeleteClick(user)
                    true
                }
            }

        }
    }



    class UserVH(val binding: ItemUserBinding):RecyclerView.ViewHolder(binding.root)
}