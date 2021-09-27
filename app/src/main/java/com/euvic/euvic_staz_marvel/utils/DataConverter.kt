package com.euvic.euvic_staz_marvel.utils

import androidx.room.TypeConverter
import com.euvic.euvic_staz_marvel.db.models.characters.dto.Comics
import com.euvic.euvic_staz_marvel.db.models.characters.dto.Series
import com.euvic.euvic_staz_marvel.db.models.characters.dto.CharactersThumbnail
import com.google.gson.reflect.TypeToken

import com.google.gson.Gson
import java.lang.reflect.Type


class DataConverter {
    private val gson = Gson()
    @TypeConverter
    fun fromMutableList(mutableList: MutableList<Any>?): String? {
        if (mutableList == null) {
            return null
        }
        val type: Type = object : TypeToken<MutableList<Any>?>() {}.type
        return gson.toJson(mutableList, type)
    }

    @TypeConverter
    fun toMutableList(jsonString: String?): MutableList<Any>? {
        if (jsonString == null) {
            return null
        }
        val type: Type = object : TypeToken<MutableList<Any>?>() {}.type
        return gson.fromJson<MutableList<Any>>(jsonString, type)
    }

    @TypeConverter
    fun fromComics(comics: Comics?): String? {
        if (comics == null) {
            return null
        }
        val type: Type = object : TypeToken<Comics?>() {}.type
        return gson.toJson(comics, type)
    }

    @TypeConverter
    fun toComics(jsonString: String?): Comics? {
        if (jsonString == null) {
            return null
        }
        val type: Type = object : TypeToken<Comics?>() {}.type
        return gson.fromJson<Comics>(jsonString, type)
    }

    @TypeConverter
    fun fromThumbnail(thumbnail: CharactersThumbnail?): String? {
        if (thumbnail == null) {
            return null
        }
        val type: Type = object : TypeToken<CharactersThumbnail?>() {}.type
        return gson.toJson(thumbnail, type)
    }

    @TypeConverter
    fun toThumbnail(jsonString: String?): CharactersThumbnail? {
        if (jsonString == null) {
            return null
        }
        val type: Type = object : TypeToken<CharactersThumbnail?>() {}.type
        return gson.fromJson<CharactersThumbnail>(jsonString, type)
    }

    @TypeConverter
    fun fromSeries(series: Series?): String? {
        if (series == null) {
            return null
        }
        val type: Type = object : TypeToken<Series?>() {}.type
        return gson.toJson(series, type)
    }

    @TypeConverter
    fun toSeries(jsonString: String?): Series? {
        if (jsonString == null) {
            return null
        }
        val type: Type = object : TypeToken<CharactersThumbnail?>() {}.type
        return gson.fromJson<Series>(jsonString, type)
    }

    @TypeConverter
    fun fromSeriesThumbnail(thumbnail: com.euvic.euvic_staz_marvel.db.models.series.SeriesThumbnail?): String? {
        if (thumbnail == null) {
            return null
        }
        val type: Type = object : TypeToken<com.euvic.euvic_staz_marvel.db.models.series.SeriesThumbnail?>() {}.type
        return gson.toJson(thumbnail, type)
    }

    @TypeConverter
    fun toSeriesThumbnail(jsonString: String?): com.euvic.euvic_staz_marvel.db.models.series.SeriesThumbnail? {
        if (jsonString == null) {
            return null
        }
        val type: Type = object : TypeToken<com.euvic.euvic_staz_marvel.db.models.series.SeriesThumbnail?>() {}.type
        return gson.fromJson<com.euvic.euvic_staz_marvel.db.models.series.SeriesThumbnail>(jsonString, type)
    }
}