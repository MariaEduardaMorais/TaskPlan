package com.example.taskplanapp.adapter

import android.content.Context
import android.text.method.TextKeyListener.clear
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.taskplanapp.R
import com.example.taskplanapp.banco.SQLite
import com.example.taskplanapp.entity.Tarefa
import java.util.Collections.addAll

class TarefaAdapter(private val context: Context, private val tarefas: List<Tarefa>) : RecyclerView.Adapter<TarefaAdapter.TarefaViewHolder>() {

    inner class TarefaViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val idTextView: TextView = itemView.findViewById(R.id.idTextView)
        val taskNameTextView: TextView = itemView.findViewById(R.id.TaskNameTextView)
        val descriptionTextView: TextView = itemView.findViewById(R.id.DescriptionTextView)
        val dateTextView: TextView = itemView.findViewById(R.id.DateTextView)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TarefaViewHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.item_tarefas, parent, false)
        return TarefaViewHolder(view)
    }

    override fun onBindViewHolder(holder: TarefaViewHolder, position: Int) {
        val tarefa = tarefas[position]

        holder.idTextView.text = "ID: ${tarefa.id.toString()}"
        holder.taskNameTextView.text = "Nome da Tarefa: ${tarefa.nome}"
        holder.descriptionTextView.text = "Descrição: ${tarefa.descricao}"
        holder.dateTextView.text = "Data: ${tarefa.data}"

    }

    override fun getItemCount(): Int {
        return tarefas.size
    }

}
