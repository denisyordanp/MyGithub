package com.denisyordanp.mygithub

import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import com.denisyordanp.mygithub.databinding.ActivityDetailUserBinding

class DetailUserActivity : AppCompatActivity() {

    private lateinit var binding: ActivityDetailUserBinding
    private var user: User? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailUserBinding.inflate(layoutInflater)
        setTheme(R.style.Theme_MyGithub_Main)
        setContentView(binding.root)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        user = intent.getParcelableExtra(EXTRA_USER)
        user?.let { setView(it) }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.detail_menu, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            android.R.id.home -> {
                onBackPressed()
                true
            }
            R.id.share -> {
                shareUser(user)
                true
            }
            else -> {
                super.onOptionsItemSelected(item)
            }
        }
    }

    private fun setView(user: User) {
        binding.apply {
            nameTextView.text = user.name
            usernameTextView.text = user.username
            locationTextView.text = user.location
            companyTextView.text = user.company
            Glide.with(root.context)
                .load(user.avatar)
                .circleCrop()
                .into(userImageView)

            val followers = resources.getString(R.string.followers, user.follower)
            followersTextView.text = followers

            val following = resources.getString(R.string.following, user.following)
            followingTextView.text = following

            val repositories = resources.getString(R.string.repository, user.repository)
            repositoryTextView.text = repositories
        }
    }

    private fun shareUser(user: User?) {
        user?.let {
            val intent = Intent(Intent.ACTION_SEND)
            val shareBody = "${user.name}, ${user.username}, ${user.company}, ${user.location}, ${
                resources.getString(
                    R.string.following,
                    it.following
                )
            }, ${
                resources.getString(
                    R.string.followers,
                    it.follower
                )
            }, ${resources.getString(R.string.repository, it.repository)}"
            intent.type = "text/plain"
            intent.putExtra(
                Intent.EXTRA_SUBJECT,
                it.name
            )
            intent.putExtra(Intent.EXTRA_TEXT, shareBody)
            startActivity(Intent.createChooser(intent, getString(R.string.share)))
        }
    }

    companion object {
        const val EXTRA_USER = "extra_user"
    }
}