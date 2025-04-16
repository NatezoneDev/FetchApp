package com.nativ.fetch.ui.itemlist

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.nativ.fetch.R
import com.nativ.fetch.ui.common.UiState

@Composable
fun ItemListScreen(
    viewModel: ItemViewModel = hiltViewModel()
) {
    val state by viewModel.uiState.collectAsStateWithLifecycle()

    when (val currentState = state) {
        is UiState.Loading -> {
            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                CircularProgressIndicator()
            }
        }

        is UiState.Error -> {
            val error = currentState.message ?: stringResource(id = R.string.something_went_wrong)
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(dimensionResource(id = R.dimen.padding)),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = stringResource(R.string.error_value, error),
                    color = MaterialTheme.colorScheme.error
                )
            }
        }

        is UiState.Success -> {
            val grouped = currentState.data.groupBy { it.listId }

            LazyColumn(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(dimensionResource(id = R.dimen.padding)),
                verticalArrangement = Arrangement.spacedBy(
                    dimensionResource(id = R.dimen.list_vertical_spacing))
            ) {
                grouped.forEach { (listId, items) ->
                    item {
                        Text(
                            text = stringResource(id = R.string.list_id_value, listId),
                            style = MaterialTheme.typography.titleLarge,
                            color = MaterialTheme.colorScheme.primary,
                            modifier = Modifier.padding(
                                vertical = dimensionResource(id = R.dimen.padding_small))
                        )
                    }

                    items(items) { item ->
                        Card(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(horizontal = dimensionResource(id = R.dimen.padding_x_small)),
                            shape = RoundedCornerShape(dimensionResource(id = R.dimen.radius)),
                            elevation = CardDefaults.cardElevation(
                                defaultElevation = dimensionResource(id = R.dimen.elevation))
                        ) {
                            Text(
                                text = item.name ?: stringResource(R.string.unnamed),
                                modifier = Modifier
                                    .padding(dimensionResource(id = R.dimen.padding)),
                                style = MaterialTheme.typography.bodyLarge
                            )
                        }
                    }

                    item {
                        Spacer(modifier = Modifier.height(dimensionResource(id = R.dimen.spacer_height)))
                        HorizontalDivider()
                    }
                }
            }
        }
    }
}