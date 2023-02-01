package io.github.keddnyo.neopad.data.database

import androidx.lifecycle.LiveData
import io.github.keddnyo.neopad.domain.model.Note

interface DatabaseRepository {

    val getAllNotes: LiveData<List<Note>>

    suspend fun createNote(note: Note, onSuccess: () -> Unit)

    suspend fun updateNote(note: Note, onSuccess: () -> Unit)

    suspend fun deleteNote(note: Note, onSuccess: () -> Unit)

}