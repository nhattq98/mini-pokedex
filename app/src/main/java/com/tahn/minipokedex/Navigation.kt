package com.tahn.minipokedex

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.tahn.minipokedex.ui.PokemonListScreen
import kotlinx.serialization.Serializable
import org.koin.androidx.compose.koinViewModel

@Serializable
object PokemonList

@Serializable
object PokemonDetail

@Composable
fun AppNavHost(navController: NavHostController = rememberNavController()) {
    NavHost(
        navController = navController,
        startDestination = PokemonList
    ) {
        composable<PokemonList> {
            PokemonListScreen(viewModel = koinViewModel())
        }
    }
}