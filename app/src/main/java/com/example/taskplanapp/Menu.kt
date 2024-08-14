package com.example.taskplanapp

import android.app.DatePickerDialog
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.EditText
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.taskplanapp.R.id.Date
import com.example.taskplanapp.adapter.TarefaAdapter
import com.example.taskplanapp.banco.SQLite
import com.example.taskplanapp.entity.Tarefa
import com.google.android.material.floatingactionbutton.FloatingActionButton
import java.util.Calendar


class Menu : AppCompatActivity() {

    private lateinit var recyclerView: RecyclerView
    private lateinit var tarefaAdapter: TarefaAdapter
    private lateinit var editTextDate: EditText

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_menu)
        editTextDate = findViewById(R.id.Date)
        SQLite.setContext(this)
        val sqlite = SQLite(this)
        val floatingActionButton: FloatingActionButton = findViewById(R.id.floatingActionButton)
        recyclerView = findViewById(R.id.recyclerView)
        recyclerView.layoutManager = LinearLayoutManager(this)

        // Obtém a lista de tarefas do banco de dados
        val tarefas = sqlite.getTarefas()

        // Configura o adaptador
        tarefaAdapter = TarefaAdapter(this, tarefas)
        recyclerView.adapter = tarefaAdapter

        floatingActionButton.setOnClickListener {
            // Quando o FloatingActionButton é clicado, inicie a nova tela
            val intent = Intent(this, Tarefas::class.java)
            intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
            startActivity(intent)
        }

        editTextDate.setOnFocusChangeListener { _, hasFocus ->
            if (hasFocus) {
                showDatePickerDialog()

                //imprime a data no logcat do android studio
                val data = editTextDate.text.toString()

                if (data.isEmpty()) {
                    val tarefas = sqlite.getTarefas()
                    tarefaAdapter = TarefaAdapter(this, tarefas)
                    recyclerView.adapter = tarefaAdapter
                    return@setOnFocusChangeListener
                }
                else {
                    //busca as tarefas no banco de dados
                    val tarefas = sqlite.getTarefas(data)
                    tarefaAdapter = TarefaAdapter(this, tarefas)
                    recyclerView.adapter = tarefaAdapter
                }
            }
        }
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

    fun deleteTask(view: View) {
        val sqlite = SQLite(this)
        sqlite.deleteTask()

        val tarefas = sqlite.getTarefas()
        tarefaAdapter = TarefaAdapter(this, tarefas)
        recyclerView.adapter = tarefaAdapter
    }
}