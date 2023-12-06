package com.example.notes_sqlite

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.example.notes_sqlite.databinding.ActivityUpdateBinding

class UpdateActivity : AppCompatActivity() {

    private lateinit var binding: ActivityUpdateBinding
    private lateinit var db: PaginaDataBaseHelper
    private var paginaId: Int = -1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityUpdateBinding.inflate(layoutInflater)
        setContentView(binding.root)

        db = PaginaDataBaseHelper(this)

        paginaId = intent.getIntExtra("note_id", -1)
        if (paginaId == -1) {
            finish()
            return
        }

        val pagina = db.getNoteByID(paginaId)
        binding.updateTitleEditText.setText(pagina.title)


        binding.updateSaveButton.setOnClickListener{
            val newTitle = binding.updateTitleEditText.text.toString()
            val updateNote = Convidado(paginaId, newTitle)
            db.updateNote(updateNote)
            finish()
            Toast.makeText(this, "Convidado Editado", Toast.LENGTH_SHORT).show()
        }


    }
}