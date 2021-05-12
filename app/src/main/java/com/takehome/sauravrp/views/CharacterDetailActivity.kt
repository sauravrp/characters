package com.takehome.sauravrp.views

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.viewModels
import com.squareup.picasso.Picasso
import com.takehome.sauravrp.DirectoryComponentProvider
import com.takehome.sauravrp.databinding.ActivityCharacterDetailBinding
import com.takehome.sauravrp.di.components.DaggerActivityComponent
import com.takehome.sauravrp.viewmodels.CharacterDetailViewModel
import com.takehome.sauravrp.viewmodels.CharacterDetailViewModelFactory
import com.takehome.sauravrp.viewmodels.ShowCharacter
import timber.log.Timber
import javax.inject.Inject

class CharacterDetailActivity : AppCompatActivity() {

    @Inject
    lateinit var characterDetailViewModelFactory: CharacterDetailViewModelFactory

    private lateinit var binding: ActivityCharacterDetailBinding

    companion object {
        const val ID = "ID"
        fun startActivity(context: Context, id: String) {
            val intent = Intent(context, CharacterDetailActivity::class.java).apply {
                putExtra(ID, id)
            }
            context.startActivity(intent)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
         binding = ActivityCharacterDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        DaggerActivityComponent
            .builder()
            .directoryComponent((applicationContext as DirectoryComponentProvider).directoryComponent())
            .build()
            .inject(this)

        val viewModel : CharacterDetailViewModel by viewModels { characterDetailViewModelFactory }

        viewModel.viewState.observe(this, {
            when(it) {
                is CharacterDetailViewModel.ViewState.Error -> Timber.e(it.error)
                is CharacterDetailViewModel.ViewState.Success -> showDetail(it.character)
            }
        })

        val id = intent.getStringExtra(ID).orEmpty()
        viewModel.getDetail(id)
    }

    private fun showDetail(detail: ShowCharacter) {
        Picasso.get().load(detail.image).into(binding.imageView)
    }
}