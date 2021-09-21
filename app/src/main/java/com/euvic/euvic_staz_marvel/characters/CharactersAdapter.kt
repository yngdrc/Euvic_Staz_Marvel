package com.euvic.euvic_staz_marvel.characters;

import android.util.Log
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import com.euvic.euvic_staz_marvel.models.Result;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.CircularProgressDrawable
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.euvic.euvic_staz_marvel.R
import com.euvic.euvic_staz_marvel.models.Thumbnail
import io.reactivex.subjects.PublishSubject
import io.reactivex.subjects.Subject
import org.jetbrains.anko.AnkoContext
import org.jetbrains.anko.find
import org.jetbrains.anko.sdk27.coroutines.onClick

// ograniczyc obiekty w liscie tylko do tych wyswietlanych
class CharactersAdapter(val characters: MutableList<Result>): RecyclerView.Adapter<CharactersAdapter.CharactersViewHolder>() {
    val subject: Subject<Int> = PublishSubject.create()

    inner class CharactersViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        fun bind(character: Result) {
            with(character) {
                val thumbnail: Thumbnail? = character.thumbnail
                itemView.find<TextView>(R.id.heroName).text = character.name
                Glide.with(itemView.context)
                    .load("${thumbnail?.path?.replace("http", "https")}/standard_xlarge.${thumbnail?.extension}")
                    .apply(RequestOptions().override(150, 150))
                    .into(itemView.find<ImageView>(R.id.heroImage))
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CharactersViewHolder {
        return CharactersViewHolder(
            CharactersUI().createView(
                AnkoContext.create(parent.context, parent)
            )
        )
    }

    // stworzyc PublishSubject i tutaj onNext
    override fun onBindViewHolder(holder: CharactersViewHolder, position: Int) {
        holder.bind(characters[position])
        holder.itemView.onClick {
            Log.d("ADAPTER", position.toString())
            characters[position].id?.let { id -> subject.onNext(id) }
        }
    }

    override fun getItemCount(): Int = characters.size
}
