package io.github.keddnyo.neopad.domain.navigation

import android.app.Application
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import io.github.keddnyo.neopad.domain.viewmodel.MainViewModel
import io.github.keddnyo.neopad.domain.viewmodel.MainViewModelFactory
import io.github.keddnyo.neopad.presentation.composable.note_detail.NoteDetail

@Composable
fun NoteNavHost() {

    val context = LocalContext.current
    val navController = rememberNavController()

    val viewModel: MainViewModel =
        viewModel(factory = MainViewModelFactory(context.applicationContext as Application))

    NavHost(
        navController = navController,
        startDestination = NoteNavRoute.NoteDetail.route
    ) {
        composable(NoteNavRoute.MainScreen.route) {

        }
        composable(NoteNavRoute.NoteDetail.route) {
            NoteDetail(navController, viewModel)
        }
    }

}