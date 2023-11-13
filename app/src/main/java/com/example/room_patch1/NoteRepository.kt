package com.example.room_patch1

import androidx.lifecycle.LiveData
import kotlinx.coroutines.flow.Flow

class NoteRepository(private val dataBase: NoteDataBase) {

    val allNotes : Flow<List<Note>> = dataBase.getNoteDao().getAllNotes()

    suspend fun insertNote(note: Note) = dataBase.getNoteDao().insertNote(note)

    suspend fun deleteNote(note: Note) = dataBase.getNoteDao().deleteNote(note)

    suspend fun updateNote(note: Note) = dataBase.getNoteDao().updateNote(note)

    suspend fun getFirstNote() = dataBase.getNoteDao().getTopNote()
}