package rs.raf.projekat2.valerija_nagl_rn682018.presentation.view.contract

import androidx.lifecycle.LiveData
import rs.raf.projekat2.valerija_nagl_rn682018.data.models.Location
import rs.raf.projekat2.valerija_nagl_rn682018.data.models.LocationFilter

import rs.raf.projekat2.valerija_nagl_rn682018.presentation.view.states.LocationsState


interface LocationContract {

    interface ViewModel {

        val locationsState: LiveData<LocationsState>
        val locationChanged : LiveData<Location>

        fun getAll()
        fun addLocation(l : Location)
        fun getAllByFilter(locationFilter: LocationFilter)
        fun updateTitleAndContentById(id:Long,title:String,content:String)
        fun deleteLocation(id:Long)
        fun getLocationById(id:Long)
    }
}