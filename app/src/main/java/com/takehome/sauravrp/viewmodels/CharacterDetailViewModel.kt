package com.takehome.sauravrp.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.takehome.sauravrp.repository.DirectoryRepository
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.disposables.CompositeDisposable
import io.reactivex.rxjava3.schedulers.Schedulers

class CharacterDetailViewModel(private val directoryRepository: DirectoryRepository): ViewModel() {


    private val mutableViewState = MutableLiveData<ViewState>()

    val viewState : LiveData<ViewState> by lazy {
        mutableViewState
    }

    private val compositeDisposable = CompositeDisposable()

    fun getDetail(id: String) {
        directoryRepository
            .getCharacterDetail(id)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({ data ->
                       mutableViewState.value = ViewState.Success(data)
            }, {
                mutableViewState.value = ViewState.Error(it)
            }).also {
                compositeDisposable.add(it)
            }
    }

    override fun onCleared() {
        super.onCleared()
        compositeDisposable.clear()
    }

    sealed class ViewState {
        data class Success(val character: ShowCharacter) : ViewState()
        data class Error(val error: Throwable) : ViewState()
    }
}