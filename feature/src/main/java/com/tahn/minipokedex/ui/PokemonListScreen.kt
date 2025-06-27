package com.tahn.minipokedex.ui

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.paging.compose.LazyPagingItems
import androidx.paging.compose.collectAsLazyPagingItems
import coil.compose.AsyncImage
import com.tahn.domain.model.Pokemon

@Composable
fun PokemonListScreen(viewModel: HomeViewModel) {
    val lazyPagingItems = viewModel.pokemonFlow.collectAsLazyPagingItems()

    Scaffold { innerPadding ->
        PokemonListScreenContent(modifier = Modifier.padding(innerPadding), lazyPagingItems)
    }
}

@Composable
fun PokemonListScreenContent(modifier: Modifier = Modifier, pagingItems: LazyPagingItems<Pokemon>) {
    LazyColumn(modifier = modifier) {
        items(pagingItems.itemCount) { index ->
            val pokemon = pagingItems[index]
            pokemon?.let {
                PokemonRowItem(
                    modifier = Modifier.padding(horizontal = 8.dp),
                    pokemon = it
                )
            }
        }
    }
}

@Composable
fun PokemonRowItem(modifier: Modifier = Modifier, pokemon: Pokemon) {
    Row(modifier = modifier.height(60.dp)) {
        AsyncImage(
            model = pokemon.imageUrl,
            contentDescription = null,
            modifier = Modifier.size(60.dp)
        )
        Spacer(Modifier.width(16.dp))
        Text(pokemon.name.replaceFirstChar { it.uppercase() })
    }
}


@Preview(showBackground = true)
@Composable
fun PreviewPokemonRowItem() {
    PokemonRowItem(pokemon = Pokemon(0, "Pikachu", ""))
}

