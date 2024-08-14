package com.example.taskplanapp.ui.login

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.text.method.PasswordTransformationMethod
import android.view.MotionEvent
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.widget.addTextChangedListener
import com.example.taskplanapp.Menu
import com.example.taskplanapp.data.model.LoggedInUser
import com.example.taskplanapp.R
import com.example.taskplanapp.SignUp
import com.example.taskplanapp.banco.SQLite

class LoginActivity : AppCompatActivity() {

    private lateinit var editTextEmail: EditText
    private lateinit var editTextPassword: EditText
    private lateinit var btnLogin: Button
    private lateinit var btnCadastro: TextView
    private lateinit var imageViewShowPassword: ImageView
    private var isPasswordVisible = false

    @SuppressLint("ClickableViewAccessibility")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
        SQLite.setContext(this)
        val sqlite = SQLite(this)

        editTextEmail = findViewById(R.id.username)
        editTextPassword = findViewById(R.id.password)
        btnLogin = findViewById(R.id.login)
        btnCadastro = findViewById(R.id.bntCadastro)
        imageViewShowPassword = findViewById(R.id.imageOlho)

        imageViewShowPassword.setOnClickListener {
            // Alternar a visibilidade da senha
            isPasswordVisible = !isPasswordVisible
            updatePasswordVisibility()
        }


        btnLogin.setOnClickListener {
            val email = editTextEmail.text.toString()
            val password = editTextPassword.text.toString()

            // Verifica se os campos estão preenchidos
            if (email.isNotEmpty() && password.isNotEmpty()) {
                // Autenticação usando a classe SQLite
                val loggedInUser = sqlite.getUser(email, password)

                if (loggedInUser.first) {
                    // Login bem-sucedido, você pode navegar para a próxima atividade
                    val intent = Intent(this, Menu::class.java)
                    intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
                    startActivity(intent)
                    Toast.makeText(this, "Login efetuado com sucesso :)", Toast.LENGTH_SHORT).show()
                    finish()
                } else {
                    // Exibe uma mensagem de erro, por exemplo, usando um Toast
                    Toast.makeText(this, "Credenciais inválidas", Toast.LENGTH_SHORT).show()
                }
            } else {
                Toast.makeText(this, "Preencha todos os campos", Toast.LENGTH_SHORT).show()
            }
        }


        btnCadastro.setOnClickListener {
            // Inicia a tela de cadastro quando o botão de cadastro é clicado
            val intent = Intent(this, SignUp::class.java)
            startActivity(intent)
        }
    }

    private fun updatePasswordVisibility() {
        if (isPasswordVisible) {
            // Se a senha deve ser visível, defina o método de transformação para null
            editTextPassword.transformationMethod = null
            // Altere o ícone do olho para o ícone de olho aberto
            imageViewShowPassword.setImageResource(R.drawable.ic_eye)
        } else {
            // Se a senha deve ser oculta, defina o método de transformação como PasswordTransformationMethod
            editTextPassword.transformationMethod = PasswordTransformationMethod.getInstance()
        }
    }

}



