package rs.raf.projekat2.valerija_nagl_rn682018.presentation.view.activities

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import kotlinx.android.synthetic.main.activity_change_location.*
import kotlinx.android.synthetic.main.activity_map.*
import org.koin.androidx.viewmodel.ext.android.viewModel
import rs.raf.projekat2.valerija_nagl_rn682018.R
import rs.raf.projekat2.valerija_nagl_rn682018.data.models.Location
import rs.raf.projekat2.valerija_nagl_rn682018.presentation.view.contract.LocationContract
import rs.raf.projekat2.valerija_nagl_rn682018.presentation.view.fragments.ListFragment
import rs.raf.projekat2.valerija_nagl_rn682018.presentation.view.states.LocationsState
import rs.raf.projekat2.valerija_nagl_rn682018.presentation.viewmodel.LocationViewModel
import timber.log.Timber

class ChangeLocationActivity: AppCompatActivity(R.layout.activity_change_location), OnMapReadyCallback{

    private val viewModel: LocationContract.ViewModel by viewModel<LocationViewModel>()
    private var location : Location? = null
    private var mMap: GoogleMap? = null

    companion object{
        const val CHANGE_KEY = "changeKey"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        init()
        val mapFragment = supportFragmentManager.findFragmentById(R.id.mapChange) as SupportMapFragment?
        mapFragment?.getMapAsync(this)
    }

    private fun init(){
        parseIntent()
        initListeners()
        initObserver()
    }

    private fun parseIntent() {
        intent?.let {

            location = it.getParcelableExtra(CHANGE_KEY)

            val title : String? = location?.title
            val content : String? = location?.content

            changeTitleEt.setText(title)
            changeContentEt.setText(content)
            changeDateTv.text = location?.date.toString()
        }

    }

    override fun onMapReady(googleMap: GoogleMap) {
        mMap = googleMap
        mMap?.mapType = GoogleMap.MAP_TYPE_NORMAL
        mMap?.uiSettings?.isZoomControlsEnabled = true
        mMap?.uiSettings?.isZoomGesturesEnabled = true
        mMap?.uiSettings?.isCompassEnabled = true

        mMap?.clear()
        Timber.e(location.toString())
        val latLng = LatLng(location!!.latitude,location!!.longitude)
        mMap!!.addMarker(MarkerOptions().position(latLng).title(location!!.title).snippet(location!!.content))
    }


    private fun initObserver() {
        viewModel.locationsState.observe(this, Observer {
            when(it) {
                is LocationsState.Update -> {
                    viewModel.getLocationById(location!!.id)
                    Toast.makeText(this, "Informacije o lokaciji su uspešno promenjene!", Toast.LENGTH_LONG).show()
                }else -> {
                    Toast.makeText(this, "Greška prilikom menjanja podatka u bazi!", Toast.LENGTH_LONG).show()
                }
            }
        })

        viewModel.locationChanged.observe(this, Observer {
            location = it
            mMap?.let { it1 -> onMapReady(it1) }
        })
    }

    private fun initListeners(){

        ponisti_btn.setOnClickListener {
            val title : String? = location?.title
            val content : String? = location?.content

            changeTitleEt.setText(title)
            changeContentEt.setText(content)
        }

        sacuvaj_btn.setOnClickListener {
            if (checkInput()){
                val title = changeTitleEt.text.toString()
                val content = changeContentEt.text.toString()
                location?.id?.let { it1 -> viewModel.updateTitleAndContentById(it1, title, content) }
            }
        }

        nazad_btn.setOnClickListener{
            setResult(Activity.RESULT_OK)
            finish()
        }
    }

    // validacija unosa
    private fun checkInput(): Boolean  {
        var answer : Boolean = true

        if(changeTitleEt.text.isEmpty()  || changeContentEt.text.isEmpty() ){
            mistakeChangeTv.text = getString(R.string.greska_za_unos)
            answer = false
        }else{
            mistakeChangeTv.text = ""
        }

        return answer
    }
}