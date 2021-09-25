package com.euvic.euvic_staz_marvel.characters;

import android.os.Bundle
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.navigation.NavController
import androidx.navigation.findNavController
import com.euvic.euvic_staz_marvel.db.models.characters.CharactersResult;
import androidx.recyclerview.widget.RecyclerView;
import com.bumptech.glide.Glide
import com.euvic.euvic_staz_marvel.R
import com.euvic.euvic_staz_marvel.db.models.characters.CharactersThumbnail
import org.jetbrains.anko.AnkoContext
import org.jetbrains.anko.find
import org.jetbrains.anko.sdk27.coroutines.onClick

// ograniczyc obiekty w liscie tylko do tych wyswietlanych
class CharactersAdapter(val characters: MutableList<CharactersResult>): RecyclerView.Adapter<CharactersAdapter.CharactersViewHolder>() {
    lateinit var navController: NavController

    inner class CharactersViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bind(character: CharactersResult) {
            with(character) {
                val thumbnail: CharactersThumbnail? = character.thumbnail
                val imageView: ImageView = itemView.find<ImageView>(R.id.heroImage)
                imageView.layoutParams.height = 150
                imageView.layoutParams.width = 150
                itemView.find<TextView>(R.id.heroName).text = character.name
                Glide.with(itemView.context)
                    .load("${thumbnail?.path?.replace("http", "https")}/standard_xlarge.${thumbnail?.extension}")
                    .placeholder(R.drawable.image_placeholder_marvel_square)
                    .into(imageView)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CharactersViewHolder {
        navController = parent.findNavController()
        return CharactersViewHolder(
            CharactersUI().createView(
                AnkoContext.create(parent.context, parent)
            )
        )
    }

    override fun onBindViewHolder(holder: CharactersViewHolder, position: Int) {
        holder.bind(characters[position])
        holder.itemView.onClick {
            val bundle: Bundle = Bundle()
            characters[position].id?.let { it1 -> bundle.putInt("characterID", it1) }
            navController.navigate(R.id.action_charactersFragment_to_detailsFragment, bundle)
        }
    }

    override fun getItemCount(): Int = characters.size
}
