package com.takehome.sauravrp.viewmodels

import android.os.Parcelable
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.takehome.sauravrp.repository.DirectoryRepository
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.disposables.Disposable
import io.reactivex.rxjava3.schedulers.Schedulers
import kotlinx.android.parcel.Parcelize

class CharacterViewModel(
        private val directoryRepository: DirectoryRepository
) : ViewModel() {

    sealed class ViewState {
        class Error(val error: Throwable) : ViewState()
        object Loading : ViewState()
        class Success(val data: Characters) : ViewState()
    }

    private val mutableViewState = MutableLiveData<ViewState>()

    val viewState: LiveData<ViewState> by lazy {
        fetchCharacters()
        mutableViewState
    }

    private var disposable: Disposable? = null

    fun fetchCharacters() {
        disposable?.dispose()
        directoryRepository
                .getCharacterDirectory()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnSubscribe {
                    mutableViewState.value = ViewState.Loading
                }
                .subscribe({ result ->
                    mutableViewState.value = ViewState.Success(data = result)
                }, { error ->
                    mutableViewState.value = ViewState.Error(error = error)
                }).also {
                    disposable = it
                }
    }

    override fun onCleared() {
        super.onCleared()
        disposable?.dispose()
    }
}


@Parcelize
@Entity(tableName = "show_character")
data class ShowCharacter(
        @PrimaryKey val id: String,
        val name: String,
        val species: String,
        val type: String,
        val gender: String,
        val image: String
) : Parcelable

@Parcelize
data class Characters(
        val info: Info?,
        val characters: List<ShowCharacter>
) : Parcelable

@Parcelize
data class Info(
        val count: Int,
        val pages: Int,
        val next: String?,
        val prev: String?
) : Parcelable