package io.github.keddnyo.neopad.presentation.composable.note_detail

import android.app.Application
import android.widget.Toast
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.twotone.ArrowBack
import androidx.compose.material.icons.twotone.Delete
import androidx.compose.material.icons.twotone.Done
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.KeyboardCapitalization
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
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
fun NoteDetail(navController: NavHostController, viewModel: MainViewModel) {

    val context = LocalContext.current

    val currentNote = viewModel.currentNote.value
    val isNoteExists = viewModel.isNoteExists.value

    var title by remember { mutableStateOf(currentNote.title) }
    var content by remember { mutableStateOf(currentNote.content) }

    NeoPadTheme {
        Surface(
            modifier = Modifier.fillMaxSize(), color = MaterialTheme.colorScheme.background
        ) {
            Scaffold(topBar = {
                CenterAlignedTopAppBar(title = {
                    BasicTextField(value = title,
                        onValueChange = {
                            if (it.length <= 15) title = it
                        },
                        textStyle = MaterialTheme.typography.headlineMedium.copy(
                            color = MaterialTheme.colorScheme.onBackground,
                            textAlign = TextAlign.Center
                        ),
                        keyboardOptions = KeyboardOptions.Default.copy(
                            capitalization = KeyboardCapitalization.Sentences
                        ),
                        singleLine = true,
                        cursorBrush = SolidColor(MaterialTheme.colorScheme.onBackground),
                        decorationBox = { innerTextField ->
                            Box(
                                contentAlignment = Alignment.Center
                            ) {
                                if (title.isEmpty()) {
                                    Text(
                                        text = "Title",
                                        style = MaterialTheme.typography.headlineMedium.copy(
                                            color = MaterialTheme.colorScheme.onBackground.copy(
                                                alpha = 0.3f
                                            )
                                        )
                                    )
                                }
                                innerTextField()
                            }
                        })
                }, navigationIcon = {
                    IconButton(onClick = {
                        popUp(navController)
                    }) {
                        Icon(
                            imageVector = Icons.TwoTone.ArrowBack,
                            contentDescription = stringResource(id = R.string.pop_up)
                        )
                    }
                }, actions = {
                    if (isNoteExists) {
                        IconButton(onClick = {
                            viewModel.deleteNote(currentNote) {
                                Toast.makeText(
                                    context,
                                    context.getString(R.string.note_deleted),
                                    Toast.LENGTH_SHORT
                                ).show()
                                popUp(navController)
                            }
                        }) {
                            Icon(
                                imageVector = Icons.TwoTone.Delete,
                                contentDescription = stringResource(id = R.string.delete_note)
                            )
                        }
                    }
                })
            }, floatingActionButton = {
                if (title.isNotBlank() && content.isNotBlank()) {
                    FloatingActionButton(onClick = {
                        modifyDatabase(
                            viewModel, Note(
                                currentNote.id, title, content
                            ), isNoteExists
                        ) {
                            popUp(navController)
                        }
                    }) {
                        Icon(
                            imageVector = Icons.TwoTone.Done,
                            contentDescription = stringResource(id = R.string.save_note)
                        )
                    }
                }
            }) { padding ->
                TextField(value = "", onValueChange = {})
                BasicTextField(value = content,
                    onValueChange = {
                        content = it
                    },
                    modifier = Modifier
                        .padding(padding)
                        .padding(16.dp)
                        .fillMaxSize(),
                    textStyle = MaterialTheme.typography.bodyLarge.copy(
                        color = MaterialTheme.colorScheme.onBackground
                    ),
                    keyboardOptions = KeyboardOptions.Default.copy(
                        capitalization = KeyboardCapitalization.Sentences
                    ),
                    cursorBrush = SolidColor(MaterialTheme.colorScheme.onBackground),
                    decorationBox = { innerTextField ->
                        Box {
                            if (content.isEmpty()) {
                                Text(
                                    text = "Note", style = MaterialTheme.typography.bodyLarge.copy(
                                        color = MaterialTheme.colorScheme.onBackground.copy(
                                            alpha = 0.3f
                                        )
                                    )
                                )
                            }
                            innerTextField()
                        }
                    })
            }
        }
    }
}

private fun popUp(navController: NavHostController) {
    navController.navigate(NoteNavRoute.MainScreen.route) {
        popUpTo(NoteNavRoute.MainScreen.route) {
            inclusive = true
        }
    }
}

private fun modifyDatabase(
    viewModel: MainViewModel, note: Note, isNoteExists: Boolean, onSuccess: () -> Unit
) {
    if (isNoteExists) {
        viewModel.updateNote(
            note
        ) {
            onSuccess()
        }
    } else {
        viewModel.createNote(
            note
        ) {
            onSuccess()
        }
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewNoteDetail() {
    val context = LocalContext.current
    val navController = rememberNavController()

    val viewModel: MainViewModel =
        viewModel(factory = MainViewModelFactory(context.applicationContext as Application))

    NoteDetail(navController, viewModel)
}