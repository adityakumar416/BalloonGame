package com.example.balloonassignment

import android.media.MediaPlayer
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.core.content.ContextCompat
import java.util.*

class MainActivity : AppCompatActivity() {
    private lateinit var balloon1ImageView: ImageView
    private lateinit var balloon2ImageView: ImageView
    private lateinit var colorTextView: TextView
    private lateinit var mediaPlayer: MediaPlayer
    private val random = Random()
    private var wrongBalloonClicked = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        balloon1ImageView = findViewById(R.id.balloon1ImageView)
        balloon2ImageView = findViewById(R.id.balloon2ImageView)
        colorTextView = findViewById(R.id.colorTextView)
        mediaPlayer = MediaPlayer.create(this, R.raw.balloon_burst_sound)

        balloon1ImageView.setOnClickListener {
            handleBalloonClick(balloon1ImageView)
        }

        balloon2ImageView.setOnClickListener {
            handleBalloonClick(balloon2ImageView)
        }

        showRandomBalloons()
    }

    private fun showRandomBalloons() {
        val randomColor1 = random.nextInt(2)
        val randomColor2 = random.nextInt(2)

        if (randomColor1 == 0) {
            balloon1ImageView.setImageResource(R.drawable.balloon_blue)
        } else {
            balloon1ImageView.setImageResource(R.drawable.balloon_red)
        }

        if (randomColor2 == 0) {
            balloon2ImageView.setImageResource(R.drawable.balloon_blue)
        } else {
            balloon2ImageView.setImageResource(R.drawable.balloon_red)
        }

        val animation = AnimationUtils.loadAnimation(this, R.anim.balloon_animation)
        balloon1ImageView.startAnimation(animation)
        balloon2ImageView.startAnimation(animation)

        // Reset the wrongBalloonClicked flag
        wrongBalloonClicked = false

        // Set the color text based on the first balloon color
        if (randomColor1 == 0) {
            colorTextView.text = "Blue"
        } else {
            colorTextView.text = "Red"
        }
    }

    private fun handleBalloonClick(balloonImageView: ImageView) {
        if (wrongBalloonClicked) {
            // If the wrong balloon was previously clicked, don't handle further clicks until reset
            showRandomBalloons()
            Toast.makeText(this, "Opps.. you have clicked the wrong balloon.", Toast.LENGTH_SHORT).show()

            return
        }

        val currentDrawable = balloonImageView.drawable
        val balloonColor = colorTextView.text.toString()

        if (balloonColor.equals("Blue", ignoreCase = true) &&
            currentDrawable.constantState == ContextCompat.getDrawable(this, R.drawable.balloon_blue)?.constantState) {
            playBurstSound()
            showRandomBalloons()
            Toast.makeText(this, "Congratulation.. you have clicked the right balloon.", Toast.LENGTH_SHORT).show()

        } else if (balloonColor.equals("Red", ignoreCase = true) &&
            currentDrawable.constantState == ContextCompat.getDrawable(this, R.drawable.balloon_red)?.constantState) {
            playBurstSound()
            showRandomBalloons()
            Toast.makeText(this, "Congratulation.. you have clicked the right balloon.", Toast.LENGTH_SHORT).show()

        } else {
            // Wrong balloon clicked, set the wrongBalloonClicked flag
            wrongBalloonClicked = true
        }
    }

    private fun playBurstSound() {
        if (!mediaPlayer.isPlaying) {
            mediaPlayer.start()
        }
    }
}