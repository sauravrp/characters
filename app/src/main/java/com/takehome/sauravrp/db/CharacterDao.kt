package com.takehome.sauravrp.db

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.takehome.sauravrp.viewmodels.ShowCharacter
import io.reactivex.rxjava3.core.Completable
import io.reactivex.rxjava3.core.Single

@Dao
interface CharacterDao {

    @Query("SELECT * from show_character")
    fun getAllCharacters() : Single<List<ShowCharacter>>

    @Query("SELECT * from show_character where id = :id")
    fun getCharacter(id: String) : Single<ShowCharacter>

    @Insert
    fun insertCharacters(vararg character: ShowCharacter) : Completable
}