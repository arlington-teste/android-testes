package com.example.ghas_poc

import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import java.io.StringReader
import org.apache.xerces.parsers.DOMParser
import org.xml.sax.InputSource
import javax.xml.parsers.DocumentBuilderFactory


import android.os.AsyncTask

class XmlExternalEntityActivity : AppCompatActivity() {

    private lateinit var xmlInsertInputEditText: EditText
    private lateinit var xmlResultTextView: TextView
    private lateinit var xmlResponseContent: TextView
    private lateinit var executeButton: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_xml_external_entity)

        xmlInsertInputEditText = findViewById(R.id.xmlInsertInputEditText)
        xmlResultTextView = findViewById(R.id.xmlResultTextView)
        xmlResponseContent = findViewById(R.id.xmlResponseContent)
        executeButton = findViewById(R.id.executeButton)

        executeButton.setOnClickListener {
            val xmlContent = xmlInsertInputEditText.text.toString()
            ProcessXmlTask().execute(xmlContent)
        }
    }

    private inner class ProcessXmlTask : AsyncTask<String, Void, String>() {

        override fun doInBackground(vararg params: String): String {
            val xmlContent = params[0]
            return processXml(xmlContent)
        }

        override fun onPostExecute(result: String) {
            // Atualize a UI com o resultado aqui
            xmlResultTextView.text = "Resultado do XML:"
            xmlResponseContent.text = result
        }
    }

    private fun processXml(xmlContent: String): String {
        val parser = DOMParser()
        parser.parse(InputSource(xmlContent.reader()))

        val document = parser.document
        val lastNameNode = document.getElementsByTagName("lastName").item(0)
        val lastName = lastNameNode.textContent

        return lastName
    }
}



