package com.nativ.fetch.ui.itemlist

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.nativ.fetch.data.model.Item
import com.nativ.fetch.data.repository.ItemRepository
import com.nativ.fetch.ui.common.UiState
import com.nativ.fetch.utils.Result
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ItemViewModel @Inject constructor(
    private val repository: ItemRepository
) : ViewModel() {

    private val _uiState = MutableStateFlow<UiState<List<Item>>>(UiState.Loading)
    val uiState: StateFlow<UiState<List<Item>>> = _uiState

    init {
        fetchItems()
    }

    private fun fetchItems() {
        viewModelScope.launch(Dispatchers.IO) {
            repository.fetchItems().collect { result ->
                _uiState.value = when (result) {
                    is Result.Success -> UiState.Success(result.data)
                    is Result.Failure -> UiState.Error(result.exception.message)
                }
            }
        }
    }
}