package com.takehome.sauravrp.views

import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.takehome.sauravrp.DirectoryComponentProvider
import com.takehome.sauravrp.R
import com.takehome.sauravrp.databinding.CharactersViewBinding
import com.takehome.sauravrp.di.components.DaggerActivityComponent
import com.takehome.sauravrp.viewmodels.ShowCharacter
import com.takehome.sauravrp.viewmodels.CharacterViewModel
import com.takehome.sauravrp.viewmodels.CharactersViewModelFactory
import com.takehome.sauravrp.viewmodels.Characters
import com.takehome.sauravrp.views.adapter.CharactersAdapter
import timber.log.Timber
import javax.inject.Inject

class CharactersActivity : AppCompatActivity(),
        CharactersAdapter.CharacterSelectionListener {

    @Inject
    lateinit var charactersViewModelFactory: CharactersViewModelFactory

    private lateinit var binding: CharactersViewBinding

    private lateinit var model: CharacterViewModel

    private val listAdapter = CharactersAdapter(this)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = CharactersViewBinding.inflate(layoutInflater)
        setContentView(binding.root)

        DaggerActivityComponent
                .builder()
                .directoryComponent((applicationContext as DirectoryComponentProvider).directoryComponent())
                .build()
                .inject(this)

        model = ViewModelProvider(this, charactersViewModelFactory).get(CharacterViewModel::class.java)

        binding.listView.apply {
            adapter = listAdapter
        }

        binding.retryButton.setOnClickListener {
            model.fetchCharacters()
        }

        model.viewState.observe(this, Observer {
            when (it) {
                is CharacterViewModel.ViewState.Error -> showError(it.error)
                CharacterViewModel.ViewState.Loading -> showLoading()
                is CharacterViewModel.ViewState.Success -> {
                    if (it.data.characters.isEmpty()) {
                        showEmpty()
                    } else {
                        showSuccess(it.data)
                    }
                }
            }
        })

    }

    private fun showEmpty() {
        Timber.d("Showing empty state")
        binding.apply {
            listView.isVisible = false
            progress.isVisible = false

            noResultsContainer.isVisible = true
            noResultsMessage.text = getString(R.string.empty_message)
        }
    }

    private fun showError(error: Throwable) {
        Timber.d("Showing error state")
        binding.apply {
            listView.isVisible = false
            progress.isVisible = false

            noResultsContainer.isVisible = true
            noResultsMessage.text = getString(R.string.error_message)

            Timber.e(error)
        }
    }

    private fun showLoading() {
        Timber.d("Showing loading state")
        binding.apply {
            listView.isVisible = false
            progress.isVisible = true
            noResultsContainer.isVisible = false
        }
    }

    private fun showSuccess(data: Characters) {
        Timber.d("Showing characters")
        binding.apply {
            listView.isVisible = true
            progress.isVisible = false
            noResultsContainer.isVisible = false
        }
        listAdapter.submitList(data.characters)
    }

    override fun cardItemSelected(employee: ShowCharacter) {
        CharacterDetailActivity.startActivity(this, employee.id)
    }
}