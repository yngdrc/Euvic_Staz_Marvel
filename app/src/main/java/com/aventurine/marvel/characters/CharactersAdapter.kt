package com.aventurine.marvel.characters;

import android.os.Bundle
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.navigation.findNavController
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import com.aventurine.marvel.db.models.characters.CharactersResult;
import androidx.recyclerview.widget.RecyclerView;
import com.aventurine.R
import com.bumptech.glide.Glide
import com.aventurine.marvel.db.models.characters.CharactersThumbnail
import io.reactivex.rxjava3.subjects.PublishSubject
import org.jetbrains.anko.AnkoContext
import org.jetbrains.anko.find
import org.jetbrains.anko.sdk27.coroutines.onClick

sealed class CharacterItemAction {
    data class Navigate(
        val characterId: Int
    ) : CharacterItemAction()
}

class CharactersAdapter : RecyclerView.Adapter<CharactersAdapter.CharactersViewHolder>() {
    val actionRelay: PublishSubject<CharacterItemAction> = PublishSubject.create()

    private val diffCallback = object : DiffUtil.ItemCallback<CharactersResult>() {
        override fun areItemsTheSame(
            oldItem: CharactersResult,
            newItem: CharactersResult
        ): Boolean = oldItem.id == newItem.id

        override fun areContentsTheSame(
            oldItem: CharactersResult,
            newItem: CharactersResult
        ): Boolean = oldItem == newItem
    }

    private val differ = AsyncListDiffer(this, diffCallback)
    fun submitList(list: Collection<CharactersResult>) = differ.submitList(list.toList())

    inner class CharactersViewHolder(
        private val layout: CharacterListItemLayout
    ) : RecyclerView.ViewHolder(layout.rootView) {
        fun bind(character: CharactersResult) = with(layout) {
            heroName.text = character.name

            Glide.with(itemView.context).load(
                "${
                    character.thumbnail.path.replace(
                        "http",
                        "https"
                    )
                }/standard_xlarge.${character.thumbnail.extension}"
            ).placeholder(R.drawable.image_placeholder_marvel_square).into(heroImage)

            setOnClickListener {
                actionRelay.onNext(
                    CharacterItemAction.Navigate(characterId = character.id)
                )
            }
        }
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): CharactersViewHolder = CharactersViewHolder(layout = CharacterListItemLayout(parent.context))

    override fun onBindViewHolder(
        holder: CharactersViewHolder,
        position: Int
    ) = holder.bind(differ.currentList[position])

    override fun getItemCount(): Int = differ.currentList.size
}
