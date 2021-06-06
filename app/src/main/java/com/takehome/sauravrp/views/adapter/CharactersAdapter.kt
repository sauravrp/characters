package com.takehome.sauravrp.views.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.RecyclerView
import com.squareup.picasso.Picasso
import com.takehome.sauravrp.R
import com.takehome.sauravrp.databinding.CharacterSummaryItemViewBinding
import com.takehome.sauravrp.helpers.CharacterItemResultDiffCallback
import com.takehome.sauravrp.viewmodels.Character
import com.takehome.sauravrp.views.adapter.CharactersAdapter.CharacterCardViewHolder

class CharactersAdapter(private val characterSelectionListener: CharacterSelectionListener) :
    PagingDataAdapter<Character, CharacterCardViewHolder>(CharacterItemResultDiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CharacterCardViewHolder {
        val itemBinding = CharacterSummaryItemViewBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return CharacterCardViewHolder(itemBinding).apply {
            itemBinding.root.setOnClickListener(this)
        }
    }

    override fun onBindViewHolder(holder: CharacterCardViewHolder, position: Int) {
        holder.bind(snapshot().items[position])
    }

    inner class CharacterCardViewHolder(private val binding: CharacterSummaryItemViewBinding) :
        RecyclerView.ViewHolder(binding.root), View.OnClickListener {

        fun bind(item: Character) {
            with(binding) {
                root.setOnClickListener(this@CharacterCardViewHolder)

                name.text = item.name
                name.isVisible = item.name.isNotBlank()

                speciesValue.text = item.species
                item.species.isNotBlank().let {
                    speciesValue.isVisible = it
                    speciesLabel.isVisible = it
                }
            }

            if(item.image.isNotBlank()) {
                binding.avatar.isVisible = true
                Picasso
                    .get()
                    .load(item.image)
                    .error(R.drawable.no_image_found)
                    .placeholder(R.drawable.image_loading)
                    .into(binding.avatar)
            } else {
                binding.avatar.isVisible = false
            }
        }

        override fun onClick(v: View?) {
            characterSelectionListener.cardItemSelected(snapshot().items[adapterPosition])
        }
    }

    interface CharacterSelectionListener {
        fun cardItemSelected(character: Character)
    }
}