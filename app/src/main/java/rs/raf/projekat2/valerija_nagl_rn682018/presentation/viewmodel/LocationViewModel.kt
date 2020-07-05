package rs.raf.projekat2.valerija_nagl_rn682018.presentation.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import io.reactivex.subjects.PublishSubject
import rs.raf.projekat2.valerija_nagl_rn682018.data.models.Location
import rs.raf.projekat2.valerija_nagl_rn682018.data.models.LocationFilter
import rs.raf.projekat2.valerija_nagl_rn682018.data.repositories.LocationRepository
import rs.raf.projekat2.valerija_nagl_rn682018.presentation.view.contract.LocationContract
import rs.raf.projekat2.valerija_nagl_rn682018.presentation.view.states.LocationsState
import timber.log.Timber
import java.util.concurrent.TimeUnit

class LocationViewModel(private val locationRepository: LocationRepository) : ViewModel(), LocationContract.ViewModel {

    override val locationsState: MutableLiveData<LocationsState> = MutableLiveData()
    override val locationChanged: MutableLiveData<Location> = MutableLiveData()
    private val subscriptions = CompositeDisposable()
    private val publishSubject: PublishSubject<LocationFilter> = PublishSubject.create()

    init {
        val subscription = publishSubject
            .debounce(200, TimeUnit.MILLISECONDS)
            .distinctUntilChanged()
            .switchMap {
                locationRepository
                    .getAllByFilter(it)
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .doOnError {
                        Timber.e("Greska u publish subject-u.")
                        Timber.e(it)
                    }
            }
            .subscribe(
                {
                    locationsState.value = LocationsState.Success(it)
                },
                {
                    locationsState.value = LocationsState.Error("Greska tokom preuzimanja podataka iz baze.")
                    Timber.e(it)
                }
            )
        subscriptions.add(subscription)
    }

    override fun getAll() {
        val subscription = locationRepository
            .getAll()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(
                {
                    locationsState.value = LocationsState.Success(it)
                },
                {
                    locationsState.value = LocationsState.Error("Greska tokom preuzimanja podataka iz baze.")
                    Timber.e(it)
                }
            )
        subscriptions.add(subscription)
    }

    override fun addLocation(location: Location) {
        val subscription = locationRepository
            .addLocation(location)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(
                {
                    locationsState.value = LocationsState.Add
                },
                {
                    locationsState.value = LocationsState.Error("Greska tokom dodavanja podatka u bazu.")
                    Timber.e(it)
                }
            )
        subscriptions.add(subscription)
    }

    override fun getAllByFilter(locationFilter: LocationFilter) {
        publishSubject.onNext(locationFilter)
    }

    override fun updateTitleAndContentById(id: Long, title: String, content: String) {
        val subscription = locationRepository
            .updateTitleAndContentById(id, title, content)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(
                {
                    locationsState.value = LocationsState.Update
                },
                {
                    locationsState.value = LocationsState.Error("Greska tokom menjanja podatka iz baze.")
                    Timber.e(it)
                }
            )
        subscriptions.add(subscription)
    }

    override fun deleteLocation(id: Long) {
        val subscription = locationRepository
            .deleteLocation(id)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(
                {
                    locationsState.value = LocationsState.Delete
                },
                {
                    locationsState.value = LocationsState.Error("Greska tokom brisanja podatka iz baze.")
                    Timber.e(it)
                }
            )
        subscriptions.add(subscription)
    }

    override fun getLocationById(id: Long) {
        val subscription = locationRepository
            .getLocationById(id)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(
                {
                    locationChanged.value = it
                },
                {
                    locationsState.value = LocationsState.Error("Greska prilikom uzimanja podatka iz baze.")
                    Timber.e(it)
                }
            )
        subscriptions.add(subscription)
    }
}