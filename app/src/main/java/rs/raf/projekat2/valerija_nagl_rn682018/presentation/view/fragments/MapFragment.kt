package rs.raf.projekat2.valerija_nagl_rn682018.presentation.view.fragments

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import org.koin.androidx.viewmodel.ext.android.sharedViewModel
import rs.raf.projekat2.valerija_nagl_rn682018.R
import rs.raf.projekat2.valerija_nagl_rn682018.data.models.Location
import rs.raf.projekat2.valerija_nagl_rn682018.presentation.view.contract.LocationContract
import rs.raf.projekat2.valerija_nagl_rn682018.presentation.view.states.LocationsState
import rs.raf.projekat2.valerija_nagl_rn682018.presentation.viewmodel.LocationViewModel
import timber.log.Timber


class MapFragment : Fragment(R.layout.fragment_map), OnMapReadyCallback{

    private val viewModel: LocationContract.ViewModel by sharedViewModel<LocationViewModel>()
    private var locations : MutableList<Location> = mutableListOf()
    private var mMap: GoogleMap? = null

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val mapFragment = childFragmentManager.findFragmentById(R.id.mapList) as SupportMapFragment?
        mapFragment?.getMapAsync(this)
        initObservers()
    }

    override fun onResume() {
        super.onResume()
        val mapFragment = childFragmentManager.findFragmentById(R.id.mapList) as SupportMapFragment?
        mapFragment?.getMapAsync(this)
    }


    override fun onMapReady(googleMap: GoogleMap) {
        mMap = googleMap
        mMap?.moveCamera(CameraUpdateFactory.newLatLngZoom(LatLng(44.7866, 20.4489), 10f))
        mMap?.mapType = GoogleMap.MAP_TYPE_NORMAL
        mMap?.uiSettings?.isZoomControlsEnabled = true
        mMap?.uiSettings?.isZoomGesturesEnabled = true
        mMap?.uiSettings?.isCompassEnabled = true
        mMap?.clear()

        locations.forEach {
            val location = LatLng(it.latitude,it.longitude)
            // dodajemo naslov i sadrzaj
            mMap!!.addMarker(MarkerOptions().position(location).title(it.title).snippet(it.content))
        }

    }


    private fun initObservers(){
        viewModel.locationsState.observe(viewLifecycleOwner, Observer {
            renderState(it)
        })

        viewModel.getAll()
    }

    private fun renderState(state : LocationsState) {
        when (state) {
            is LocationsState.Success -> {
                locations.clear()
                state.locations.map {
                    val location = Location(it.id, it.latitude, it.longitude, it.title, it.content, it.date)
                    locations.add(location)
                }
            }
            is LocationsState.Error -> {
                Toast.makeText(context, state.message, Toast.LENGTH_SHORT).show()
            }
            is LocationsState.Update -> {
                Timber.e("Lokacija je uspesno promenjena!")
                Toast.makeText(context, "Lokacija je uspesno promenjena!", Toast.LENGTH_SHORT).show()
            }
            is LocationsState.Add -> {
                Timber.e("Lokacija je uspesno dodata!")
                Toast.makeText(context, "Lokacija je uspesno dodata!", Toast.LENGTH_SHORT).show()
            }
            is LocationsState.Delete -> {
                Timber.e("Lokacija je uspesno izbrisana!")
                Toast.makeText(context, "Lokacija je uspesno izbrisana!", Toast.LENGTH_SHORT).show()
            }
        }
    }

}









