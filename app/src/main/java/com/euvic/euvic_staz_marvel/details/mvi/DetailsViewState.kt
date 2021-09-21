package com.euvic.euvic_staz_marvel.details.mvi

import com.euvic.euvic_staz_marvel.main.ViewStateBase
import com.euvic.euvic_staz_marvel.models.CharactersDataClass
import com.euvic.euvic_staz_marvel.models.Result

data class DetailsViewState(
    var loading: Boolean = false,
    var details: CharactersDataClass? = null,
    var empty: Int? = null,
    var error: Throwable? = null
) : ViewStateBase