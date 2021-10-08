package com.denisyordanp.mygithub

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.denisyordanp.mygithub.databinding.ActivityListUserBinding

class ListUserActivity : AppCompatActivity() {

    private lateinit var binding: ActivityListUserBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityListUserBinding.inflate(layoutInflater)
        setTheme(R.style.Theme_MyGithub_Main)
        setContentView(binding.root)

        setupRecyclerView()
        showUsers()
    }



    private fun setupRecyclerView() {
        binding.usersRecyclerView.setHasFixedSize(true)
    }

    private fun showUsers() {
        val adapter = UserAdapter(UserData.getUsers(resources))
        adapter.onClickOnUser = { toDetail(it) }
        binding.usersRecyclerView.adapter = adapter
    }

    private fun toDetail(user: User) {
        val toDetailIntent = Intent(this, DetailUserActivity::class.java).apply {
            putExtra(DetailUserActivity.EXTRA_USER, user)
        }
        startActivity(toDetailIntent)
    }
}