package rs.raf.projekat2.valerija_nagl_rn682018.data.datasources.local

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import rs.raf.projekat2.valerija_nagl_rn682018.data.models.LocationEntity
import rs.raf.projekat2.valerija_nagl_rn682018.data.datasources.local.converters.DateConverter


@Database(
    entities = [LocationEntity::class],
    version = 2,
    exportSchema = false)
@TypeConverters(DateConverter::class)
abstract class LocationDatabase : RoomDatabase(){
    abstract fun getLocationDao() : LocationDao
}