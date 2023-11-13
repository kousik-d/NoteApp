package com.example.room_patch1

import android.app.Dialog
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.floatingactionbutton.FloatingActionButton
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.Dispatchers.Main
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class MainActivity : AppCompatActivity() {
    private lateinit var recyclerView: RecyclerView
    private lateinit var addButton: Button
    private lateinit var listNotes : List<Note>
    private lateinit var noteViewModel: NoteViewModel
    private lateinit var noteAdapter: NoteAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        recyclerView = findViewById(R.id.RVItems)
        addButton = findViewById(R.id.AddNote)
        listNotes = listOf()

        noteAdapter = NoteAdapter(listNotes)
        recyclerView.adapter=noteAdapter
        recyclerView.layoutManager = LinearLayoutManager(this)

        val noteRepository = NoteRepository(NoteDataBase.invoke(this))
        val factory = ViewModelFactory(noteRepository)

        val deleteBtn : Button = findViewById(R.id.DeleteBtn)

        noteViewModel = ViewModelProvider(this,factory).get(NoteViewModel::class.java)


        noteViewModel.allNotes.observe(this, Observer {
            //Log.i("LISTED - Count","$it count ${NoteViewModel.count}")
            noteAdapter.list =it
            noteAdapter.notifyDataSetChanged()
        })

        deleteBtn.setOnClickListener{
            CoroutineScope(IO).launch {
                val res=noteViewModel.getFirstNote()
                withContext(Main) {
                    res?.let {
                        deleteNote(it)

                    }
                }
            }
        }

        deleteBtn.setOnLongClickListener {
            noteViewModel.deleteAllNote()
            Toast.makeText(this,"Deleted All",Toast.LENGTH_SHORT).show()
            true
        }

        addButton.setOnClickListener {
            OpenDialog()
        }
    }
    fun deleteNote(note:Note){
        noteViewModel.deleteNote(note)
        noteAdapter.notifyDataSetChanged()
        Toast.makeText(this, "First Item Deleted", Toast.LENGTH_SHORT).show()
    }
    fun OpenDialog(){
        val dialog = Dialog(this)
        dialog.setContentView(R.layout.add_item_dialog)
        val addBtn = dialog.findViewById<Button>(R.id.idBtnAdd)
        val cancelBtn = dialog.findViewById<Button>(R.id.idBtnCancel)
        val noteHead = dialog.findViewById<EditText>(R.id.NoteHeadingId)
        val noteText = dialog.findViewById<EditText>(R.id.NoteTextid)
        cancelBtn.setOnClickListener {
            dialog.dismiss()
        }
        addBtn.setOnClickListener {
            if (noteHead.text.isNotEmpty() && noteText.text.isNotEmpty()) {
                val note =
                    Note(0,noteHead=noteHead.text.toString(), noteData = noteText.text.toString())
                noteViewModel.insertNote(note)
                Toast.makeText(this, "Item Inserted", Toast.LENGTH_SHORT).show()
                noteAdapter.notifyDataSetChanged()
                dialog.dismiss()
            }else{
                Toast.makeText(this, "Enter All values", Toast.LENGTH_SHORT).show()
            }
        }

        dialog.show()
    }



}