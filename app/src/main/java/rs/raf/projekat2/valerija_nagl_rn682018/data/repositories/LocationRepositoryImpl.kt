package rs.raf.projekat2.valerija_nagl_rn682018.data.repositories

import io.reactivex.Completable
import io.reactivex.Observable
import rs.raf.projekat2.valerija_nagl_rn682018.data.models.Location
import rs.raf.projekat2.valerija_nagl_rn682018.data.models.LocationEntity
import rs.raf.projekat2.valerija_nagl_rn682018.data.models.LocationFilter
import rs.raf.projekat2.valerija_nagl_rn682018.data.datasources.local.LocationDao


class LocationRepositoryImpl(private val localDataSource: LocationDao) : LocationRepository {

    override fun getAll(): Observable<List<Location>> {
        return localDataSource
            .getAll()
            .map {
                it.map {
                    Location(it.id,it.latitude, it.longitude, it.title,it.content,it.date)
                }
            }
    }

    override fun addLocation(location: Location): Completable {
        val locationEntity = LocationEntity(location.id,location.latitude, location.longitude, location.title,location.content,location.date)
        return localDataSource.insert(locationEntity)
    }

    override fun updateTitleAndContentById(id: Long, title: String, content: String): Completable {
        return localDataSource.updateTitleAndContentById(id, title, content)
    }

    override fun getAllByFilter(locationFilter: LocationFilter): Observable<List<Location>> {
        // u zavinosti od toga da li je sort true ili false, pozivamo metodu koja sortira opadajuce/rastuce
        if(locationFilter.sort == true){
            return localDataSource
                .getByFilterDesc(locationFilter.filter)
                .map {
                    it.map {
                        Location(it.id,it.latitude, it.longitude, it.title,it.content,it.date)
                    }
                }
        }else{
            return localDataSource
                .getByFilterAsc(locationFilter.filter)
                .map {
                    it.map {
                        Location(it.id,it.latitude, it.longitude, it.title,it.content,it.date)
                    }
                }
        }
    }

    override fun getLocationById(id: Long): Observable<Location> {
        return localDataSource
            .getLocationById(id)
            .map {
                Location(it.id,it.latitude, it.longitude, it.title,it.content,it.date)
            }
    }

    override fun deleteLocation(id: Long): Completable {
        return localDataSource.delete(id)
    }
}
