package com.tahn.minipokedex

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent

import org.koin.androidx.viewmodel.ext.android.viewModel

class MainActivity : ComponentActivity() {
    private val viewModel: com.tahn.minipokedex.ui.HomeViewModel by viewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            AppNavHost()
        }
    }
}

