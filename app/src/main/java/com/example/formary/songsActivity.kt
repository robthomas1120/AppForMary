package com.example.formary

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

class songsActivity : AppCompatActivity() {

    private lateinit var songDbHandler: SongDatabaseHandler

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.songspage)

        val homeButton = findViewById<ImageButton>(R.id.homeButtonSongImageButton)
        val shuffleButton = findViewById<ImageButton>(R.id.randomSongImageButton)

        homeButton.setOnClickListener {
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
        }

        // Creating an instance of the SongDatabaseHandler
        songDbHandler = SongDatabaseHandler(this)

        // Adding sample songs
        songDbHandler.addSong("Song 1", "Artist 1", "An amazing song by Artist 1.", "drawable/song1")
        songDbHandler.addSong("Song 2", "Artist 2", "A beautiful melody by Artist 2.", "drawable/song2")
        songDbHandler.addSong("Song 3", "Artist 3", "A soulful tune by Artist 3.", "drawable/song3")
        songDbHandler.addSong("Song 4", "Artist 4", "A catchy song by Artist 4.", "drawable/song4")
        songDbHandler.addSong("Song 5", "Artist 5", "An uplifting track by Artist 5.", "drawable/song5")

        // Retrieving a random song entry
        val randomSong = songDbHandler.getRandomSong()
        randomSong?.let {
            Log.d("Random Song", "ID: ${it.id}, Title: ${it.title}, Artist: ${it.artist}, Description: ${it.description}, Image Path: ${it.imagePath}")
        }

        // Assuming you have TextViews and an ImageView in your layout with appropriate IDs
        val songTitleTextView = findViewById<TextView>(R.id.songTitle)
        val artistNameTextView = findViewById<TextView>(R.id.artistName)
        val songDescriptionTextView = findViewById<TextView>(R.id.songDescription)
        val songImageView = findViewById<ImageView>(R.id.songImage)

        // Update TextViews with song information
        songTitleTextView.text = randomSong?.title
        artistNameTextView.text = randomSong?.artist
        songDescriptionTextView.text = randomSong?.description

        // Set the image resource using setImageResource
        val resourceId = resources.getIdentifier(randomSong?.imagePath, "drawable", packageName)
        if (resourceId != 0) {
            songImageView.setImageResource(resourceId)
        } else {
            // If the resource ID is not valid, you can set a placeholder image or handle the case accordingly
            // songImageView.setImageResource(R.drawable.default_image)
        }

        shuffleButton.setOnClickListener {
            // Calling getRandomSong on the instance of SongDatabaseHandler
            val newRandomSong = songDbHandler.getRandomSong()

            // Check if a random song is retrieved and log its details
            newRandomSong?.let {
                Log.d("Random Song", "ID: ${it.id}, Title: ${it.title}, Artist: ${it.artist}, Description: ${it.description}, Image Path: ${it.imagePath}")
            }

            // Update TextViews with new song information
            songTitleTextView.text = newRandomSong?.title
            artistNameTextView.text = newRandomSong?.artist
            songDescriptionTextView.text = newRandomSong?.description

            // Set the new image resource using setImageResource
            val newResourceId = resources.getIdentifier(newRandomSong?.imagePath, "drawable", packageName)
            if (newResourceId != 0) {
                songImageView.setImageResource(newResourceId)
            } else {
                // If the resource ID is not valid, you can set a placeholder image or handle the case accordingly
                // songImageView.setImageResource(R.drawable.default_image)
            }
        }
    }
}
