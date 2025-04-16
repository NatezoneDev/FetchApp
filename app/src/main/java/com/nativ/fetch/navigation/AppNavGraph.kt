package com.nativ.fetch.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.nativ.fetch.ui.itemlist.ItemListScreen

@Composable
fun AppNavGraph(navController: NavHostController) {
    NavHost(navController, startDestination = "itemList") {
        composable("itemList") {
            ItemListScreen()
        }
    }
}
