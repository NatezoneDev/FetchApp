package com.nativ.fetch.data.remote

import com.nativ.fetch.data.model.Item
import retrofit2.http.GET

interface ItemApiService {
    @GET("hiring.json")
    suspend fun getItems(): List<Item>
}
