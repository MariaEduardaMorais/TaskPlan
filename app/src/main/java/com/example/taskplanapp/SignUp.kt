package com.example.taskplanapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.EditText
import com.example.taskplanapp.banco.SQLite

import android.widget.Toast

class SignUp : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sign_up)
    }


    fun create_account(view: View) {
        val nome = findViewById<EditText>(R.id.NomeUsuario)
        val email = findViewById<EditText>(R.id.Email)
        val senha = findViewById<EditText>(R.id.Senha)

        val sqlite = SQLite(this)

        val userId = sqlite.inserirUsuario(nome.text.toString(), email.text.toString(), senha.text.toString())

        if (userId != -1L) {
            Toast.makeText(this, "Usuário cadastrado com sucesso!", Toast.LENGTH_SHORT).show()
            finish()
        } else {
            Toast.makeText(this, "Erro ao cadastrar usuário", Toast.LENGTH_SHORT).show()
        }
    }
}