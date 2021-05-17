package com.takehome.sauravrp.views

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.takehome.sauravrp.databinding.CharactersViewBinding

class CharactersActivity : AppCompatActivity() {

    lateinit var binding: CharactersViewBinding

    private var isDetail = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = CharactersViewBinding.inflate(layoutInflater)
        setContentView(binding.root)

        if (savedInstanceState == null) {
            navigateToSummary()
        }
    }

//    override fun onBackPressed() {
//        if (isDetail) {
//           supportFragmentManager.popBackStack()
//        } else {
//            super.onBackPressed()
//        }
//    }

    fun navigateToDetail() {
        isDetail = true

        var detail = supportFragmentManager.findFragmentByTag("TagA")

        if(detail == null) {
            detail = CharactersDetailsFragment()
        }

        supportFragmentManager.beginTransaction()
            .replace(binding.contentMain.id, detail, "TagA")
            .addToBackStack(null)
            .commit()
    }

    fun navigateToSummary() {
        isDetail = false

        var summary = supportFragmentManager.findFragmentByTag("TagB")

        if(summary == null) {
            summary = CharactersFragment()
        }


        supportFragmentManager.beginTransaction()
            .replace(binding.contentMain.id, summary,  "TagB").commit()
    }
}