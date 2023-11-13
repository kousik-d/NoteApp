package com.example.room_patch1

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.async
import kotlinx.coroutines.launch

class NoteViewModel(val noteRepository: NoteRepository): ViewModel() {

    val allNotes :LiveData<List<Note>> = noteRepository.allNotes.asLiveData()

    companion object {
        var count = 0
    }

    fun insertNote(note : Note) = viewModelScope.launch(IO) {
        noteRepository.insertNote(note)
    }

    fun deleteNote(note: Note) = viewModelScope.launch(IO) {
        noteRepository.deleteNote(note)
    }

    fun updateNote(note: Note) = viewModelScope.launch(IO) {
        noteRepository.updateNote(note)
    }

    suspend fun getFirstNote() : Note = viewModelScope.async {
            noteRepository.getFirstNote()
        }.await()

}