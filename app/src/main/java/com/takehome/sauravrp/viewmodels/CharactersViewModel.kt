package com.takehome.sauravrp.viewmodels

import android.os.Parcelable
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.takehome.sauravrp.repository.DirectoryRepository
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.disposables.Disposable
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

    private var disposable : Disposable? = null

    fun fetchCharacters() {
        disposable?.dispose()
        directoryRepository
                /** use this for testing malformed data only */
//            .getEmployeeDirectoryMalformed()
            /** use this for testing empty data only */
//            .getEmptyEmployeeDirectory()
            .getCharacterDirectory()
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
data class Character(
    val uuid: String,
    val name: String,
    val phone: String,
    val email: String,
    val biography: String,
    val smallPhotoUrl: String,
    val largePhotoUrl: String,
    val team: String,
    val type: EmployeeType
) : Parcelable

@Parcelize
data class Characters(
    val characters: List<Character>
) : Parcelable

enum class EmployeeType {
    FULL_TIME, PART_TIME, CONTRACTOR
}