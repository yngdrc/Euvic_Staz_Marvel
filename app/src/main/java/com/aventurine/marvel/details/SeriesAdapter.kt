package com.aventurine.marvel.details

import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView;
import com.aventurine.R
import com.bumptech.glide.Glide
import com.aventurine.marvel.db.models.series.SeriesResult
import com.aventurine.marvel.db.models.series.SeriesThumbnail
import org.jetbrains.anko.AnkoContext
import org.jetbrains.anko.find

// ograniczyc obiekty w liscie tylko do tych wyswietlanych
class SeriesAdapter(val series: MutableList<SeriesResult>): RecyclerView.Adapter<SeriesAdapter.SeriesViewHolder>() {

    inner class SeriesViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bind(series: SeriesResult, position: Int) {
            with(series) {
                val imageView: ImageView = itemView.find<ImageView>(R.id.seriesImage)
                val thumbnail: SeriesThumbnail = series.thumbnail
                imageView.layoutParams.height = 250
                imageView.layoutParams.width = 250
                itemView.find<TextView>(R.id.seriesName).text = series.title

                Glide.with(itemView.context)
                    .load("${thumbnail.path.replace("http", "https")}/standard_xlarge.${thumbnail.extension}")
                    .placeholder(R.drawable.image_placeholder_marvel_square)
                    .into(imageView)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SeriesViewHolder {
        return SeriesViewHolder(
            SeriesUI().createView(
                AnkoContext.create(parent.context, parent)
            )
        )
    }

    override fun onBindViewHolder(holder: SeriesViewHolder, position: Int) {
        holder.bind(series[position], position)
//        holder.itemView.onClick {
//            val bundle: Bundle = Bundle()
//            characters[position].id?.let { it1 -> bundle.putInt("characterID", it1) }
//            navController.navigate(R.id.action_charactersFragment_to_detailsFragment, bundle)
//        }
    }

    override fun getItemCount(): Int = series.size
}
