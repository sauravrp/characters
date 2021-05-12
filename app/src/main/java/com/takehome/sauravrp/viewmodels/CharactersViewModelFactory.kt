package com.takehome.sauravrp.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.takehome.sauravrp.repository.DirectoryRepository

open class CharactersViewModelFactory(
    private val directoryRepository: DirectoryRepository
) : ViewModelProvider.Factory {

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
       return CharacterViewModel(directoryRepository) as T
    }
}