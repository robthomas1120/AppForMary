package com.example.formary

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.TextView

class lonelyActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.lonelypage)

        val homeButton = findViewById<Button>(R.id.homeButtonLonely)

        homeButton.setOnClickListener{

            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)

        }

        // Creating an instance of the LonelyDatabaseHandler
        val lonelyDbHandler = LonelyDatabaseHandler(this)

        // Adding sample lonely entries
        lonelyDbHandler.addLonely("Lonely 1", "Feeling lonely today...")
        lonelyDbHandler.addLonely("Lonely 2", "Another day, another lonely moment.")
        lonelyDbHandler.addLonely("Lonely 3", "Loneliness is my only companion.")
        lonelyDbHandler.addLonely("Lonely 4", "In solitude, I find solace.")
        lonelyDbHandler.addLonely("Lonely 5", "Isolation echoes in my heart.")

        // Retrieving a random lonely entry
        val randomLonely = lonelyDbHandler.getRandomLonely()
        randomLonely?.let {
            Log.d("Random Lonely", "ID: ${it.id}, Title: ${it.title}, Lonely: ${it.lonely}")
        }

        val lonely = randomLonely
        val lonelyString = lonely.toString()
        val displayLonely = findViewById<TextView>(R.id.lonelyID)
        displayLonely.text = lonelyString

        val generateLonelyButton = findViewById<Button>(R.id.randomLonelyButton)

        generateLonelyButton.setOnClickListener {

            // Calling getRandomLonely on the instance of LonelyDatabaseHandler
            val randomLonely = lonelyDbHandler.getRandomLonely()

            // Check if a random lonely is retrieved and log its details
            randomLonely?.let {
                Log.d("Random Lonely", "ID: ${it.id}, Title: ${it.title}, Lonely: ${it.lonely}")
            }

            val randomLonelyGetString = randomLonely
            val usableRandomLonely = randomLonelyGetString.toString()
            displayLonely.text = usableRandomLonely
        }

    }
}