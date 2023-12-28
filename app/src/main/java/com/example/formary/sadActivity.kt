package com.example.formary

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

class sadActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.sadpage)

        val homeButton = findViewById<Button>(R.id.homeButtonSad)

        homeButton.setOnClickListener {
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
        }

        // Creating an instance of the SadDatabaseHandler
        val sadDbHandler = SadDatabaseHandler(this)

        // Adding sample sad entries
        sadDbHandler.addSad("Sad 1", "ano kaya feeling maging sad")
        sadDbHandler.addSad("Sad 2", "sanaol hindi sad")
        sadDbHandler.addSad("Sad 3", "Sadness ang favorite emotion ko sa inside out")
        sadDbHandler.addSad("Sad 4", "huhu ako ay malongcoat")
        sadDbHandler.addSad("Sad 5", "ang longcoat longcoat")

        // Retrieving a random sad entry
        val randomSad = sadDbHandler.getRandomSad()
        randomSad?.let {
            Log.d("Random Sad", "ID: ${it.id}, Title: ${it.title}, Sad: ${it.sad}")
        }

        val sadTitle = findViewById<TextView>(R.id.sadTitle)
        val sadID = findViewById<TextView>(R.id.sadID)

        randomSad?.let {
            sadTitle.text = it.title
            sadID.text = it.sad
        }

        val generateSadButton = findViewById<Button>(R.id.randomSadButton)

        generateSadButton.setOnClickListener {

            // Calling getRandomSad on the instance of SadDatabaseHandler
            val randomSad = sadDbHandler.getRandomSad()

            // Check if a random sad is retrieved and log its details
            randomSad?.let {
                Log.d("Random Sad", "ID: ${it.id}, Title: ${it.title}, Sad: ${it.sad}")
            }

            randomSad?.let {
                sadTitle.text = it.title
                sadID.text = it.sad
            }
        }
    }
}
