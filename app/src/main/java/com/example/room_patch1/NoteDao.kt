package com.example.room_patch1

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import kotlinx.coroutines.flow.Flow

@Dao
interface NoteDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertNote(note:Note)

    @Delete
    suspend fun deleteNote(note:Note)

    @Update
    suspend fun updateNote(note: Note)

    @Query("SELECT * FROM NoteTable")
    fun getAllNotes(): Flow<List<Note>>

    @Query("SELECT * FROM NoteTable ORDER BY noteId LIMIT 1")
    suspend fun getTopNote(): Note
}