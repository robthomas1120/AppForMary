package com.example.formary

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

class movieActivity : AppCompatActivity() {

    private lateinit var movieDbHandler: MovieDatabaseHandler

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.moviepage)

        val homeButton = findViewById<ImageButton>(R.id.homeButtonMovieImageButton)
        val shuffleButton = findViewById<ImageButton>(R.id.randomMovieImageButton)

        homeButton.setOnClickListener {
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
        }

        shuffleButton.setOnClickListener {
            // Calling getRandomMovie on the instance of MovieDatabaseHandler
            val newRandomMovie = movieDbHandler.getRandomMovie()

            // Check if a random movie is retrieved and log its details
            newRandomMovie?.let {
                Log.d("Random Movie", "ID: ${it.id}, Title: ${it.title}, Description: ${it.description}, Banner Path: ${it.bannerPath}")
            }

            // Update TextViews with new movie information
            val movieTitleTextView = findViewById<TextView>(R.id.movieTitle)
            val movieDescriptionTextView = findViewById<TextView>(R.id.movieDescription)
            val movieBannerImageView = findViewById<ImageView>(R.id.movieBanner)

            movieTitleTextView.text = newRandomMovie?.title
            movieDescriptionTextView.text = newRandomMovie?.description

            // Set the new image resource using setImageResource
            val newResourceId =
                resources.getIdentifier(newRandomMovie?.bannerPath, "drawable", packageName)
            if (newResourceId != 0) {
                movieBannerImageView.setImageResource(newResourceId)
            }
        }

        // Creating an instance of the MovieDatabaseHandler
        movieDbHandler = MovieDatabaseHandler(this)

        // Adding sample movies
        movieDbHandler.addMovie("Movie 1", "A great movie with an exciting plot.", "drawable/movie1")
        movieDbHandler.addMovie("Movie 2", "An emotional journey that will touch your heart.", "drawable/movie2")
        movieDbHandler.addMovie("Movie 3", "A thrilling adventure for the whole family.", "drawable/movie3")
        movieDbHandler.addMovie("Movie 4", "A mind-bending experience that will leave you thinking.", "drawable/movie4")
        movieDbHandler.addMovie("Movie 5", "A classic film that stands the test of time.", "drawable/movie5")

        // Retrieving a random movie entry
        val randomMovie = movieDbHandler.getRandomMovie()
        randomMovie?.let {
            Log.d("Random Movie", "ID: ${it.id}, Title: ${it.title}, Description: ${it.description}, Banner Path: ${it.bannerPath}")
        }

        // Assuming you have TextViews and an ImageView in your layout with appropriate IDs
        val movieTitleTextView = findViewById<TextView>(R.id.movieTitle)
        val movieDescriptionTextView = findViewById<TextView>(R.id.movieDescription)
        val movieBannerImageView = findViewById<ImageView>(R.id.movieBanner)
        val generateMovieButton = findViewById<Button>(R.id.randomMovieButton)

        // Update TextViews with movie information
        movieTitleTextView.text = randomMovie?.title
        movieDescriptionTextView.text = randomMovie?.description

        // Set the image resource using setImageResource
        val resourceId = resources.getIdentifier(randomMovie?.bannerPath, "drawable", packageName)
        if (resourceId != 0) {
            movieBannerImageView.setImageResource(resourceId)
        }

        generateMovieButton.setOnClickListener {
            // Calling getRandomMovie on the instance of MovieDatabaseHandler
            val newRandomMovie = movieDbHandler.getRandomMovie()

            // Check if a random movie is retrieved and log its details
            newRandomMovie?.let {
                Log.d("Random Movie", "ID: ${it.id}, Title: ${it.title}, Description: ${it.description}, Banner Path: ${it.bannerPath}")
            }

            // Update TextViews with new movie information
            movieTitleTextView.text = newRandomMovie?.title
            movieDescriptionTextView.text = newRandomMovie?.description

            // Set the new image resource using setImageResource
            val newResourceId =
                resources.getIdentifier(newRandomMovie?.bannerPath, "drawable", packageName)
            if (newResourceId != 0) {
                movieBannerImageView.setImageResource(newResourceId)
            }
        }
    }
}
