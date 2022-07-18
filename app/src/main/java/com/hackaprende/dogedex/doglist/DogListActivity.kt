package com.hackaprende.dogedex.doglist

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import com.hackaprende.dogedex.dogdetail.DogDetailComposeActivity
import com.hackaprende.dogedex.dogdetail.DogDetailComposeActivity.Companion.DOG_KEY
import com.hackaprende.dogedex.dogdetail.ui.theme.DogedexTheme
import com.hackaprende.dogedex.model.Dog


class DogListActivity : ComponentActivity() {

    private val viewModel: DogListViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            val status = viewModel.status
            DogedexTheme() {
                val dogList = viewModel.dogList
                DogListScreen(
                    onNavigationIconClick = ::onNavigationIconClick,
                    dogList = dogList.value,
                    onDogClicked = ::openDogDetailActivity,
                    status = status.value,
                    onErrorDialogDismiss = ::resetApiResponseStatus
                )
            }
        }
    }

    private fun resetApiResponseStatus() {
        viewModel.resetApiResponseStatus()
    }

    private fun onNavigationIconClick() {
        finish()
    }

    private fun openDogDetailActivity(dog: Dog) {
        val intent = Intent(this, DogDetailComposeActivity::class.java)
        intent.putExtra(DOG_KEY, dog)
        startActivity(intent)
    }

    /*override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = ActivityDogListBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val loadingWheel = binding.loadingWheel

        val recycler = binding.dogRecycler
        recycler.layoutManager = GridLayoutManager(this, GRID_SPAN_COUNT)

        val adapter = DogAdapter()

        adapter.setOnItemClickListener {
            val intent = Intent(this, DogDetailComposeActivity::class.java)
            intent.putExtra(DOG_KEY, it)
            startActivity(intent)
        }

        recycler.adapter = adapter

        dogListViewModel.dogList.observe(this) { dogList ->
            adapter.submitList(dogList)
        }

        dogListViewModel.status.observe(this) { status ->
            when (status) {
                is ApiResponseStatus.Error -> {
                    loadingWheel.visibility = View.GONE
                    Toast.makeText(this, status.messageId, Toast.LENGTH_LONG).show()
                }
                is ApiResponseStatus.Loading ->
                    loadingWheel.visibility = View.VISIBLE

                is ApiResponseStatus.Success ->
                    loadingWheel.visibility = View.GONE

                else -> {}
            }
        }
    }*/
}