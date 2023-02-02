package io.github.keddnyo.neopad.presentation.composable.main_screen

import android.app.Application
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.twotone.Add
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import io.github.keddnyo.neopad.R
import io.github.keddnyo.neopad.domain.model.Note
import io.github.keddnyo.neopad.domain.navigation.NoteNavRoute
import io.github.keddnyo.neopad.domain.viewmodel.MainViewModel
import io.github.keddnyo.neopad.domain.viewmodel.MainViewModelFactory
import io.github.keddnyo.neopad.presentation.ui.theme.NeoPadTheme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun NoteListScreen(navController: NavHostController, viewModel: MainViewModel) {

    val notes = viewModel.getAllNotes().observeAsState(initial = listOf()).value

    NeoPadTheme {
        Surface(
            modifier = Modifier.fillMaxSize(), color = MaterialTheme.colorScheme.background
        ) {
            Scaffold(modifier = Modifier.fillMaxSize(), topBar = {
                CenterAlignedTopAppBar(title = {
                    Text(
                        text = stringResource(id = R.string.app_name),
                        style = TextStyle(fontSize = 32.sp, fontFamily = FontFamily.Cursive)
                    )
                })
            }, floatingActionButton = {
                FloatingActionButton(onClick = {
                    viewModel.apply {
                        currentNote.value = Note()
                        isNoteExists.value = false
                    }
                    navController.navigate(NoteNavRoute.NoteDetail.route)
                }) {
                    Icon(
                        imageVector = Icons.TwoTone.Add,
                        contentDescription = stringResource(id = R.string.create_note)
                    )
                }
            }) { padding ->
                Box(
                    modifier = Modifier
                        .padding(padding)
                        .fillMaxSize(),
                    contentAlignment = Alignment.Center
                ) {
                    if (notes.isNotEmpty()) {
                        LazyVerticalGrid(
                            modifier = Modifier.fillMaxSize(), columns = GridCells.Adaptive(192.dp)
                        ) {
                            items(notes) { note ->
                                NoteItem(note = note) {
                                    viewModel.apply {
                                        currentNote.value = note
                                        isNoteExists.value = true
                                    }
                                    navController.navigate(NoteNavRoute.NoteDetail.route)
                                }
                            }
                            item {
                                Spacer(
                                    modifier = Modifier.height(32.dp)
                                )
                            }
                        }
                    } else {
                        Text(
                            text = stringResource(R.string.create_note_suggest),
                            fontSize = 20.sp,
                            textAlign = TextAlign.Center
                        )
                    }
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewNoteListScreen() {

    val context = LocalContext.current
    val viewModel: MainViewModel =
        viewModel(factory = MainViewModelFactory(context.applicationContext as Application))

    NoteListScreen(navController = rememberNavController(), viewModel = viewModel)
}