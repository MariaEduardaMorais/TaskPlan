package com.example.taskplanapp

import android.app.DatePickerDialog
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.EditText
import androidx.appcompat.widget.AppCompatButton
import androidx.appcompat.widget.AppCompatEditText
import com.example.taskplanapp.banco.SQLite
import com.google.android.material.floatingactionbutton.FloatingActionButton
import java.util.Calendar

class Tarefas : AppCompatActivity() {

    private lateinit var editTextDate: EditText
    private lateinit var nomeTarefaEditText: AppCompatEditText
    private lateinit var descricaoTarefaEditText: AppCompatEditText
    private lateinit var buttonAdicionar: AppCompatButton



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_tarefas)
        editTextDate = findViewById(R.id.editTextDate)
        val floatingActionButton: FloatingActionButton = findViewById(R.id.floatingActionButton2)
        nomeTarefaEditText = findViewById(R.id.NomeTarefa)
        descricaoTarefaEditText = findViewById(R.id.DescricaoTarefa)
        buttonAdicionar = findViewById(R.id.login2)




        floatingActionButton.setOnClickListener {
            val intent = Intent(this, Menu::class.java)
            intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
            startActivity(intent)
        }
        editTextDate.setOnFocusChangeListener { _, hasFocus ->
            if (hasFocus) {
                showDatePickerDialog()
            }
        }
        buttonAdicionar.setOnClickListener {
            salvarTarefa()
        }
    }
    private fun salvarTarefa() {
        val nomeTarefa = nomeTarefaEditText.text.toString()
        val descricaoTarefa = descricaoTarefaEditText.text.toString()
        val dataTarefa = editTextDate.text.toString()

        val sqlite = SQLite(this)
        sqlite.inserirTarefa(nomeTarefa, descricaoTarefa, dataTarefa)

        // Limpe os campos após salvar
        nomeTarefaEditText.text?.clear()
        descricaoTarefaEditText.text?.clear()
        editTextDate.text.clear()
    }

    private fun showDatePickerDialog() {
        val calendar = Calendar.getInstance()

        val datePickerDialog = DatePickerDialog(
            this,
            { _, year, month, dayOfMonth ->
                val formattedDate = String.format("%02d/%02d/%04d", dayOfMonth, month + 1, year)
                editTextDate.setText(formattedDate)
            },
            calendar.get(Calendar.YEAR),
            calendar.get(Calendar.MONTH),
            calendar.get(Calendar.DAY_OF_MONTH)
        )
        datePickerDialog.show()
    }


}