package com.example.ghas_poc

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity

class SqlInjectionActivity : AppCompatActivity() {

    private lateinit var usernameEditText: EditText
    private lateinit var passwordEditText: EditText
    private lateinit var loginButton: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sql_injection)

        // Inicialização dos componentes de UI
        usernameEditText = findViewById(R.id.editTextText)
        passwordEditText = findViewById(R.id.editTextTextPassword)
        loginButton = findViewById(R.id.button)

        // Definição do evento de clique do botão de login
        loginButton.setOnClickListener {
            // Obtenção dos valores dos campos de usuário e senha
            val username = usernameEditText.text.toString()
            val password = passwordEditText.text.toString()

            // Verificação da autenticação utilizando injeção de SQL
            if (authenticateUser(username, password)) {
                // Se a autenticação for bem-sucedida, faça algo, como redirecionar para outra activity

                Toast.makeText(this, "Autenticado com sucesso!", Toast.LENGTH_LONG).show()
            } else {
                // Se a autenticação falhar, exiba uma mensagem de erro ou tome outra ação apropriada
                Toast.makeText(this, "Não autenticado, inválido", Toast.LENGTH_LONG).show()
            }
        }
    }

    // Método para autenticar o usuário, vulnerável a injeção de SQL
    private fun authenticateUser(username: String, password: String): Boolean {
        // Construção da consulta SQL vulnerável
        val query = "SELECT * FROM users WHERE username='$username' AND password='$password'"

        // Inicialização do banco de dados
        val dbHelper = AuthDatabaseHelper(this)
        val db = dbHelper.readableDatabase

        // Execução da consulta SQL
        val cursor = db.rawQuery(query, null)

        // Verificação se a consulta retornou alguma linha (ou seja, se o usuário foi autenticado)
        val result = cursor.count > 0

        // Fechamento do cursor e do banco de dados
        cursor.close()
        db.close()

        // Retorno do resultado da autenticação
        return result
    }
}

// Classe para auxiliar na criação e gerenciamento do banco de dados de autenticação
class AuthDatabaseHelper(context: Context) : SQLiteOpenHelper(context, DATABASE_NAME, null, DATABASE_VERSION) {

    override fun onCreate(db: SQLiteDatabase) {
        // Criar a nova tabela de usuários com a coluna COLUMN_USERNAME
        db.execSQL(CREATE_TABLE_USERS)

        // Inserir um usuário e senha de teste
        val values = ContentValues().apply {
            put(COLUMN_USERNAME, "user_test")
            put(COLUMN_PASSWORD, "password_test")
        }
        db.insert(TABLE_USERS, null, values)
    }

    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
        db.execSQL("DROP TABLE IF EXISTS $TABLE_USERS") // Excluir a tabela antiga se existir
        onCreate(db) // Criar a nova tabela
    }

    companion object {
        private const val DATABASE_NAME = "auth.db" // Nome do novo banco de dados
        private const val DATABASE_VERSION = 1 // Versão do novo banco de dados

        private const val TABLE_USERS = "users"
        private const val COLUMN_ID = "_id"
        private const val COLUMN_USERNAME = "username" // Nova coluna adicionada
        private const val COLUMN_PASSWORD = "password"

        // SQL para criar a nova tabela de usuários com a coluna COLUMN_USERNAME
        private const val CREATE_TABLE_USERS = "CREATE TABLE $TABLE_USERS (" +
                "$COLUMN_ID INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "$COLUMN_USERNAME TEXT, " +
                "$COLUMN_PASSWORD TEXT)"
    }
}
