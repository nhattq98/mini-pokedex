package com.tahn.minipokedex.ui.feature

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.paging.compose.collectAsLazyPagingItems
import coil.compose.rememberImagePainter
import org.koin.androidx.viewmodel.ext.android.viewModel

class MainActivity : ComponentActivity() {
    private val viewModel: HomeViewModel by viewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            PokemonListScreen(viewModel)
        }
    }
}

@Composable
fun PokemonListScreen(viewModel: HomeViewModel) {
    val lazyPagingItems = viewModel.pokemonFlow.collectAsLazyPagingItems()

    LazyColumn {
        items(lazyPagingItems.itemCount) { index ->
            val pokemon = lazyPagingItems[index]
            if (pokemon != null) {
                Row(modifier = Modifier.padding(8.dp)) {
                    Image(
                        painter = rememberImagePainter(pokemon.imageUrl),
                        contentDescription = null,
                        modifier = Modifier.run { size(60.dp) },
                    )
                    Spacer(Modifier.width(16.dp))
                    Text(pokemon.name.replaceFirstChar { it.uppercase() })
                }
            }
        }
    }
}
