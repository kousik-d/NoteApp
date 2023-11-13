package com.example.room_patch1

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName="NoteTable")
data class Note (
    @PrimaryKey(autoGenerate = true)
    var noteId:Int =0 ,
    var noteHead:String,
    var noteData:String
        )