package com.example.ghas_poc

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import java.io.BufferedReader
import java.io.IOException
import java.io.InputStreamReader
import android.util.Log
import android.widget.Button
import android.widget.EditText

class CommandExecutionActivity : AppCompatActivity() {

    private lateinit var commandInput: EditText
    private lateinit var executeButtonCMDI: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_command_execution)

        commandInput = findViewById(R.id.commandInput)
        executeButtonCMDI = findViewById(R.id.executeButtonCMDI)

        val username = intent.getStringExtra("USERNAME")
        val password = intent.getStringExtra("PASSWORD")

        if (isValidCredentials(username, password)) {
            // Credentials are valid, execute command
            executeButtonCMDI.setOnClickListener {
                val command = getCommadInput()
                executeCommandInEmulator(command)
            }
        } else {
            // Credentials are invalid, show an error message
            Toast.makeText(this, "Invalid credentials", Toast.LENGTH_SHORT).show()
        }
    }

    private fun getCommadInput(): String {
        return commandInput.text.toString()
    }

    private fun executeCommandInEmulator(): String {
        return commandInput.text.toString()
    }

    private fun isValidCredentials(username: String?, password: String?): Boolean {
        return username == "user_test" && password == "password_test"
    }

    private fun runCommand(command: String): String {
        return try {
            val process = Runtime.getRuntime().exec(command)
            val inputStream = process.inputStream
            val bufferedReader = BufferedReader(InputStreamReader(inputStream))
            val result = StringBuilder()

            var line: String? = bufferedReader.readLine()
            while (line != null) {
                result.append(line).append("\n")
                line = bufferedReader.readLine()
            }

            bufferedReader.close()
            process.waitFor()
            result.toString()
        } catch (e: IOException) {
            e.printStackTrace()
            Log.e("MeuApp", "Erro de I/O ao executar o comando: $command", e)
            "Erro ao executar o comando: $command"
        } catch (e: InterruptedException) {
            e.printStackTrace()
            Log.e("MeuApp", "Erro de interrupção ao executar o comando: $command", e)
            "Erro ao executar o comando: $command"
        }
    }

    private fun executeCommandInEmulator(command: String) {
        val result = runCommand(command) // Substitua pelo seu comando desejado
        Toast.makeText(this, result, Toast.LENGTH_LONG).show()
    }
}