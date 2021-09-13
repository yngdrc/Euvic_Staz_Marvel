package com.euvic.euvic_staz_marvel.main

import com.euvic.euvic_staz_marvel.models.CharactersDataClass

interface PartialMainState {
    class Loading : PartialMainState
    class GotCharacters(var characters: CharactersDataClass) : PartialMainState
    class Error(var error: Throwable) : PartialMainState
}