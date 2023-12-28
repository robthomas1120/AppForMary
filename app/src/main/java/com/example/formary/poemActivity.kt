package com.example.formary

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.TextView

class poemActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.poempage)

        val homeButton = findViewById<Button>(R.id.homeButtonPoem)

        homeButton.setOnClickListener{
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
        }

        val poemDbHandler = DatabaseHandler(this)

        // Adding sample poems
        poemDbHandler.addPoem("Poem 1", "This is the first poem.")
        poemDbHandler.addPoem("Poem 2", "Here goes the second poem.")
        poemDbHandler.addPoem("Poem 3", "The third poem is quite interesting.")
        poemDbHandler.addPoem("Poem 4", "A poem for the fourth entry.")
        poemDbHandler.addPoem("Poem 5", "Fifth and final poem for now.")

        // Retrieving a random poem
        val randomPoem = poemDbHandler.getRandomPoem()
        randomPoem?.let {
            Log.d("Random Poem", "ID: ${it.id}, Title: ${it.title}, Poem: ${it.poem}")
        }

        val displayTitle = findViewById<TextView>(R.id.poemIDTitle)
        val displayPoem = findViewById<TextView>(R.id.poemID)

        // Display poem title and content
        randomPoem?.let {
            displayTitle.text = it.title
            displayTitle.gravity = android.view.Gravity.CENTER
            displayPoem.text = it.poem
        }

        val generatePoemButton = findViewById<Button>(R.id.randomPoemButton)

        generatePoemButton.setOnClickListener{
            val newRandomPoem = poemDbHandler.getRandomPoem()

            // Check if a new random poem is retrieved and log its details
            newRandomPoem?.let {
                Log.d("Random Poem", "ID: ${it.id}, Title: ${it.title}, Poem: ${it.poem}")
            }

            // Display new random poem title and content
            newRandomPoem?.let {
                displayTitle.text = it.title
                displayPoem.text = it.poem
            }
        }
    }
}
