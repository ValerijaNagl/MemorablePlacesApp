package rs.raf.projekat2.valerija_nagl_rn682018.modules

import android.content.Context
import android.content.SharedPreferences
import androidx.room.Room
import com.squareup.moshi.Moshi
import com.squareup.moshi.adapters.Rfc3339DateJsonAdapter
import org.koin.android.ext.koin.androidApplication
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module
import retrofit2.Retrofit
import rs.raf.projekat2.valerija_nagl_rn682018.data.datasources.local.LocationDatabase
import java.util.*

val coreModule = module {

    single<SharedPreferences> {
        androidApplication().getSharedPreferences(androidApplication().packageName, Context.MODE_PRIVATE)
    }

    single { Room.databaseBuilder(androidContext(), LocationDatabase::class.java, "LocationDatabase")
        .fallbackToDestructiveMigration()
        .build() }

    single { createMoshi() }

}

fun createMoshi(): Moshi {
    return Moshi.Builder()
        .add(Date::class.java, Rfc3339DateJsonAdapter())
        .build()
}

// Metoda koja kreira servis
//inline fun <reified T> create(retrofit: Retrofit): T  {
//    return retrofit.create<T>(T::class.java)
//}