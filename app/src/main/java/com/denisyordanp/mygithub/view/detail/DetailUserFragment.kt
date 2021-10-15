package com.denisyordanp.mygithub.view.detail

import android.content.Intent
import android.os.Bundle
import android.view.*
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.navArgs
import androidx.swiperefreshlayout.widget.CircularProgressDrawable
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.bumptech.glide.Glide
import com.denisyordanp.mygithub.R
import com.denisyordanp.mygithub.databinding.FragmentDetailUserBinding
import com.denisyordanp.mygithub.models.remote.ResponseDetailUser
import com.denisyordanp.mygithub.utils.EventObserver
import com.denisyordanp.mygithub.view.detail.fragments.FollowersFragment
import com.denisyordanp.mygithub.view.detail.fragments.FollowingFragment
import com.denisyordanp.mygithub.view.detail.fragments.RepositoriesFragment
import com.google.android.material.snackbar.Snackbar
import com.google.android.material.tabs.TabLayoutMediator

class DetailUserFragment : Fragment() {

    private var binding: FragmentDetailUserBinding? = null
    private val args by navArgs<DetailUserFragmentArgs>()

    private val detailViewModel by activityViewModels<DetailViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
        (activity as AppCompatActivity).supportActionBar?.elevation = 0f
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        if (binding == null) {
            val binding = FragmentDetailUserBinding.inflate(inflater, container, false)
            this.binding = binding
        }
        return binding!!.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupPagerAdapter()
        setupListener()
        requestUserData()
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        inflater.inflate(R.menu.detail_menu, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.share -> {
                detailViewModel.detailUser.value?.let { shareUser(it) }
                true
            }
            else -> {
                super.onOptionsItemSelected(item)
            }
        }
    }

    private fun setupPagerAdapter() {
        binding?.apply {
            userViewPager.adapter = MainPagerAdapter(this@DetailUserFragment)
            TabLayoutMediator(
                userTabLayout,
                userViewPager,
                false,
                true
            ) { tab, pos ->
                when (pos) {
                    0 -> tab.text = resources.getString(R.string.tab_followers)
                    1 -> tab.text = resources.getString(R.string.tab_following)
                    2 -> tab.text = resources.getString(R.string.tab_repository)
                }
            }.attach()
        }
    }

    private fun setupListener() {
        detailViewModel.detailUser.observe(viewLifecycleOwner) {
            setView(it)
        }
        detailViewModel.isDetailUserLoading.observe(viewLifecycleOwner) {
            showLoading(it)
        }
        detailViewModel.showSnackEvent.observe(viewLifecycleOwner, EventObserver {
            showError(it)
        })
    }

    private fun requestUserData() {
        detailViewModel.requestUserData(args.extraUsername)
    }

    private fun showLoading(isLoading: Boolean) {
        binding?.apply {
            detailProgressBar.isVisible = isLoading
        }
    }

    private fun showError(message: Int) {
        showLoading(false)
        Snackbar.make(requireView(), message, Snackbar.LENGTH_SHORT).show()
    }

    private fun setView(responseUser: ResponseDetailUser) {
        binding?.apply {
            nameTextView.text = responseUser.name
            usernameTextView.text = responseUser.username
            locationTextView.text = responseUser.location.locationText()
            companyTextView.text = responseUser.company.companyText()
            userImageView.load(responseUser.avatarUrl)

            val followers = resources.getString(R.string.followers, responseUser.followers)
            followersTextView.text = followers

            val following = resources.getString(R.string.followings, responseUser.following)
            followingTextView.text = following

            val repositories = resources.getString(R.string.repository, responseUser.publicRepos)
            repositoryTextView.text = repositories
        }
    }

    private fun String?.companyText(): String {
        return if (this.isNullOrEmpty()) resources.getString(R.string.no_in_company) else this
    }

    private fun String?.locationText(): String {
        return if (this.isNullOrEmpty()) resources.getString(R.string.no_location) else this
    }

    private fun ImageView.load(url: String) {
        val placeHolder = CircularProgressDrawable(this.context).apply {
            strokeWidth = 5f
            centerRadius = 30f
            start()
        }
        Glide.with(this.context)
            .load(url)
            .placeholder(placeHolder)
            .error(R.drawable.ic_broken_image)
            .circleCrop()
            .into(this)
    }

    private fun shareUser(responseUser: ResponseDetailUser?) {
        responseUser?.let {
            val intent = Intent(Intent.ACTION_SEND)
            val shareBody =
                "${responseUser.name}, ${responseUser.username}, ${responseUser.company}, ${responseUser.location}"
            intent.type = "text/plain"
            intent.putExtra(
                Intent.EXTRA_SUBJECT,
                it.name
            )
            intent.putExtra(Intent.EXTRA_TEXT, shareBody)
            startActivity(Intent.createChooser(intent, getString(R.string.share)))
        }
    }

    class MainPagerAdapter(fragment: Fragment) :
        FragmentStateAdapter(fragment) {

        override fun getItemCount(): Int = 3

        override fun createFragment(position: Int): Fragment {
            return when (position) {
                0 -> FollowersFragment()
                1 -> FollowingFragment()
                2 -> RepositoriesFragment()
                else -> throw IllegalStateException("invalid position:$position")
            }
        }
    }
}