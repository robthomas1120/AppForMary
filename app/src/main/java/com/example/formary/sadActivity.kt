package com.example.formary

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.TextView

class sadActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.sadpage)

        val homeButton = findViewById<Button>(R.id.homeButtonSad)

        homeButton.setOnClickListener{

            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)

        }

        // Creating an instance of the LonelyDatabaseHandler
        val sadDbHandler = SadDatabaseHandler(this)

        // Adding sample lonely entries
        sadDbHandler.addSad("Sad 1", "ano kaya feeling maging sad")
        sadDbHandler.addSad("Sad 2", "sanaol hindi sad")
        sadDbHandler.addSad("Sad 3", "Sadness ang favorite emotion ko sa inside out")
        sadDbHandler.addSad("Sad 4", "huhu ako ay malongcoat")
        sadDbHandler.addSad("Sad 5", "ang longcoat longcoat")

        // Retrieving a random lonely entry
        val randomSad = sadDbHandler.getRandomSad()
        randomSad?.let {
            Log.d("Random Lonely", "ID: ${it.id}, Title: ${it.title}, Lonely: ${it.sad}")
        }

        val sad = randomSad
        val sadString = sad.toString()
        val displaySad = findViewById<TextView>(R.id.sadID)
        displaySad.text = sadString

        val generateSadButton = findViewById<Button>(R.id.randomSadButton)

        generateSadButton.setOnClickListener {

            // Calling getRandomLonely on the instance of LonelyDatabaseHandler
            val randomSad = sadDbHandler.getRandomSad()

            // Check if a random lonely is retrieved and log its details
            randomSad?.let {
                Log.d("Random Lonely", "ID: ${it.id}, Title: ${it.title}, Lonely: ${it.sad}")
            }

            val randomSadGetString = randomSad
            val usableRandomSad = randomSadGetString.toString()
            displaySad.text = usableRandomSad
        }

    }
}