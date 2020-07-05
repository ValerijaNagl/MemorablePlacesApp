package rs.raf.projekat2.valerija_nagl_rn682018.data.datasources.local

import androidx.room.*
import io.reactivex.Completable
import io.reactivex.Observable
import rs.raf.projekat2.valerija_nagl_rn682018.data.models.LocationEntity

@Dao
abstract class LocationDao {
    @Insert( onConflict = OnConflictStrategy.REPLACE )
    abstract fun insert(entity: LocationEntity): Completable

    @Insert( onConflict = OnConflictStrategy.REPLACE )
    abstract fun insertAll(entities: List<LocationEntity>): Completable

    @Query("SELECT * FROM locations WHERE ((title LIKE '%'|| :titleContent ||'%' ) OR (content LIKE '%'|| :titleContent ||'%'))")
    abstract fun getByFilter(titleContent: String): Observable<List<LocationEntity>>

    @Query("SELECT * FROM locations WHERE ((title LIKE '%'|| :titleContent ||'%' ) OR (content LIKE '%'|| :titleContent ||'%')) ORDER BY  date DESC")
    abstract fun getByFilterDesc(titleContent: String): Observable<List<LocationEntity>>

    @Query("SELECT * FROM locations WHERE ((title LIKE '%'|| :titleContent ||'%' ) OR (content LIKE '%'|| :titleContent ||'%')) ORDER BY  date ASC")
    abstract fun getByFilterAsc(titleContent: String): Observable<List<LocationEntity>>

    @Query("SELECT * FROM locations")
    abstract fun getAll(): Observable<List<LocationEntity>>

    @Query("DELETE FROM locations")
    abstract fun deleteAll()

    @Query("DELETE FROM locations WHERE id=:id")
    abstract  fun delete(id:Long) : Completable

    @Query("SELECT * FROM locations WHERE id=:id")
    abstract  fun getLocationById(id:Long) : Observable<LocationEntity>

    @Query("UPDATE locations SET title = :title,content = :content WHERE id == :id")
    abstract fun updateTitleAndContentById(id: Long, title: String, content:String): Completable

    @Transaction
    open fun deleteAndInsertAll(entities: List<LocationEntity>) {
        deleteAll()
        insertAll(entities).blockingAwait()
    }
}