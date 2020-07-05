package rs.raf.projekat2.valerija_nagl_rn682018.modules

import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module
import rs.raf.projekat2.valerija_nagl_rn682018.data.repositories.LocationRepository
import rs.raf.projekat2.valerija_nagl_rn682018.data.repositories.LocationRepositoryImpl
import rs.raf.projekat2.valerija_nagl_rn682018.data.datasources.local.LocationDatabase
import rs.raf.projekat2.valerija_nagl_rn682018.presentation.viewmodel.LocationViewModel


val locationModule = module {

    viewModel { LocationViewModel(get()) }

    single<LocationRepository> { LocationRepositoryImpl(get()) }

    single { get<LocationDatabase>().getLocationDao() }

}