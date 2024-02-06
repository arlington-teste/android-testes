package com.example.ghas_poc

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity
import android.widget.Toast

class MainActivity : AppCompatActivity() {

    private lateinit var editTextUsername: EditText
    private lateinit var editTextPassword: EditText
    private lateinit var buttonLogin: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        editTextUsername = findViewById(R.id.editTextUsername)
        editTextPassword = findViewById(R.id.editTextPassword)
        buttonLogin = findViewById(R.id.buttonLogin)

        buttonLogin.setOnClickListener {
            val username = editTextUsername.text.toString()
            val password = editTextPassword.text.toString()

            if (isValidCredentials(username, password)) {
                //Credentials are valid, navigate to MenuVulnerabilitiesActivity

                val intent_MENU = Intent(this, MenuVulnerabilitiesActivity::class.java)
                intent_MENU.putExtra("USERNAME", username)
                intent_MENU.putExtra("PASSWORD", password)
                startActivity(intent_MENU)

                //Credentials are valid, navigate to CommandExecutionActivity
                //val intent_CMDI = Intent(this, CommandExecutionActivity::class.java)
                //intent_CMDI.putExtra("USERNAME", username)
                //intent_CMDI.putExtra("PASSWORD", password)
                //startActivity(intent_CMDI)


                // Remova a mensagem Toast "Login successful"
            } else {
                // Credentials are invalid, show an error message
                Toast.makeText(this, "Invalid credentials", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun isValidCredentials(username: String, password: String): Boolean {
        // Add your authentication logic here
        val validUsername = "user_test"
        val validPassword = "password_test"

        return username == validUsername && password == validPassword
    }
}
