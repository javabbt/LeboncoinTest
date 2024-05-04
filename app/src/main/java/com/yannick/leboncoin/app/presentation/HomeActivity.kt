package com.yannick.leboncoin.app.presentation

import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.appcompat.app.AppCompatActivity
import com.yannick.leboncoin.base.presentation.compose.theme.AndroidLeboncoinAppTheme

class HomeActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            AndroidLeboncoinAppTheme {
                HomeContainer()
            }
        }
    }
}
