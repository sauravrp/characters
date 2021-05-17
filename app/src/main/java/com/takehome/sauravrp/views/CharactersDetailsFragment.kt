package com.takehome.sauravrp.views

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.squareup.picasso.Picasso
import com.takehome.sauravrp.DirectoryComponentProvider
import com.takehome.sauravrp.databinding.FragmentCharacterDetailBinding
import com.takehome.sauravrp.di.components.DaggerFragmentComponent
import com.takehome.sauravrp.viewmodels.CharacterViewModel
import com.takehome.sauravrp.viewmodels.CharactersViewModelFactory
import io.reactivex.rxjava3.disposables.CompositeDisposable
import javax.inject.Inject

class CharactersDetailsFragment : Fragment() {

    private var binding: FragmentCharacterDetailBinding? = null

    @Inject
    lateinit var charactersViewModelFactory: CharactersViewModelFactory

    private lateinit var model: CharacterViewModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val _binding = FragmentCharacterDetailBinding.inflate(inflater, container, false)
        binding = _binding
        return _binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        DaggerFragmentComponent.builder().directoryComponent(
            (requireActivity().applicationContext as DirectoryComponentProvider).directoryComponent()
        )
            .build().inject(this)

        model = ViewModelProvider(
            requireActivity(),
            charactersViewModelFactory
        ).get(CharacterViewModel::class.java)

        model.selectedCharacter.observe(this, { detail ->
            binding?.imageView.apply {
                Picasso.get().load(detail.image).into(this)
            }
        })
    }

    override fun onDestroyView() {
        super.onDestroyView()
        binding = null
    }
}