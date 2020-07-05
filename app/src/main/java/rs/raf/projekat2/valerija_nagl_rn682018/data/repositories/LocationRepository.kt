package rs.raf.projekat2.valerija_nagl_rn682018.data.repositories

import io.reactivex.Completable
import io.reactivex.Observable
import rs.raf.projekat2.valerija_nagl_rn682018.data.models.Location
import rs.raf.projekat2.valerija_nagl_rn682018.data.models.LocationFilter

interface LocationRepository {
    fun getAll(): Observable<List<Location>>
    fun addLocation(location: Location): Completable
    fun updateTitleAndContentById(id:Long,title:String,content:String):Completable
    fun getAllByFilter(locationFilter : LocationFilter): Observable<List<Location>>
    fun getLocationById(id:Long): Observable<Location>
    fun deleteLocation(id:Long): Completable
}

