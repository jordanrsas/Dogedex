package com.hackaprende.dogedex.doglist

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import com.hackaprende.dogedex.dogdetail.DogDetailComposeActivity
import com.hackaprende.dogedex.dogdetail.DogDetailComposeActivity.Companion.DOG_KEY
import com.hackaprende.dogedex.dogdetail.ui.theme.DogedexTheme
import com.hackaprende.dogedex.model.Dog
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class DogListActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            DogedexTheme() {
                DogListScreen(
                    onNavigationIconClick = ::onNavigationIconClick,
                    onDogClicked = ::openDogDetailActivity
                )
            }
        }
    }

    private fun onNavigationIconClick() {
        finish()
    }

    private fun openDogDetailActivity(dog: Dog) {
        val intent = Intent(this, DogDetailComposeActivity::class.java)
        intent.putExtra(DOG_KEY, dog)
        startActivity(intent)
    }
}