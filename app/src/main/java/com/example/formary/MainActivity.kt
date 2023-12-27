package com.example.formary

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.homepage)

        val poem = findViewById<Button>(R.id.poemButton)
        val sad = findViewById<Button>(R.id.sadButton)
        val lonely = findViewById<Button>(R.id.lonelyButton)
        val movie = findViewById<Button>(R.id.movieButton)
        val series = findViewById<Button>(R.id.seriesButton)
        val songs = findViewById<Button>(R.id.songButton)

        poem.setOnClickListener {
            val intent = Intent(this, poemActivity::class.java)
            startActivity(intent)
        }

        sad.setOnClickListener {
            val intent = Intent(this, sadActivity::class.java)
            startActivity(intent)
        }

        lonely.setOnClickListener {
            val intent = Intent(this, lonelyActivity::class.java)
            startActivity(intent)
        }

        movie.setOnClickListener {
            val intent = Intent(this, movieActivity::class.java)
            startActivity(intent)
        }

        series.setOnClickListener {
            val intent = Intent(this, seriesActivity::class.java)
            startActivity(intent)
        }

        songs.setOnClickListener {
            val intent = Intent(this, songsActivity::class.java)
            startActivity(intent)
        }

    }
}