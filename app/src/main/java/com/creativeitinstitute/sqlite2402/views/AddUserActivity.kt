package com.creativeitinstitute.sqlite2402.views

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.room.Room
import com.creativeitinstitute.sqlite2402.databinding.ActivityAddUserBinding
import com.creativeitinstitute.sqlite2402.db.User
import com.creativeitinstitute.sqlite2402.db.UserDao
import com.creativeitinstitute.sqlite2402.db.UserDatabase

@Suppress("DEPRECATION")
class AddUserActivity : AppCompatActivity() {

    companion object {
        const val editKey = "edit"
        const val updatee = "Update User"
        const val add = "Add User"
    }

    private lateinit var binding: ActivityAddUserBinding
    private lateinit var dao: UserDao
    private var userId = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityAddUserBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val db = Room.databaseBuilder(
            applicationContext,
            UserDatabase::class.java,
            "User_DB"
        ).allowMainThreadQueries().build()

        dao = db.getUserDao()

        if (intent.hasExtra(editKey)) {
            binding.btnAddUser.text = updatee

            val user = intent.getParcelableExtra<User>(editKey)
            user?.let {
                binding.apply {
                    etUserName.setText(it.name)
                    etUserMobile.setText(it.mobile)
                    etUserAge.setText(it.age.toString())

                    userId = it.id
                }
            } ?: run {
                Toast.makeText(this, "User data is missing.", Toast.LENGTH_SHORT).show()
            }
        } else {
            binding.btnAddUser.text = add
        }

        binding.btnAddUser.setOnClickListener {
           val name = binding.etUserName.text.toString().trim()
           val age = binding.etUserAge.text.toString().trim()
           val mobile = binding.etUserMobile.text.toString().trim()

            if (binding.btnAddUser.text.toString()==add){

                addUser(name,age,mobile)
            }else{
                updateUser(name, age, mobile)
            }



        }




    }

    private fun updateUser(name: String, age: String, mobile: String) {

        Toast.makeText(this, "Update Successfully..", Toast.LENGTH_SHORT).show()
        val user = User(userId, name, age.toInt(), mobile)
        dao.editUser(user)

        val intent = Intent(this,MainActivity::class.java)
        startActivity(intent)
        finish()

    }

    private fun addUser(name: String, age: String, mobile: String) {

        val user = User(0, name,age.toInt(), mobile)
        dao.addUser(user)
        val intent = Intent(this,MainActivity::class.java)
        startActivity(intent)
        finish()

    }


}