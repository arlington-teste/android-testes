package com.example.ghas_poc


import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity


class MenuVulnerabilitiesActivity : AppCompatActivity() {

    private lateinit var CMDI_menu: Button
    private lateinit var XXE_menu: Button
    private lateinit var SQLi_menu: Button
    private lateinit var STORAGE_menu: Button
    private lateinit var LIBvuln_menu: Button
    private lateinit var UnprotectedActivity_menu: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_menu_vulnerabilities)

        CMDI_menu = findViewById(R.id.CMDI_menu)
        XXE_menu = findViewById(R.id.XXE_menu)
        SQLi_menu = findViewById(R.id.SQLi_menu)
        STORAGE_menu = findViewById(R.id.STORAGE_menu)
        LIBvuln_menu = findViewById(R.id.LIBvuln_menu)
        UnprotectedActivity_menu = findViewById(R.id.UnprotectedActivity_menu)


        val username = intent.getStringExtra("USERNAME")
        val password = intent.getStringExtra("PASSWORD")

        if (isValidCredentials(username, password)) {
            // Credentials is valid, select the vulnerability
            CMDI_menu.setOnClickListener {
                // Ação quando é clicado na opção de Command Injection
                val intent_CMDI = Intent(this, CommandExecutionActivity::class.java)
                intent_CMDI.putExtra("USERNAME", username)
                intent_CMDI.putExtra("PASSWORD", password)
                startActivity(intent_CMDI)
            }

            XXE_menu.setOnClickListener {
                // Ação quando é clicado na opção de XML External Entity
                val intent_XXE = Intent(this, XmlExternalEntityActivity::class.java)
                startActivity(intent_XXE)
            }

            STORAGE_menu.setOnClickListener {

            }



        } else {

        }


    }

    private fun isValidCredentials(username: String?, password: String?): Boolean {
        return username == "user_test" && password == "password_test"
    }






}