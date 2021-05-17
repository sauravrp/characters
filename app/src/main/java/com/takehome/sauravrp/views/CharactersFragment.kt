package com.takehome.sauravrp.views

import android.os.Bundle
import android.os.Parcelable
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.takehome.sauravrp.DirectoryComponentProvider
import com.takehome.sauravrp.R
import com.takehome.sauravrp.databinding.CharacterFragmentBinding
import com.takehome.sauravrp.databinding.CharactersViewBinding
import com.takehome.sauravrp.di.components.DaggerActivityComponent
import com.takehome.sauravrp.di.components.DaggerFragmentComponent
import com.takehome.sauravrp.viewmodels.CharacterViewModel
import com.takehome.sauravrp.viewmodels.Characters
import com.takehome.sauravrp.viewmodels.CharactersViewModelFactory
import com.takehome.sauravrp.viewmodels.ShowCharacter
import com.takehome.sauravrp.views.adapter.CharactersAdapter
import timber.log.Timber
import javax.inject.Inject

class CharactersFragment : Fragment(),
    CharactersAdapter.CharacterSelectionListener {

    var state: Parcelable? = null

    @Inject
    lateinit var charactersViewModelFactory: CharactersViewModelFactory

    private var binding: CharacterFragmentBinding? = null

    private lateinit var model: CharacterViewModel

    private val listAdapter = CharactersAdapter(this)

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val _binding = CharacterFragmentBinding.inflate(inflater, container, false)
        binding = _binding
        return _binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        DaggerFragmentComponent
            .builder()
            .directoryComponent((requireActivity().applicationContext as DirectoryComponentProvider).directoryComponent())
            .build()
            .inject(this)

        model = ViewModelProvider(requireActivity(), charactersViewModelFactory).get(CharacterViewModel::class.java)

        binding?.listView?.apply {
            adapter = listAdapter
        }

        binding?.retryButton?.setOnClickListener {
            model.fetchCharacters()
        }

        model.viewState.observe(requireActivity(), Observer {
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


    override fun onDestroyView() {
        super.onDestroyView()

        binding = null
    }

    override fun onStart() {
        super.onStart()
    }

    override fun onPause() {
        super.onPause()
    //    state = binding?.listView?.layoutManager?.onSaveInstanceState()
    }

    override fun onResume() {
        super.onResume()
      //  binding?.listView?.layoutManager?.onRestoreInstanceState(state)
    }

    override fun onStop() {
        super.onStop()
    }

    override fun onDestroy() {
        super.onDestroy()
    }


    private fun showEmpty() {
        Timber.d("Showing empty state")
        binding?.apply {
            listView.isVisible = false
            progress.isVisible = false

            noResultsContainer.isVisible = true
            noResultsMessage.text = getString(R.string.empty_message)
        }
    }

    private fun showError(error: Throwable) {
        Timber.d("Showing error state")
        binding?.apply {
            listView.isVisible = false
            progress.isVisible = false

            noResultsContainer.isVisible = true
            noResultsMessage.text = getString(R.string.error_message)

            Timber.e(error)
        }
    }

    private fun showLoading() {
        Timber.d("Showing loading state")
        binding?.apply {
            listView.isVisible = false
            progress.isVisible = true
            noResultsContainer.isVisible = false
        }
    }

    private fun showSuccess(data: Characters) {
        Timber.d("Showing characters")
        binding?.apply {
            listView.isVisible = true
            progress.isVisible = false
            noResultsContainer.isVisible = false
        }
        listAdapter.submitList(data.characters)
    }

    override fun cardItemSelected(character: ShowCharacter) {
       // CharacterDetailActivity.startActivity(this, employee.id)
        if(lifecycle.currentState.isAtLeast(Lifecycle.State.STARTED)) {
            (requireActivity() as? CharactersActivity)?.navigateToDetail()
            model.setSelection(character)
        }
    }
}