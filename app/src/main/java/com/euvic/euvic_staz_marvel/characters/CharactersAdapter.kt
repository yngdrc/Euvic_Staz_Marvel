package com.euvic.euvic_staz_marvel.characters;

import android.util.Log
import android.view.View
import android.view.ViewGroup
import com.euvic.euvic_staz_marvel.models.Result;
import androidx.recyclerview.widget.RecyclerView;
import org.jetbrains.anko.AnkoContext
import org.jetbrains.anko.sdk27.coroutines.onClick

class CharactersAdapter(val characters: MutableList<Result>): RecyclerView.Adapter<CharactersAdapter.CharactersViewHolder>() {
    inner class CharactersViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bind(character: Result) {
            with(character) {
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CharactersViewHolder {
        return CharactersViewHolder(
            CharacterUI().createView(
                AnkoContext.create(parent.context, parent)
            )
        )
    }

    override fun onBindViewHolder(holder: CharactersViewHolder, position: Int) {
        holder.bind(characters[position])
        holder.itemView.onClick {
            Log.d("ADAPTER", position.toString())
        }
    }

    override fun getItemCount(): Int {
        TODO("Not yet implemented")
    }
}
