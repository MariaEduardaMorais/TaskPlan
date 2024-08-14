package com.example.taskplanapp.banco

import android.app.Application
import android.content.Context
import java.security.MessageDigest

class EsquemaBD {

    companion object{
        val NOME_BD:String = "bd_taskplan"
        val VERSAO:Int = 1

        // Função para gerar hash da senha para armazenar no banco uma senha criptografada
        fun hashSenha(senha: String): String {
            val bytes = senha.toByteArray()
            val md = MessageDigest.getInstance("SHA-256")
            val digest = md.digest(bytes)
            return digest.fold("", { str, it -> str + "%02x".format(it) })
        }
    }

    object tabela_usuario{
        val NOME_TABELA = "usuario"
        val ID_ATT = "id_usuario"
        val NOME_ATT = "nome_usuario"
        val EMAIL_ATT = "email_usuario"
        val SENHA_ATT = "senha_usuario"
        val CRIA_TABELA = "CREATE TABLE IF NOT EXISTS $NOME_TABELA " +
                "($ID_ATT INTEGER primary key autoincrement, " + "$NOME_ATT text, $EMAIL_ATT text, $SENHA_ATT text)"

    }
    object tabela_tarefa{
        val NOME_TABELA = "tarefa"
        val ID_ATT = "id_tarefa"
        val NOME_ATT = "nome_tarefa"
        val DESCRICAO_ATT = "descricao_tarefa"
        val DATA_ATT = "data_tarefa"
        val CRIA_TABELA = "CREATE TABLE IF NOT EXISTS $NOME_TABELA " +
                "($ID_ATT INTEGER primary key autoincrement, " +
                "$NOME_ATT text, $DESCRICAO_ATT text, $DATA_ATT date)"

    }
}