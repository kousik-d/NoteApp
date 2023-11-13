package com.example.room_patch1

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class NoteAdapter(var list:List<Note>) : RecyclerView.Adapter<NoteAdapter.NoteViewHolder>()  {

    inner class NoteViewHolder(noteview: View): RecyclerView.ViewHolder(noteview){
        val noteHead = noteview.findViewById<TextView>(R.id.heading_text)
        val noteText = noteview.findViewById<TextView>(R.id.Activity_text)

    }



    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NoteViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.note_item,parent,false)
        return NoteViewHolder(view)
    }

    override fun getItemCount()= list.size

    override fun onBindViewHolder(holder: NoteViewHolder, position: Int) {
        val curr=list.get(position)
        holder.noteHead.text = list.get(position).noteHead
        holder.noteText.text=list.get(position).noteData
    }
}


