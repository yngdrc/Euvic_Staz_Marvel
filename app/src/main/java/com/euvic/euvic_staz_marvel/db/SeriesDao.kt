package com.euvic.euvic_staz_marvel.db

import androidx.room.*
import androidx.sqlite.db.SupportSQLiteQuery
import com.euvic.euvic_staz_marvel.db.models.series.SeriesData
import com.euvic.euvic_staz_marvel.db.models.series.SeriesDataClass
import com.euvic.euvic_staz_marvel.db.models.series.SeriesResult

@Dao
interface SeriesDao {
    @Insert
    fun insert(series: SeriesResult)

    @Update
    fun update(series: SeriesResult)

    @Delete
    fun delete(series: SeriesResult)

//    @Query("SELECT * FROM series")
//    fun getSeries(): MutableList<SeriesResult>

    @RawQuery
    fun getSeriesViaQuery(query: SupportSQLiteQuery): MutableList<SeriesResult>
}