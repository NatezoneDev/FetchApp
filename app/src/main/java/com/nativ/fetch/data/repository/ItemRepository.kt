package com.nativ.fetch.data.repository

import com.nativ.fetch.data.model.Item
import com.nativ.fetch.data.remote.ItemApiService
import com.nativ.fetch.utils.Result
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class ItemRepository @Inject constructor(
    private val apiService: ItemApiService
) {
    // Data manipulation is on the repo level to ensure a single source of truth
    // If there are use cases where said manipulation is not required, this can
    // be done in the VM
    fun fetchItems(): Flow<Result<List<Item>>> = flow {
        try {
            val response = apiService.getItems()
                .filter { !it.name.isNullOrBlank() }
                .sortedWith(compareBy({ it.listId }, { it.name }))
            emit(Result.Success(response))
        } catch (e: Exception) {
            emit(Result.Failure(e))
        }
    }
}