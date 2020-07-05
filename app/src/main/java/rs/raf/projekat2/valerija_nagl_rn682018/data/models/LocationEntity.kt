package rs.raf.projekat2.valerija_nagl_rn682018.data.models

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.*

@Entity(tableName = "locations")
data class LocationEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Long,
    val latitude:Double,
    val longitude: Double,
    val title :String,
    val content : String,
    val date: Date
)