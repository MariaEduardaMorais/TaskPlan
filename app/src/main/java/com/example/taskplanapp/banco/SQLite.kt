package com.example.taskplanapp.banco

import android.annotation.SuppressLint
import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import android.util.Log
import com.example.taskplanapp.banco.EsquemaBD.tabela_tarefa
import com.example.taskplanapp.banco.EsquemaBD.tabela_usuario
import com.example.taskplanapp.data.model.LoggedInUser
import com.example.taskplanapp.entity.Tarefa


class SQLite(context: Context) : SQLiteOpenHelper(context, EsquemaBD.NOME_BD, null, EsquemaBD.VERSAO)  {

    companion object {
        lateinit var baseContext: Context
            private set

        fun setContext(context: Context) {
            baseContext = context
        }
    }

    //executa quando o banco é criado
    override fun onCreate(db: SQLiteDatabase) {
        db.execSQL(tabela_usuario.CRIA_TABELA)
        db.execSQL(tabela_tarefa.CRIA_TABELA)

    }

    //executa quando a versao passada no construtor é diferente da "instalada"
    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        TODO("Not yet implemented")
    }

    fun inserirUsuario(username: String, email: String, password: String): Long {
        return writableDatabase.use { db ->
            val valores = ContentValues()
            valores.put(tabela_usuario.NOME_ATT, username)
            valores.put(tabela_usuario.EMAIL_ATT, email)
            valores.put(tabela_usuario.SENHA_ATT, EsquemaBD.hashSenha(password))

            return db.insert(tabela_usuario.NOME_TABELA, null, valores)
        }
    }


    fun getUser(identifier: String, password: String): Pair<Boolean, LoggedInUser> {
        return readableDatabase.use { db ->
            val query = "SELECT * FROM ${tabela_usuario.NOME_TABELA} WHERE " +
                    "(${tabela_usuario.EMAIL_ATT} = ? OR ${tabela_usuario.NOME_ATT} = ?) " +
                    "AND ${tabela_usuario.SENHA_ATT} = ?"
            val cursor = db.rawQuery(query, arrayOf(identifier, identifier, EsquemaBD.hashSenha(password)))

            var user = LoggedInUser("", "", false)
            var userFound = false

            try {
                if (cursor.moveToFirst()) {
                    val nomeAttIndex = cursor.getColumnIndex(EsquemaBD.tabela_usuario.NOME_ATT)
                    val emailAttIndex = cursor.getColumnIndex(EsquemaBD.tabela_usuario.EMAIL_ATT)

                    user = LoggedInUser(
                        cursor.getString(nomeAttIndex),
                        cursor.getString(emailAttIndex),
                        true
                    )
                    userFound = true
                }
            } finally {
                cursor.close()
            }

            Log.d("LoginDebug", "User Found: $userFound")
            Log.d("LoginDebug", "User Data: $user")

            return Pair(userFound, user)
        }
    }


    fun getTarefas(): List<Tarefa> {
        val db = readableDatabase
        val tarefas = mutableListOf<Tarefa>()

        val query = "SELECT * FROM ${EsquemaBD.tabela_tarefa.NOME_TABELA}"
        val cursor = db.rawQuery(query, null)

        try {
            while (cursor.moveToNext()) {
                val idIndex = cursor.getColumnIndex(EsquemaBD.tabela_tarefa.ID_ATT)
                val nomeIndex = cursor.getColumnIndex(EsquemaBD.tabela_tarefa.NOME_ATT)
                val descricaoIndex = cursor.getColumnIndex(EsquemaBD.tabela_tarefa.DESCRICAO_ATT)
                val dataIndex = cursor.getColumnIndex(EsquemaBD.tabela_tarefa.DATA_ATT)

                val id = cursor.getInt(idIndex)
                val nome = cursor.getString(nomeIndex)
                val descricao = cursor.getString(descricaoIndex)
                val data = cursor.getString(dataIndex)

                // Aqui, você precisa ter uma data Tarefa, supondo que você tenha uma classe Tarefa
                val tarefa = Tarefa(id, nome, descricao, data)
                tarefas.add(tarefa)
            }
        } finally {
            cursor.close()
            db.close()
        }

        return tarefas
    }

    //getTarefas com filtro de data
    fun getTarefas(data: String): List<Tarefa> {
        val db = readableDatabase
        val tarefas = mutableListOf<Tarefa>()

        val query = "SELECT * FROM ${EsquemaBD.tabela_tarefa.NOME_TABELA} WHERE ${EsquemaBD.tabela_tarefa.DATA_ATT} = ?"
        val cursor = db.rawQuery(query, arrayOf(data))

        try {
            while (cursor.moveToNext()) {
                val idIndex = cursor.getColumnIndex(EsquemaBD.tabela_tarefa.ID_ATT)
                val nomeIndex = cursor.getColumnIndex(EsquemaBD.tabela_tarefa.NOME_ATT)
                val descricaoIndex = cursor.getColumnIndex(EsquemaBD.tabela_tarefa.DESCRICAO_ATT)
                val dataIndex = cursor.getColumnIndex(EsquemaBD.tabela_tarefa.DATA_ATT)

                val id = cursor.getInt(idIndex)
                val nome = cursor.getString(nomeIndex)
                val descricao = cursor.getString(descricaoIndex)
                val data = cursor.getString(dataIndex)

                // Aqui, você precisa ter uma data Tarefa, supondo que você tenha uma classe Tarefa
                val tarefa = Tarefa(id, nome, descricao, data)
                tarefas.add(tarefa)
            }
        } finally {
            cursor.close()
            db.close()
        }

        return tarefas
    }

    fun inserirTarefa(nome: String, descricao: String, data: String): Long {
        var db = writableDatabase
        var valores = ContentValues()
        valores.put(tabela_tarefa.NOME_ATT, nome)
        valores.put(tabela_tarefa.DESCRICAO_ATT, descricao)
        valores.put(tabela_tarefa.DATA_ATT, data)

        var id = db.insert(tabela_tarefa.NOME_TABELA, null, valores)

        db.close()
        return id
    }

    fun excluirTarefa(id: Int) {
        val db = this.writableDatabase
        db.delete(tabela_tarefa.NOME_TABELA, "${tabela_tarefa.ID_ATT} = ?", arrayOf(id.toString()))
        db.close()
    }

    fun deleteTask() {
        //exclui o primeiro registro
        val db = this.writableDatabase
        val query = "SELECT * FROM ${EsquemaBD.tabela_tarefa.NOME_TABELA}"
        val cursor = db.rawQuery(query, null)
        cursor.moveToFirst()
        val idIndex = cursor.getColumnIndex(EsquemaBD.tabela_tarefa.ID_ATT)
        val id = cursor.getInt(idIndex)
        db.delete(tabela_tarefa.NOME_TABELA, "${tabela_tarefa.ID_ATT} = ?", arrayOf(id.toString()))
        db.close()
    }
}
