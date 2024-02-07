package com.example.ghas_poc

import android.content.ContentValues
import androidx.appcompat.app.AppCompatActivity
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast

// Classe que estende SQLiteOpenHelper para ajudar na criação e atualização do banco de dados
class MyDatabaseHelper(context: Context) : SQLiteOpenHelper(context, DATABASE_NAME, null, DATABASE_VERSION) {

    // Método chamado quando o banco de dados é criado pela primeira vez
    override fun onCreate(db: SQLiteDatabase) {
        // Executa uma operação SQL para criar a tabela de usuários no banco de dados
        db.execSQL(CREATE_TABLE_USERS)
    }

    // Método chamado quando é necessário atualizar o banco de dados para uma nova versão
    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
        // Executa uma operação SQL para excluir a tabela de usuários se ela existir
        db.execSQL("DROP TABLE IF EXISTS $TABLE_USERS")
        // Chama onCreate() novamente para criar a nova estrutura do banco de dados
        onCreate(db)
    }

    // Companheiro do objeto que contém constantes utilizadas para definir o banco de dados
    companion object {
        private const val DATABASE_NAME = "mydatabase.db" // Nome do banco de dados
        private const val DATABASE_VERSION = 1 // Versão do banco de dados

        const val TABLE_USERS = "passwords_table" // Nome da tabela de usuários
        private const val COLUMN_ID = "_id" // Nome da coluna de ID
        const val COLUMN_PASSWORD = "password" // Nome da coluna de senha de usuário

        // SQL para criar a tabela de usuários com colunas de ID, nome de usuário e senha
        private const val CREATE_TABLE_USERS = "CREATE TABLE $TABLE_USERS (" +
                "$COLUMN_ID INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "$COLUMN_PASSWORD TEXT)"
    }
}


class SqlInjectionActivity : AppCompatActivity() {

    private lateinit var dbHelper: MyDatabaseHelper
    private lateinit var passwordEditText: EditText
    private lateinit var saveButton: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_insecure_data_storage)

        dbHelper = MyDatabaseHelper(this)
        passwordEditText = findViewById(R.id.StorageInput)
        saveButton = findViewById(R.id.executeButtonStorage)

        saveButton.setOnClickListener {
            val password = passwordEditText.text.toString()

            // Executa o SQL Injection
            execSQLInjection(password)
        }
    }

    private fun execSQLInjection(password: String) {
        // Abre o banco de dados para escrita
        val db = dbHelper.writableDatabase

        // Cria um objeto ContentValues para armazenar os valores a serem inseridos
        val values = ContentValues()
        values.put(MyDatabaseHelper.COLUMN_PASSWORD, password)

        // Executa a inserção no banco de dados
        val newRowId = db.insert(MyDatabaseHelper.TABLE_USERS, null, values)

        // Verifica se a inserção foi bem sucedida
        if (newRowId != -1L) {
            Toast.makeText(this, "Senha salva com sucesso!", Toast.LENGTH_SHORT).show()
        } else {
            Toast.makeText(this, "Erro ao salvar a senha!", Toast.LENGTH_SHORT).show()
        }

        // Fecha o banco de dados
        db.close()
    }
}

