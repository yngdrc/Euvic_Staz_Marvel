package com.euvic.euvic_staz_marvel.db

import com.euvic.euvic_staz_marvel.db.models.characters.CharactersDataClass
import com.euvic.euvic_staz_marvel.db.models.series.SeriesData
import com.euvic.euvic_staz_marvel.db.models.series.SeriesDataClass
import com.euvic.euvic_staz_marvel.db.models.series.SeriesResult

class SeriesRepo(private val seriesDao: SeriesDao) {
    fun insertSeries(series: SeriesResult) =
        seriesDao.insert(series)

    fun updateSeries(series: SeriesResult) =
        seriesDao.update(series)

    fun deleteSeries(series: SeriesResult) =
        seriesDao.delete(series)

//    fun getAllSeries(): Observable<DataState<MainViewState>> {
//    }
}