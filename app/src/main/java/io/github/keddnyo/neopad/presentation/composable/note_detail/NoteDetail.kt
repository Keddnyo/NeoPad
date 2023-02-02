package io.github.keddnyo.neopad.presentation.composable.note_detail

import android.app.Application
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.twotone.ArrowBack
import androidx.compose.material.icons.twotone.Done
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.input.KeyboardCapitalization
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import io.github.keddnyo.neopad.domain.viewmodel.MainViewModel
import io.github.keddnyo.neopad.domain.viewmodel.MainViewModelFactory
import io.github.keddnyo.neopad.presentation.ui.theme.NeoPadTheme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun NoteDetail(navController: NavHostController, viewModel: MainViewModel) {

    val currentNote = viewModel.currentNote.value

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

                    }) {
                        Icon(
                            imageVector = Icons.TwoTone.ArrowBack,
                            contentDescription = "" // TODO: Replace
                        )
                    }
                })
            }, floatingActionButton = {
                FloatingActionButton(onClick = {

                }) {
                    Icon(
                        imageVector = Icons.TwoTone.Done, contentDescription = "" // TODO: Replace
                    )
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

@Preview(showBackground = true)
@Composable
fun PreviewNoteDetail() {
    val context = LocalContext.current
    val navController = rememberNavController()

    val viewModel: MainViewModel =
        viewModel(factory = MainViewModelFactory(context.applicationContext as Application))

    NoteDetail(navController, viewModel)
}