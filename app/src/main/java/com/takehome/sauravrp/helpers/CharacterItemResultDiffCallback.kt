package com.takehome.sauravrp.helpers

import androidx.recyclerview.widget.DiffUtil
import com.takehome.sauravrp.viewmodels.ShowCharacter

class CharacterItemResultDiffCallback : DiffUtil.ItemCallback<ShowCharacter>() {
    override fun areItemsTheSame(oldItem: ShowCharacter, newItem: ShowCharacter) = oldItem.id == newItem.id
    override fun areContentsTheSame(oldItem: ShowCharacter, newItem: ShowCharacter) = oldItem == newItem
}