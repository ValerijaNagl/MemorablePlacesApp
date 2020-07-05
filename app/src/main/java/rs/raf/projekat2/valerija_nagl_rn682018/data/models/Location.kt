package rs.raf.projekat2.valerija_nagl_rn682018.data.models

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize
import java.util.*

@Parcelize
data class Location(val id : Long, val latitude : Double, val longitude: Double, val title :String, val content : String, val date : Date)
    : Parcelable