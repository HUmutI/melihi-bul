package com.example.melihibul

import android.animation.Animator
import android.animation.AnimatorInflater
import android.animation.AnimatorListenerAdapter
import android.animation.AnimatorSet
import android.annotation.SuppressLint
import android.os.Bundle
import android.widget.ImageView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.melihibul.databinding.ActivityMainBinding
import com.google.android.material.snackbar.Snackbar
import kotlin.random.Random


class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private var melih = 0
    private var attempCount= 1
    private lateinit var imageViews: List<ImageView>

    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)
        enableEdgeToEdge()
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        imageViews = listOf(
            binding.image1, binding.image2, binding.image3,
            binding.image4, binding.image5, binding.image6,
            binding.image7, binding.image8, binding.image9
        )

        //melih
        melih = Random.nextInt(0, 9)

        for (i in imageViews.indices) {
            imageViews[i].setOnClickListener { flipCard(i) }
        }

        //tekrar oyna
        binding.bottomButton.setOnClickListener {
            playAgain()
        }

    }

    @SuppressLint("ResourceType")
    private fun flipCard(index: Int) {
        val imageView = imageViews[index]

        // /res/anim içindeki animasyon dosyalarını al
        val flipOutAnim = AnimatorInflater.loadAnimator(this, R.anim.card_flip_out) as AnimatorSet
        val flipInAnim = AnimatorInflater.loadAnimator(this, R.anim.card_flip_in) as AnimatorSet
            flipOutAnim.setTarget(imageView)
            flipOutAnim.start()

            flipOutAnim.addListener(object : AnimatorListenerAdapter() {
                override fun onAnimationEnd(animation: Animator) {
                    if (index == melih) {
                        imageView.setImageResource(R.drawable.recep_turuncu_gomlek_nah)
                        Snackbar.make(imageView,"MELİHİ $attempCount DENEMEDE BULDUN!", Snackbar.LENGTH_SHORT).show()

                    } else {
                        imageView.setImageResource(R.drawable.recep_mavi_gomlek_nah)
                        Snackbar.make(imageView,"Tekrar Dene",Snackbar.LENGTH_SHORT).show()
                        attempCount += 1

                    }
                    flipInAnim.setTarget(imageView)
                    flipInAnim.start()

                }
            })

    }

    @SuppressLint("ResourceType")
    private fun playAgain() {
        melih = Random.nextInt(0, 9)
        attempCount =1

        for (imageView in imageViews) {
            val flipOutAnim = AnimatorInflater.loadAnimator(this, R.anim.card_flip_out) as AnimatorSet
            val flipInAnim = AnimatorInflater.loadAnimator(this, R.anim.card_flip_in) as AnimatorSet

            flipOutAnim.setTarget(imageView)
            flipInAnim.setTarget(imageView)
            flipOutAnim.addListener(object : AnimatorListenerAdapter() {
                override fun onAnimationEnd(animation: Animator) {
                    imageView.setImageResource(R.drawable.soru_isareti)
                    flipInAnim.start()
                }
            })
            flipOutAnim.start()
        }
        Snackbar.make(binding.root, "Oyun yeniden başladı!", Snackbar.LENGTH_SHORT).show()
    }


}