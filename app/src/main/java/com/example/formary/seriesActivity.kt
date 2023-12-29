package com.example.formary

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

class seriesActivity : AppCompatActivity() {

    private lateinit var seriesDbHandler: TVSeriesDatabaseHandler

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.seriespage)

        val homeButton = findViewById<ImageButton>(R.id.homeButtonSeriesImageButton)
        val shuffleButton = findViewById<ImageButton>(R.id.randomSeriesImageButton)

        homeButton.setOnClickListener {

            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)

        }

        shuffleButton.setOnClickListener {
            // Calling getRandomSeries on the instance of TVSeriesDatabaseHandler
            val newRandomSeries = seriesDbHandler.getRandomSeries()

            // Check if a random TV series is retrieved and log its details
            newRandomSeries?.let {
                Log.d(
                    "Random TV Series",
                    "ID: ${it.id}, Title: ${it.title}, Description: ${it.description}, Banner Path: ${it.bannerPath}"
                )
            }

            // Update TextViews with new TV series information
            val seriesTitleTextView = findViewById<TextView>(R.id.tvSeriesTitle)
            val seriesDescriptionTextView = findViewById<TextView>(R.id.tvSeriesDescription)
            val seriesBannerImageView = findViewById<ImageView>(R.id.tvSeriesBanner)

            seriesTitleTextView.text = newRandomSeries?.title
            seriesDescriptionTextView.text = newRandomSeries?.description

            // Set the new image resource using setImageResource
            val newResourceId =
                resources.getIdentifier(newRandomSeries?.bannerPath, "drawable", packageName)
            if (newResourceId != 0) {
                seriesBannerImageView.setImageResource(newResourceId)
            }
        }

        // Creating an instance of the TVSeriesDatabaseHandler
        seriesDbHandler = TVSeriesDatabaseHandler(this)

        // Adding sample TV series
        seriesDbHandler.addSeries("Series 1", "An amazing TV series with a captivating story.", "series1")
        seriesDbHandler.addSeries("Series 2", "A thrilling journey into the unknown.", "series2")
        seriesDbHandler.addSeries("Series 3", "A comedy series that will make you laugh out loud.", "series3")
        seriesDbHandler.addSeries("Series 4", "A suspenseful drama that will keep you on the edge of your seat.", "series4")
        seriesDbHandler.addSeries("Series 5", "An epic fantasy series with magic and adventure.", "series5")

        // Retrieving a random TV series entry
        val randomSeries = seriesDbHandler.getRandomSeries()
        randomSeries?.let {
            Log.d(
                "Random TV Series",
                "ID: ${it.id}, Title: ${it.title}, Description: ${it.description}, Banner Path: ${it.bannerPath}"
            )
        }

        // Assuming you have TextViews and an ImageView in your layout with appropriate IDs
        val seriesTitleTextView = findViewById<TextView>(R.id.tvSeriesTitle)
        val seriesDescriptionTextView = findViewById<TextView>(R.id.tvSeriesDescription)
        val seriesBannerImageView = findViewById<ImageView>(R.id.tvSeriesBanner)

        // Update TextViews with TV series information
        seriesTitleTextView.text = randomSeries?.title
        seriesDescriptionTextView.text = randomSeries?.description

        // Set the image resource using setImageResource
        val resourceId = resources.getIdentifier(randomSeries?.bannerPath, "drawable", packageName)
        if (resourceId != 0) {
            seriesBannerImageView.setImageResource(resourceId)
        }
    }
}
