package rs.raf.projekat2.valerija_nagl_rn682018.presentation.view.states

import rs.raf.projekat2.valerija_nagl_rn682018.data.models.Location

sealed class LocationsState {

    data class Success(val locations: List<Location>): LocationsState()
    data class Error(val message: String): LocationsState()
    object Update: LocationsState()
    object Add: LocationsState()
    object Delete: LocationsState()

}