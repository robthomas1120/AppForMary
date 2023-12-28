package com.example.formary

import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide

class movieActivity : AppCompatActivity() {

    private lateinit var movieDbHandler: MovieDatabaseHandler

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.moviepage)

        // Creating an instance of the MovieDatabaseHandler
        movieDbHandler = MovieDatabaseHandler(this)

        // Adding sample movies
        movieDbHandler.addMovie("Movie 1", "A great movie with an exciting plot.", "banner1")
        movieDbHandler.addMovie("Movie 2", "An emotional journey that will touch your heart.", "banner2")
        movieDbHandler.addMovie("Movie 3", "A thrilling adventure for the whole family.", "banner3")
        movieDbHandler.addMovie("Movie 4", "A mind-bending experience that will leave you thinking.", "banner4")
        movieDbHandler.addMovie("Movie 5", "A classic film that stands the test of time.", "banner5")

        // Retrieving a random movie entry
        val randomMovie = movieDbHandler.getRandomMovie()
        randomMovie?.let {
            Log.d("Random Movie", "ID: ${it.id}, Title: ${it.title}, Description: ${it.description}, Banner Path: ${it.bannerPath}")
        }

        // Assuming you have an ImageView in your layout with ID R.id.movieBanner
        val movieBannerImageView = findViewById<ImageView>(R.id.movieBanner)

        // Load the image using Glide with the resource ID
        Glide.with(this)
            .load(resources.getIdentifier(randomMovie?.bannerPath, "drawable", packageName))
            .into(movieBannerImageView)

        val generateMovieButton = findViewById<Button>(R.id.randomMovieButton)

        generateMovieButton.setOnClickListener {

            // Calling getRandomMovie on the instance of MovieDatabaseHandler
            val randomMovie = movieDbHandler.getRandomMovie()

            // Check if a random movie is retrieved and log its details
            randomMovie?.let {
                Log.d("Random Movie", "ID: ${it.id}, Title: ${it.title}, Description: ${it.description}, Banner Path: ${it.bannerPath}")
            }

            // Load the new movie banner into the ImageView using Glide
            Glide.with(this)
                .load(resources.getIdentifier(randomMovie?.bannerPath, "drawable", packageName))
                .into(movieBannerImageView)
        }
    }
}