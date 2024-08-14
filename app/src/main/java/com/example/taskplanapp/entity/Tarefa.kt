package com.example.taskplanapp.entity
data class Tarefa(
    val id: Int,
    val nome: String,
    val descricao: String,
    val data: String // Pode ser um formato de data mais apropriado
) {
    override fun toString(): String {
        return "Tarefa(id=$id, nome='$nome', descricao='$descricao', data='$data')"
    }
}


