package com.takehome.sauravrp.helpers

import androidx.recyclerview.widget.DiffUtil
import com.takehome.sauravrp.viewmodels.Character

class CharacterItemResultDiffCallback : DiffUtil.ItemCallback<Character>() {
    override fun areItemsTheSame(oldItem: Character, newItem: Character) = oldItem.uuid == newItem.uuid
    override fun areContentsTheSame(oldItem: Character, newItem: Character) = oldItem == newItem
}