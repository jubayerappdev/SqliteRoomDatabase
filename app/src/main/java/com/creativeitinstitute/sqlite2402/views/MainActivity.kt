package com.creativeitinstitute.sqlite2402.views

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.room.Room
import com.creativeitinstitute.sqlite2402.UserAdapter
import com.creativeitinstitute.sqlite2402.databinding.ActivityMainBinding
import com.creativeitinstitute.sqlite2402.db.User
import com.creativeitinstitute.sqlite2402.db.UserDao
import com.creativeitinstitute.sqlite2402.db.UserDatabase

class MainActivity : AppCompatActivity(), UserAdapter.HandleUserClick {
    private lateinit var binding: ActivityMainBinding

    val love = "She loves you"

    private lateinit var dao:UserDao
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)



        val db = Room.databaseBuilder(
            applicationContext,
            UserDatabase::class.java,
            "User_DB"
        ).allowMainThreadQueries().build()

        dao = db.getUserDao()

        setUserData()




        binding.addBtn.setOnClickListener {

            val intent = Intent(this@MainActivity, AddUserActivity::class.java)
            startActivity(intent)


        }


    }

    private fun setUserData() {
        dao.getAllUser().apply {
            val userAdapter = UserAdapter(this@MainActivity,this)
            binding.rvUser.adapter = userAdapter
        }

    }

    override fun onEditClick(user: User) {
        val editIntent = Intent(this@MainActivity, AddUserActivity::class.java)
        editIntent.putExtra(AddUserActivity.editKey,user)

        startActivity(editIntent)


    }

    override fun onLongDeleteClick(user: User) {
        dao.deleteUser(user)
        Toast.makeText(this@MainActivity, "${user.name} data has bees deleted! ", Toast.LENGTH_SHORT).show()
        setUserData()
    }
}